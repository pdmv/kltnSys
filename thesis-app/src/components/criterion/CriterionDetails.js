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
    const fetchCriterion = async () => {
      if (!id) {
        navigate('/'); // Redirect to home if id is not available
        return;
      }

      setLoading(true);
      try {
        let res = await authApi().get(endpoints["criterion-details"](id));
        setCriterion(res.data); // Assuming res.data is the correct structure returned by your API
      } catch (error) {
        console.error(error);
        setError("Không thể tải dữ liệu tiêu chí."); // Set error message on fetch failure
      } finally {
        setLoading(false);
      }
    };

    fetchCriterion(); // Fetch criterion details when id or navigate changes
  }, [id, navigate]);

  // Handle case when id is not available or data is loading
  if (!id || loading) {
    return (
      <Container>
        <Spinner animation="border" variant="primary" className="mt-5" />
      </Container>
    );
  }

  // Handle case when there is an error fetching data
  if (error) {
    return (
      <Container>
        <Alert variant="danger" className="mt-5">
          {error}
        </Alert>
      </Container>
    );
  }

  // Render criterion details if data is available
  return criterion && (
    <Container>
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
                  {criterion.affairId?.lastName} {criterion.affairId?.firstName} - Liên hệ: {criterion.affairId?.email}
                </ListGroup.Item>
              </ListGroup>
            </Col>
          </Row>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default CriterionDetails;
