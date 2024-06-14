import { useLocation, useNavigate } from "react-router-dom";
import Title from "../common/Title";
import { useEffect, useState } from "react";
import { authApi, endpoints } from "../../configs/APIs";
import { Spinner, Alert, Container, Row, Col, Card, ListGroup } from "react-bootstrap";
import FormatDate from "../common/FormatDate";

const ThesisDetails = () => {
  const location = useLocation();
  const nav = useNavigate();
  const [thesis, setThesis] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const id = location.state?.selected;

  useEffect(() => {
    if (!id) {
      nav('/');
      return;
    }

    const fetchThesis = async () => {
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
    };

    fetchThesis();
  }, [id, nav]);

  if (!id) {
    return null; // Trả về null nếu không có id, hoặc có thể trả về một thông báo lỗi hoặc điều hướng khác
  }

  return (
    <Container>
      {loading && (
        <div
          style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            height: '200px',
          }}
        >
          <Spinner animation="border" variant="primary" />
        </div>
      )}
      {error && <Alert variant="danger">{error}</Alert>}
      {thesis && (
        <>
          <Title title="Chi tiết khoá luận" strong={thesis.name} />
          <Card className="mt-3">
            <Card.Body>
              <Row>
                <Col md={6}>
                  <ListGroup variant="flush">
                    <ListGroup.Item><strong>ID:</strong> {thesis.id}</ListGroup.Item>
                    <ListGroup.Item><strong>Tên luận văn:</strong> {thesis.name}</ListGroup.Item>
                    <ListGroup.Item><strong>Ngày bắt đầu thực hiện:</strong> <FormatDate date={thesis.startDate} /></ListGroup.Item>
                    <ListGroup.Item><strong>Ngày kết thúc thực hiện:</strong> <FormatDate date={thesis.endDate} /></ListGroup.Item>
                    <ListGroup.Item><strong>Ngày hết hạn nộp:</strong> <FormatDate date={thesis.expDate} /></ListGroup.Item>
                    <ListGroup.Item><strong>Trạng thái:</strong> {thesis.status}</ListGroup.Item>
                    <ListGroup.Item><strong>Ghi chú:</strong> {thesis.comment}</ListGroup.Item>
                    <ListGroup.Item><strong>Năm học:</strong> {thesis.schoolYearId.startYear} - {thesis.schoolYearId.endYear}</ListGroup.Item>
                  </ListGroup>
                </Col>
                <Col md={6}>
                  <ListGroup variant="flush">
                    <ListGroup.Item>
                      <strong>Giảng viên hướng dẫn:</strong>
                      <ul>
                        {thesis.thesisLecturerSet.map((lecturer) => (
                          <li key={lecturer.id}>
                            {lecturer.lastName} {lecturer.firstName} - Liên hệ: {lecturer.email}
                          </li>
                        ))}
                      </ul>
                    </ListGroup.Item>
                    <ListGroup.Item>
                      <strong>Sinh viên thực hiện:</strong>
                      <ul>
                        {thesis.thesisStudentSet.map((student, index) => (
                          <li key={index}>
                            {student.lastName} {student.firstName} - Liên hệ: {student.email}
                          </li>
                        ))}
                      </ul>
                    </ListGroup.Item>
                    <ListGroup.Item>
                      <strong>Giảng viên phản biện:</strong> 
                      {thesis.criticalLecturerId.lastName} {thesis.criticalLecturerId.firstName} - Liên hệ: {thesis.criticalLecturerId.email}
                    </ListGroup.Item>
                    <ListGroup.Item>
                      <strong>Người tạo:</strong> 
                      {thesis.affairId.lastName} {thesis.affairId.firstName} - Liên hệ: {thesis.affairId.email}
                    </ListGroup.Item>
                  </ListGroup>
                </Col>
              </Row>
            </Card.Body>
          </Card>
        </>
      )}
    </Container>
  );
};

export default ThesisDetails;
