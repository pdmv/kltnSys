import { useCallback, useContext, useEffect, useState } from "react";
import { Col, Container, Row, Table, Alert, Spinner, Button } from "react-bootstrap";
import { UserContext } from "../../configs/UserContext";
import Title from "../common/Title";
import { authApi, endpoints } from "../../configs/APIs";
import FormatDate from "../common/FormatDate";
import { Link, useNavigate } from "react-router-dom";
import { Helmet } from "react-helmet";
import ThesisStatusBadge from "../common/ThesisStatusBadge";
import withAuth from "../hoc/withAuth";

const Thesis = () => {
  const { user } = useContext(UserContext);
  const [theses, setTheses] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);
  const nav = useNavigate();

  const checkRole = useCallback(() => {
    if (user.account.role !== 'AFFAIR' && user.account.role !== 'STUDENT') {
      nav('/forbidden');
    }
  }, [user.account.role, nav]);

  const fetchTheses = useCallback(async () => {
    if (user) {
      setLoading(true);
      try {
        let res;
        if (user.account.role === "AFFAIR") {
          res = await authApi().get(`${endpoints.thesis}?&page=${page}&facultyId=${user.faculty.id}`);
        } else if (user.account.role === "STUDENT") {
          res = await authApi().get(`${endpoints.thesis}?studentId=${user.id}&page=${page}`);
        }

        if (res.data.length > 0) {
          if (page === 1) {
            setTheses(res.data);
          } else {
            setTheses(prevTheses => [...prevTheses, ...res.data]);
          }
          if (res.data.length < 10) {
            setHasMore(false);
          }
        } else {
          setHasMore(false);
        }
      } catch (error) {
        console.error(error);
        setError("Không thể tải dữ liệu khoá luận.");
      } finally {
        setLoading(false);
      }
    }
  }, [user, page]);

  useEffect(() => {
    checkRole();
    fetchTheses();
  }, [checkRole, fetchTheses]);

  const handleLoadMore = () => {
    setPage(prevPage => prevPage + 1);
  };

  return (
    <Container>
      <Helmet>
        <title>Danh sách Khoá luận</title>
      </Helmet>
      <Row>
        <Col>
          <Title title="Danh sách" strong="Khoá luận" />
          {user.account.role === "AFFAIR" ?
            <div className="d-flex justify-content-center align-items-center mb-4">
              <Link className="btn btn-dark" to="/thesis/create">
                <>Thêm <strong>Khoá luận</strong></>
              </Link>
            </div> : <></>}
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
          {!loading && !error && theses.length === 0 && <Alert variant="warning">Không có dữ liệu khoá luận.</Alert>}
          {!loading && theses.length > 0 && (
            <>
              <Table hover>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Tên khoá luận</th>
                    <th>Ngày bắt đầu thực hiện</th>
                    <th>Ngày kết thúc thực hiện</th>
                    <th>Ngày hết hạn nộp</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                  </tr>
                </thead>
                <tbody>
                  {theses.map((item, index) => (
                    <tr key={index}>
                      <td>{item.id}</td>
                      <td>{item.name}</td>
                      <td><FormatDate date={item.startDate} /></td>
                      <td><FormatDate date={item.endDate} /></td>
                      <td><FormatDate date={item.expDate} /></td>
                      <td><ThesisStatusBadge status={item.status} /></td>
                      <td>
                        <Link to={`/thesis/${item.id}`} className="btn btn-outline-dark">Chi tiết</Link>
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

export default withAuth(Thesis);