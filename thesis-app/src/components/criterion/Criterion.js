import { useCallback, useContext, useEffect, useState } from "react";
import ActiveStatusBadge from "../common/ActiveStatusBadge";
import withAuth from "../hoc/withAuth";
import { UserContext } from "../../configs/UserContext";
import { authApi, endpoints } from "../../configs/APIs";
import { Alert, Col, Container, Row, Spinner, Table, Button } from "react-bootstrap";
import { Helmet } from "react-helmet";
import Title from "../common/Title";
import { Link } from "react-router-dom";

const Criterion = () => {
  const { user } = useContext(UserContext);
  const [criterions, setCriterions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);

  const fetchCriterions = useCallback(async () => {
    if (user && hasMore) {
      setLoading(true);
      try {
        const res = await authApi().get(`${endpoints.criterion}?facultyId=${user.faculty.id}&page=${page}`);
        if (res.data.length > 0) {
          if (page === 1) {
            setCriterions(res.data);
          } else {
            setCriterions(prevCriterions => [...prevCriterions, ...res.data]);
          }
          if (res.data.length < 10) {
            setHasMore(false);
          }
        } else {
          setHasMore(false);
        }
      } catch (error) {
        console.error(error);
        setError("Không thể tải dữ liệu.");
      } finally {
        setLoading(false);
      }
    }
  }, [user, page, hasMore]);

  useEffect(() => {
    fetchCriterions();
  }, [fetchCriterions]);

  const handleLoadMore = () => {
    setPage(prevPage => prevPage + 1);
  };

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
          {loading && page === 1 && (
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
            <>
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
              <div className="mb-3 d-flex justify-content-center align-items-center">
                <Button variant="outline-dark" onClick={handleLoadMore} disabled={!hasMore || loading}>
                  {loading ? <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" /> : 'Tải thêm'}
                </Button>
              </div>
            </>
          )}
        </Col>
      </Row>
    </Container>
  );
}

export default withAuth(Criterion);