import { useCallback, useContext, useEffect, useState } from "react";
import { UserContext } from "../../configs/UserContext";
import { Alert, Col, Container, Row, Spinner, Table } from "react-bootstrap";
import { Helmet } from "react-helmet";
import Title from "../common/Title";
import { Link } from "react-router-dom";
import { authApi, endpoints } from "../../configs/APIs";
import withAuth from "../hoc/withAuth";
import FormatDateTime from "../common/FormatDateTime";
import CouncilStatusBadge from "../common/CouncilStatusBadge";

const Council = () => {
  const { user } = useContext(UserContext);
  const [councils, setCouncils] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchCouncils = useCallback(async () => {
    if (user) {
      setLoading(true);
      try {
        let res = await authApi().get(`${endpoints['council']}?facultyId=${user.faculty.id}`);
        setCouncils(res.data);
      } catch (error) {
        console.error(error);
        setError("Không thể tải dữ liệu.");
      } finally {
        setLoading(false);
      }
    }
  }, [user]);

  useEffect(() => {
    fetchCouncils();
  }, [fetchCouncils]);

  return (
    <Container>
      <Helmet>
        <title>Danh sách Hội đồng</title>
      </Helmet>
      <Row>
        <Col>
          <Title title="Danh sách" strong="Hội đồng" />
          <div className="d-flex justify-content-center align-items-center mb-4">
            <Link className="btn btn-dark" to="/council/create">
              <>Thêm <strong>Hội đồng</strong></>
            </Link>
          </div>
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
          {!loading && !error && councils.length === 0 && <Alert variant="warning">Không có dữ liệu.</Alert>}
          {!loading && councils.length > 0 && (
            <Table hover>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Tên hội đồng</th>
                  <th>Năm học</th>
                  <th>Ngày họp</th>
                  <th>Trạng thái</th>
                  <th>Hành động</th>
                </tr>
              </thead>
              <tbody>
                {councils.map((item, index) => (
                  <tr key={index}>
                    <td>{item.id}</td>
                    <td>{item.name}</td>
                    <td>{item.schoolYearId.startYear} - {item.schoolYearId.endYear}</td>
                    <td><FormatDateTime date={item.meetingDate} /></td>
                    <td><CouncilStatusBadge status={item.status} /></td>
                    <td>
                      <>
                        <Link to={`/council/${item.id}`} className="btn btn-outline-dark ms-2">Chi tiết</Link>
                      </>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          )}
        </Col>
      </Row>
    </Container>
  );
}

export default withAuth(Council);