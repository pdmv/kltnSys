import { Link, useNavigate, useParams } from "react-router-dom";
import Title from "../common/Title";
import { useCallback, useEffect, useState } from "react";
import { authApi, endpoints } from "../../configs/APIs";
import { Spinner, Alert, Card, ListGroup, Button } from "react-bootstrap";
import FormatDate from "../common/FormatDate";
import { Helmet } from "react-helmet";
import ThesisStatusBadge from "../common/ThesisStatusBadge";

const ThesisDetails = () => {
  const { id } = useParams();
  const [thesis, setThesis] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const nav = useNavigate();

  const fetchThesis = useCallback(async () => {
    setLoading(true);
    try {
      let res = await authApi().get(endpoints["thesis-details"](id));
      setThesis(res.data);
    } catch (error) {
      console.error(error);
      setError("Không thể tải dữ liệu khoá luận.");
    } finally {
      setLoading(false);
    }
  }, [id]);

  useEffect(() => {
    fetchThesis();
  }, [fetchThesis]);

  const handleBack = () => {
    nav(-1);
  }

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
        {error && <Alert variant="danger">{error}</Alert>}
        {thesis && (
          <Card className="thesis-card mt-4 mb-4">
            <Card.Body>
              <Card.Title className="mb-4"><Title title="Chi tiết khoá luận" strong={thesis.name} /></Card.Title>
              <ListGroup variant="flush">
                <ListGroup.Item><strong>ID:</strong> {thesis.id}</ListGroup.Item>
                <ListGroup.Item><strong>Ngày bắt đầu thực hiện:</strong> <FormatDate date={thesis.startDate} /></ListGroup.Item>
                <ListGroup.Item><strong>Ngày kết thúc thực hiện:</strong> <FormatDate date={thesis.endDate} /></ListGroup.Item>
                <ListGroup.Item><strong>Ngày hết hạn nộp:</strong> <FormatDate date={thesis.expDate} /></ListGroup.Item>
                <ListGroup.Item><strong>Trạng thái:</strong> <ThesisStatusBadge status={thesis.status} /></ListGroup.Item>
                <ListGroup.Item><strong>Ghi chú:</strong> {thesis.comment}</ListGroup.Item>
                <ListGroup.Item><strong>Năm học:</strong> {thesis.schoolYearId.startYear} - {thesis.schoolYearId.endYear}</ListGroup.Item>
                <ListGroup.Item><strong>Khoa:</strong> {thesis.facultyId.name}</ListGroup.Item>
                <ListGroup.Item><strong>Ngành:</strong> {thesis.majorId.name}</ListGroup.Item>
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
              </ListGroup>
              <div className="button-group mt-3">
                <Button as={Link} onClick={handleBack} variant="outline-dark">Quay lại</Button>
              </div>
            </Card.Body>
          </Card>
        )}
      </div>
    </>
  );
};

export default ThesisDetails;
