import { useNavigate, useParams } from "react-router-dom";
import withAuth from "../hoc/withAuth";
import { useCallback, useContext, useEffect, useState } from "react";
import { authApi, endpoints } from "../../configs/APIs";
import { Helmet } from "react-helmet";
import { Alert, Button, Card, Form, ListGroup, Spinner } from "react-bootstrap";
import Title from "../common/Title";
import { UserContext } from "../../configs/UserContext";

const ThesisGrading = () => {
  const { id, thesisId } = useParams();
  const { user } = useContext(UserContext);
  const [thesis, setThesis] = useState(null);
  const [criteria, setCriteria] = useState([]);
  const [scores, setScores] = useState({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const nav = useNavigate();

  const fetchThesisDetails = useCallback(async () => {
    setLoading(true);
    try {
      const thesisRes = await authApi().get(endpoints["thesis-details"](thesisId));
      setThesis(thesisRes.data);

      const criteriaRes = await authApi().get(endpoints["council-criterions"](id));
      setCriteria(criteriaRes.data);

      // Fetch existing scores
      const marksRes = await authApi().get(`${endpoints["council-marks"]}?councilId=${id}&thesisId=${thesisId}&lecturerId=${user.id}`);
      const existingScores = Array.isArray(marksRes.data.scores) ? marksRes.data.scores.reduce((acc, mark) => {
        acc[mark.id] = mark.score;
        return acc;
      }, {}) : {};

      // Initialize scores with existing scores or default values
      const initialScores = {};
      criteriaRes.data.forEach(criterion => {
        initialScores[criterion.id] = existingScores[criterion.id] || 0;
      });
      setScores(initialScores);
    } catch (error) {
      console.error(error);
      setError("Không thể tải dữ liệu.");
    } finally {
      setLoading(false);
    }
  }, [id, thesisId, user.id]);

  const checkRole = useCallback(() => {
    if (user.account.role !== 'LECTURER') {
      nav('/forbidden');
    }
  }, [user.account.role, nav]);

  useEffect(() => {
    checkRole();
    fetchThesisDetails();
  }, [checkRole, fetchThesisDetails]);

  const handleScoreChange = (criterionId, score) => {
    setScores(prevScores => ({
      ...prevScores,
      [criterionId]: score,
    }));
  };

  const handleSubmit = async () => {
    try {
      const payload = {
        thesisId: parseInt(thesisId, 10),
        scores: Object.entries(scores).map(([id, score]) => ({ id: parseInt(id, 10), score: parseFloat(score) }))
      };
      await authApi().post(endpoints["submit-scores"](id), payload);
      alert("Chấm điểm thành công!");
      nav(`/council/${id}`);
    } catch (error) {
      console.error(error);
      setError("Không thể gửi điểm.");
    }
  };

  const handleDownload = (url) => {
    fetch(url)
      .then(response => {
        if (response.status === 403) {
          nav("/forbidden");
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
        <title>Chấm Điểm Khoá Luận</title>
      </Helmet>
      <div className="thesis-grading-container">
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
        </div>
      </div>
      {thesis && criteria.length > 0 && (
        <Card className="thesis-card mt-4 mb-4">
          <Card.Body>
            <Card.Title className="mb-4"><Title title="Chấm điểm " strong="Khoá Luận" /></Card.Title>
            <ListGroup variant="flush">
              <ListGroup.Item><strong>Tên khoá luận:</strong> {thesis.name}</ListGroup.Item>
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
              <ListGroup.Item>
                <strong>Tệp báo cáo:</strong> <Button variant="outline-dark" size="sm" onClick={() => handleDownload(thesis.reportFile)}>Tải xuống</Button>
              </ListGroup.Item>
            </ListGroup>

            <Card className="mt-3">
              <Card.Header><strong>Tiêu chí chấm điểm</strong></Card.Header>
              <ListGroup variant="flush">
                {criteria.map(criterion => (
                  <ListGroup.Item key={criterion.id}>
                    <Form.Group>
                      <Form.Label>{criterion.name} - Trọng số: {criterion.weight}</Form.Label>
                      <Form.Control
                        type="number"
                        value={scores[criterion.id]}
                        onChange={e => handleScoreChange(criterion.id, e.target.value)}
                        min="0"
                        max="10"
                        step="0.1"
                      />
                    </Form.Group>
                  </ListGroup.Item>
                ))}
              </ListGroup>
            </Card>

            <div className="button-group mt-3">
              <Button variant="dark" onClick={handleSubmit}>Gửi điểm</Button>
              <Button variant="outline-dark" className="ms-2" onClick={() => nav(`/council/${id}`)}>Quay lại</Button>
            </div>
          </Card.Body>
        </Card>
      )}
    </>
  );
}

export default withAuth(ThesisGrading);