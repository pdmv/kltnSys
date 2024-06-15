import { useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { authApi, endpoints } from "../../configs/APIs";
import { Spinner, Alert, Container, Row, Col, Card, ListGroup } from "react-bootstrap";
import FormatDate from "../common/FormatDate";
import Title from "../common/Title";

const CriterionDetails = () => {
  const location = useLocation();
  const navigate = useNavigate(); // Renamed nav to navigate
  const [criterion, setCriterion] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const id = location.state?.selected;

  useEffect(() => {
    if (!id) {
      navigate('/'); // Use navigate instead of nav
      return;
    }

    const fetchCriterion = async () => {
      setLoading(true);
      try {
        let res = await authApi().get(endpoints["criterion-details"](id));
        setCriterion(res.data);
      } catch (error) {
        console.error(error);
        setError("Không thể tải dữ liệu tiêu chí.");
      } finally {
        setLoading(false);
      }
    };

    fetchCriterion();
  }, [id, navigate]);

  // Handle case when id is not available
  if (!id) {
    return null; // or you can render a message here
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
      {criterion && (
        <>
          <Title title="Chi tiết tiêu chí" strong={criterion.name} />
          <Card className="mt-3">
            <Card.Body>
              <Row>
                <Col md={6}>
                  <ListGroup variant="flush">
                    <ListGroup.Item><strong>ID:</strong> {criterion.id}</ListGroup.Item>
                    <ListGroup.Item><strong>Tên tiêu chí:</strong> {criterion.name}</ListGroup.Item>
                    <ListGroup.Item><strong>Ngày tạo:</strong> <FormatDate date={criterion.createdDate} /></ListGroup.Item>
                    <ListGroup.Item><strong>Ngày cập nhật:</strong> <FormatDate date={criterion.updatedDate} /></ListGroup.Item>
                    <ListGroup.Item><strong>Trạng thái:</strong> {criterion.active ? "Hoạt động" : "Không hoạt động"}</ListGroup.Item>
                    <ListGroup.Item><strong>Mô tả:</strong> {criterion.description}</ListGroup.Item>
                  </ListGroup>
                </Col>
                <Col md={6}>
                  <ListGroup variant="flush">
                    <ListGroup.Item>
                      <strong>Người tạo:</strong> 
                      {criterion.affairId.lastName} {criterion.affairId.firstName} - Liên hệ: {criterion.affairId.email}
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

export default CriterionDetails;
