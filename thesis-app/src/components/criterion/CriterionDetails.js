import { Link, useNavigate, useParams } from "react-router-dom";
import withAuth from "../hoc/withAuth";
import { useCallback, useEffect, useState } from "react";
import { authApi, endpoints } from "../../configs/APIs";
import { Helmet } from "react-helmet";
import { Alert, Button, Card, ListGroup, Spinner } from "react-bootstrap";
import ActiveStatusBadge from "../common/ActiveStatusBadge";
import Title from "../common/Title";
import FormatDateTime from "../common/FormatDateTime";

const CriterionDetails = () => {
  const { id } = useParams();
  const [c, setC] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const nav = useNavigate();

  const fetchCriterions = useCallback(async () => {
    setLoading(true);
    try {
      let res = await authApi().get(endpoints["criterion-details"](id));
      setC(res.data);
    } catch (error) {
      console.error(error);
      setError("Không thể tải dữ liệu khoá luận.");
    } finally {
      setLoading(false);
    }
  }, [id]);

  useEffect(() => {
    fetchCriterions();
  }, [fetchCriterions]);

  const handleBack = () => {
    nav(-1);
  }

  return (
    <>
      <Helmet>
        <title>Chi tiết Tiêu chí</title>
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
      </div>
      {c && (
        <Card className="thesis-card mt-4 mb-4">
          <Card.Body>
            <Card.Title className="mb-4"><Title title="Chi tiết " strong="Tiêu chí" /></Card.Title>
            <ListGroup variant="flush">
              <ListGroup.Item><strong>ID:</strong> {c.id}</ListGroup.Item>
              <ListGroup.Item><strong>Tên tiêu chí:</strong> {c.name}</ListGroup.Item>
              <ListGroup.Item><strong>Mô tả:</strong> {c.description}</ListGroup.Item>
              <ListGroup.Item><strong>Khoa:</strong> {c.faculty.name}</ListGroup.Item>
              <ListGroup.Item><strong>Ngày tạo:</strong> <FormatDateTime date={c.createdDate} /></ListGroup.Item>
              <ListGroup.Item><strong>Ngày cập nhật:</strong> <FormatDateTime date={c.updatedDate} /></ListGroup.Item>
              <ListGroup.Item><strong>Trạng thái:</strong> <ActiveStatusBadge status={c.active} /></ListGroup.Item>
              <ListGroup.Item>
                <strong>Người tạo:</strong> {c.affair.lastName} {c.affair.firstName} - Liên hệ: {c.affair.email}
              </ListGroup.Item>
            </ListGroup>
            <div className="button-group mt-3">
              <Link to={`/criterion/edit/${id}`} className="btn btn-dark">Cập nhật</Link>
              <Button as={Link} onClick={handleBack} variant="outline-dark" className="ms-2">Quay lại</Button>
            </div>
          </Card.Body>
        </Card>
      )}
    </>
  );
}

export default withAuth(CriterionDetails);