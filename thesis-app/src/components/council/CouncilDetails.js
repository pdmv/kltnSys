import { Link, useParams } from "react-router-dom";
import withAuth from "../hoc/withAuth";
import { useCallback, useContext, useEffect, useState } from "react";
import { authApi, endpoints } from "../../configs/APIs";
import { Helmet } from "react-helmet";
import { Alert, Button, Card, ListGroup, Spinner } from "react-bootstrap";
import Title from "../common/Title";
import FormatDateTime from "../common/FormatDateTime";
import CouncilStatusBadge from "../common/CouncilStatusBadge";
import { UserContext } from "../../configs/UserContext";

const mapPositionToLabel = (position) => {
  switch (position) {
    case "president":
      return "Chủ tịch";
    case "secretary":
      return "Thư ký";
    case "critical":
      return "Phản biện";
    default:
      return "Thành viên";
  }
};

const CouncilDetails = () => {
  const { id } = useParams();
  const { user } = useContext(UserContext);
  const [council, setCouncil] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [blocking, setBlocking] = useState(false);
  const [unblocking, setUnblocking] = useState(false);

  const fetchCouncil = useCallback(async () => {
    setLoading(true);
    try {
      let res = await authApi().get(endpoints["council-details"](id));
      setCouncil(res.data);
    } catch (error) {
      console.error(error);
      setError("Không thể tải dữ liệu hội đồng.");
    } finally {
      setLoading(false);
    }
  }, [id]);

  useEffect(() => {
    fetchCouncil();
  }, [fetchCouncil]);

  const blockCouncil = async () => {
    setBlocking(true);
    try {
      await authApi().post(endpoints["block-council"](id));
      setCouncil(prevCouncil => ({ ...prevCouncil, status: "blocked" }));
    } catch (error) {
      console.error(error);
      setError("Không thể khóa hội đồng.");
    } finally {
      setBlocking(false);
    }
  };

  const unblockCouncil = async () => {
    setUnblocking(true);
    try {
      await authApi().post(endpoints["unblock-council"](id));
      setCouncil(prevCouncil => ({ ...prevCouncil, status: "pending" }));
    } catch (error) {
      console.error(error);
      setError("Không thể mở khóa hội đồng.");
    } finally {
      setUnblocking(false);
    }
  };

  const handleDownloadReport = async (thesisId) => {
    setError(null);
    try {
      const response = await authApi().get(endpoints['thesis-report'](thesisId), {
        responseType: 'blob',
      });
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `Thesis_Report_${thesisId}.pdf`);
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    } catch (error) {
      console.error('Error downloading the report:', error);
      setError("Không thể tải báo cáo bảng điểm.");
    }
  };

  return (
    <>
      <Helmet>
        <title>Chi tiết Hội đồng</title>
      </Helmet>
      <div className="council-details-container">
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
      {council && (
        <Card className="council-card mt-4 mb-4">
          <Card.Body>
            <Card.Title className="mb-4"><Title title="Chi tiết " strong="Hội đồng" /></Card.Title>
            <ListGroup variant="flush">
              <ListGroup.Item><strong>ID:</strong> {council.id}</ListGroup.Item>
              <ListGroup.Item><strong>Tên hội đồng:</strong> {council.name}</ListGroup.Item>
              <ListGroup.Item><strong>Ngày họp:</strong> <FormatDateTime date={council.meetingDate} /></ListGroup.Item>
              <ListGroup.Item><strong>Trạng thái:</strong> <CouncilStatusBadge status={council.status} /></ListGroup.Item>
              <ListGroup.Item><strong>Ngày tạo:</strong> <FormatDateTime date={council.createdDate} /></ListGroup.Item>
              <ListGroup.Item><strong>Ngày cập nhật:</strong> <FormatDateTime date={council.updatedDate} /></ListGroup.Item>
              <ListGroup.Item><strong>Khoa:</strong> {council.faculty.name}</ListGroup.Item>
              <ListGroup.Item><strong>Người tạo:</strong> {council.affair.lastName} {council.affair.firstName} - Liên hệ: {council.affair.email}</ListGroup.Item>
            </ListGroup>

            {/* List of Criteria */}
            <Card className="mt-3">
              <Card.Header><strong>Danh sách tiêu chí</strong></Card.Header>
              <ListGroup variant="flush">
                {council.councilCriterionSet.map(criterion => (
                  <ListGroup.Item key={criterion.id}>
                    {criterion.name} - Trọng số: {criterion.weight}
                  </ListGroup.Item>
                ))}
              </ListGroup>
            </Card>

            {/* List of Lecturers */}
            <Card className="mt-3">
              <Card.Header><strong>Danh sách thành viên hội đồng</strong></Card.Header>
              <ListGroup variant="flush">
                {council.councilLecturerSet.map(lecturer => (
                  <ListGroup.Item key={lecturer.id}>
                    {lecturer.lastName} {lecturer.firstName} - Vị trí: {mapPositionToLabel(lecturer.position)}
                  </ListGroup.Item>
                ))}
              </ListGroup>
            </Card>

            {/* List of Theses */}
            <Card className="mt-3">
              <Card.Header><strong>Danh sách khoá luận</strong></Card.Header>
              <ListGroup variant="flush">
                {council.councilThesisSet.map(thesis => (
                  <ListGroup.Item key={thesis.id} className="d-flex justify-content-between align-items-center">
                    <Link to={`/council/${id}/thesis/${thesis.id}/grade`}>
                      {thesis.name}
                    </Link>
                    {council.status === 'blocked' && (
                      <Button variant="outline-dark" onClick={() => handleDownloadReport(thesis.id)}>
                        Xuất bảng điểm
                      </Button>
                    )}
                  </ListGroup.Item>
                ))}
              </ListGroup>
            </Card>

            <div className="button-group mt-3">
              {user.account.role === "AFFAIR" && (<>
                <Link to={`/council/edit/${id}`} className="btn btn-dark">Cập nhật</Link>
                {council.status === 'blocked' ? (
                  <Button variant="success" onClick={unblockCouncil} className="ms-2" disabled={unblocking}>
                    {unblocking ? <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" /> : "Mở khóa"}
                  </Button>
                ) : (
                  <Button variant="danger" onClick={blockCouncil} className="ms-2" disabled={blocking}>
                    {blocking ? <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" /> : "Khóa"}
                  </Button>
                )}
              </>)}
              <Link to={"/council"} className="btn btn-outline-dark ms-2">Quay lại</Link>
            </div>
          </Card.Body>
        </Card>
      )}
    </>
  );
}

export default withAuth(CouncilDetails);