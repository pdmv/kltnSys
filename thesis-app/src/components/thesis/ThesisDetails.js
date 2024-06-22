import { useNavigate, useParams } from "react-router-dom";
import Title from "../common/Title";
import { useCallback, useContext, useEffect, useState } from "react";
import { authApi, endpoints } from "../../configs/APIs";
import { Spinner, Alert, Card, ListGroup, Button, Form } from "react-bootstrap";
import FormatDate from "../common/FormatDate";
import { Helmet } from "react-helmet";
import ThesisStatusBadge from "../common/ThesisStatusBadge";
import { UserContext } from "../../configs/UserContext";

const ThesisDetails = () => {
  const { id } = useParams();
  const [thesis, setThesis] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const { user } = useContext(UserContext);
  const navigate = useNavigate();
  const [file, setFile] = useState(null);
  const [submitting, setSubmitting] = useState(false);

  const fetchThesis = useCallback(async () => {
    setLoading(true);
    try {
      let res = await authApi().get(endpoints["thesis-details"](id));
      setThesis(res.data);
    } catch (error) {
      if (error.response && error.response.status === 403) {
        navigate("/forbidden");
      } else {
        console.error(error);
        setError("Không thể tải dữ liệu khoá luận.");
      }
    } finally {
      setLoading(false);
    }
  }, [id, navigate]);

  useEffect(() => {
    fetchThesis();
  }, [fetchThesis]);

  const handleBack = () => {
    navigate(-1);
  };

  const handleFileChange = (event) => {
    const selectedFile = event.target.files[0];
    if (selectedFile && (selectedFile.type === "application/zip" || selectedFile.type === "application/x-rar-compressed" || selectedFile.name.endsWith('.zip') || selectedFile.name.endsWith('.rar'))) {
      setFile(selectedFile);
    } else {
      setError("Chỉ chấp nhận tệp ZIP hoặc RAR.");
    }
  };

  const handleSubmit = async () => {
    if (!file) {
      setError("Vui lòng chọn tệp để nộp.");
      return;
    }

    setSubmitting(true);
    setError(null);
    setSuccess(null);

    const formData = new FormData();
    formData.append("file", file);

    try {
      let res = await authApi().post(endpoints["submit-report-file"](id), formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      setSuccess("Nộp báo cáo thành công!");
      setFile(null);
      setThesis(res.data);
    } catch (error) {
      if (error.response && error.response.status === 403) {
        navigate("/forbidden");
      } else {
        setError("Có lỗi xảy ra khi nộp báo cáo.");
      }
    } finally {
      setSubmitting(false);
    }
  };

  const handleDownload = (url) => {
    fetch(url)
      .then(response => {
        if (response.status === 403) {
          navigate("/forbidden");
          throw new Error("403 Forbidden");
        }
        return response.blob();
      })
      .then(blob => {
        const link = document.createElement('a');
        const objectURL = URL.createObjectURL(blob);
        link.href = objectURL;
        link.download = url.split('/').pop();
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        URL.revokeObjectURL(objectURL);
      })
      .catch(error => {
        if (error.message !== "403 Forbidden") {
          console.error("Error downloading the file:", error);
        }
      });
  };

  return (
    <>
      <Helmet>
        <title>Chi tiết Khoá luận</title>
      </Helmet>
      <div className="thesis-details-container">
        {loading && (
          <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '200px' }}>
            <Spinner
              as="span"
              animation="border"
              size="sm"
              role="status"
              aria-hidden="true"
            />
          </div>
        )}
        <div className="mt-4">
          {error && <Alert variant="danger">{error}</Alert>}
          {success && <Alert variant="success">{success}</Alert>}
        </div>
        {thesis && (
          <Card className="thesis-card mt-4 mb-4">
            <Card.Body>
              <Card.Title className="mb-4"><Title title="Chi tiết khoá luận" strong={thesis.name} /></Card.Title>
              <ListGroup variant="flush">
                <ListGroup.Item><strong>ID:</strong> {thesis.id}</ListGroup.Item>
                <ListGroup.Item><strong>Tên khoá luận:</strong> {thesis.name}</ListGroup.Item>
                <ListGroup.Item><strong>Ngày bắt đầu thực hiện:</strong> <FormatDate date={thesis.startDate} /></ListGroup.Item>
                <ListGroup.Item><strong>Ngày kết thúc thực hiện:</strong> <FormatDate date={thesis.endDate} /></ListGroup.Item>
                <ListGroup.Item><strong>Ngày hết hạn nộp:</strong> <FormatDate date={thesis.expDate} /></ListGroup.Item>
                <ListGroup.Item><strong>Năm học:</strong> {thesis.schoolYearId.startYear} - {thesis.schoolYearId.endYear}</ListGroup.Item>
                <ListGroup.Item><strong>Khoa:</strong> {thesis.facultyId.name}</ListGroup.Item>
                <ListGroup.Item><strong>Ngành:</strong> {thesis.majorId.name}</ListGroup.Item>
                <ListGroup.Item><strong>Trạng thái:</strong> <ThesisStatusBadge status={thesis.status} /></ListGroup.Item>
                {thesis.avgScore && <ListGroup.Item><strong>Điểm trung bình:</strong> {thesis.avgScore}</ListGroup.Item>}
                <ListGroup.Item><strong>Ghi chú:</strong> {thesis.comment}</ListGroup.Item>
                <ListGroup.Item>
                  <strong>Giảng viên hướng dẫn:</strong>
                  <ul className="lecturer-list">
                    {thesis.thesisLecturerSet.map((lecturer) => (
                      <li key={lecturer.id}>{lecturer.lastName} {lecturer.firstName} - Liên hệ: {lecturer.email}</li>
                    ))}
                  </ul>
                </ListGroup.Item>
                <ListGroup.Item>
                  <strong>Sinh viên thực hiện:</strong>
                  <ul className="student-list">
                    {thesis.thesisStudentSet.map((student, index) => (
                      <li key={index}>{student.lastName} {student.firstName} - Liên hệ: {student.email}</li>
                    ))}
                  </ul>
                </ListGroup.Item>
                <ListGroup.Item>
                  <strong>Giảng viên phản biện:</strong> {thesis.criticalLecturerId.lastName} {thesis.criticalLecturerId.firstName} - Liên hệ: {thesis.criticalLecturerId.email}
                </ListGroup.Item>
                <ListGroup.Item>
                  <strong>Người tạo:</strong> {thesis.affairId.lastName} {thesis.affairId.firstName} - Liên hệ: {thesis.affairId.email}
                </ListGroup.Item>
                {thesis.reportFile && (
                  <ListGroup.Item>
                    <strong>Tệp báo cáo:</strong> <Button variant="outline-dark" size="sm" onClick={() => handleDownload(thesis.reportFile)}>Tải xuống</Button>
                  </ListGroup.Item>
                )}
              </ListGroup>
              <div className="button-group mt-3">
                {user.account.role === "STUDENT" && (thesis.status === "in_progress" || thesis.status === "submitted") && (
                  <>
                    <Form.Group controlId="formFile" className="mb-3">
                      <Form.Label>Nộp Báo cáo (chỉ chấp nhận tệp ZIP hoặc RAR):</Form.Label>
                      <Form.Control type="file" accept=".zip,.rar" onChange={handleFileChange} />
                    </Form.Group>
                    <Button variant="dark" type="submit" disabled={submitting} onClick={handleSubmit}>
                      {submitting ? <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" /> : <>Nộp <strong>Báo cáo</strong></>}
                    </Button>
                  </>
                )}
                <Button onClick={handleBack} variant="outline-dark" className="ms-2">Quay lại</Button>
              </div>
            </Card.Body>
          </Card>
        )}
      </div>
    </>
  );
};

export default ThesisDetails;