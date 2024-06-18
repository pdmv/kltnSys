import { useCallback, useContext, useEffect, useState } from "react";
import ActiveStatusBadge from "../common/ActiveStatusBadge";
import withAuth from "../hoc/withAuth";
import { UserContext } from "../../configs/UserContext";
import { authApi, endpoints } from "../../configs/APIs";
import { Alert, Col, Container, Row, Spinner, Table } from "react-bootstrap";
import { Helmet } from "react-helmet";
import Title from "../common/Title";
import { Link } from "react-router-dom";

const Criterion = () => {
  const { user } = useContext(UserContext);
  const [criterions, setCriterions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchCriterions = useCallback(async () => {
    if (user) {
      setLoading(true);
      try {
        let res = await authApi().get(`${endpoints.criterion}?facultyId=${user.account.facultyId}`);
        setCriterions(res.data);
      } catch (error) {
        console.error(error);
        setError("Không thể tải dữ liệu.");
      } finally {
        setLoading(false);
      }
    }
  }, [user]);

  useEffect(() => {
    fetchCriterions();
  }, [fetchCriterions]);

  return (
    <Container>
      <Helmet>
        <title>Danh sách Tiêu chí</title>
      </Helmet>
      <Row>
        <Col>
          <Title title="Danh sách" strong="Tiêu chí" />
          <div className="d-flex justify-content-center align-items-center mb-4">
            <Link className="btn btn-dark" to="/criterion/create">
              <>Thêm <strong>Tiêu chí</strong></>
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
          {!loading && !error && criterions.length === 0 && <Alert variant="warning">Không có dữ liệu.</Alert>}
          {!loading && criterions.length > 0 && (
            <Table hover>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Tên tiêu chí</th>
                  <th>Người tạo</th>
                  <th>Trạng thái</th>
                  <th>Hành động</th>
                </tr>
              </thead>
              <tbody>
                {criterions.map((item, index) => (
                  <tr key={index}>
                    <td>{item.id}</td>
                    <td>{item.name}</td>
                    <td>{item.affair.lastName} {item.affair.firstName}</td>
                    <td><ActiveStatusBadge status={item.active} /></td>
                    <td>
                      <>
                        <Link to={`/criterion/edit/${item.id}`} className="btn btn-outline-dark">Cập nhật</Link>
                        <Link to={`/criterion/${item.id}`} className="btn btn-outline-dark ms-2">Chi tiết</Link>
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

export default withAuth(Criterion);