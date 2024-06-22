import { useCallback, useContext, useEffect, useState } from "react";
import { UserContext } from "../../configs/UserContext";
import { Alert, Col, Container, Row, Spinner, Table, Button, FloatingLabel, Form } from "react-bootstrap";
import { Helmet } from "react-helmet";
import Title from "../common/Title";
import { Link, useNavigate } from "react-router-dom";
import { authApi, endpoints } from "../../configs/APIs";
import withAuth from "../hoc/withAuth";
import FormatDateTime from "../common/FormatDateTime";
import CouncilStatusBadge from "../common/CouncilStatusBadge";

const Council = () => {
  const { user } = useContext(UserContext);
  const [councils, setCouncils] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);
  const [schoolYears, setSchoolYears] = useState([]);
  const [selectedSchoolYear, setSelectedSchoolYear] = useState('');
  const nav = useNavigate();

  const checkRole = useCallback(() => {
    if (user.account.role !== 'AFFAIR' && user.account.role !== 'LECTURER') {
      nav('/forbidden');
    }
  }, [user.account.role, nav]);

  const fetchSchoolYears = useCallback(async () => {
    try {
      const res = await authApi().get(endpoints["school-years"]);
      setSchoolYears(res.data);
      if (res.data.length > 0) {
        setSelectedSchoolYear(res.data[0].id);
      }
    } catch (error) {
      console.error(error);
      setError("Không thể tải danh sách năm học.");
    }
  }, []);

  const fetchCouncils = useCallback(async () => {
    if (user && selectedSchoolYear) {
      setLoading(true);
      try {
        let res;
        if (user.account.role === "AFFAIR") {
          res = await authApi().get(`${endpoints.council}?schoolYearId=${selectedSchoolYear}&facultyId=${user.faculty.id}&page=${page}`);
        } else if (user.account.role === "LECTURER") {
          res = await authApi().get(`${endpoints.council}?schoolYearId=${selectedSchoolYear}&lecturerId=${user.id}&page=${page}`);
        }

        if (page === 1) {
          setCouncils(res.data);
        } else {
          setCouncils(prevCouncils => [...prevCouncils, ...res.data]);
        }

        if (res.data.length < 10 || res.data.length === 0) {
          setHasMore(false);
        }
      } catch (error) {
        console.error(error);
        setError("Không thể tải dữ liệu.");
      } finally {
        setLoading(false);
      }
    }
  }, [user, page, selectedSchoolYear]);

  useEffect(() => {
    checkRole();
    fetchSchoolYears();
  }, [checkRole, fetchSchoolYears]);

  useEffect(() => {
    fetchCouncils();
  }, [fetchCouncils, selectedSchoolYear]);

  const handleLoadMore = () => {
    setPage(prevPage => prevPage + 1);
  };

  const handleSchoolYearChange = (e) => {
    setSelectedSchoolYear(e.target.value);
    setPage(1);
    setHasMore(true);
  };

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
          <FloatingLabel controlId="floatingSelect" label="Năm học" className="mb-4">
            <Form.Select aria-label="Chọn năm học" value={selectedSchoolYear} onChange={handleSchoolYearChange}>
              {schoolYears.map((year) => (
                <option key={year.id} value={year.id}>{year.startYear} - {year.endYear}</option>
              ))}
            </Form.Select>
          </FloatingLabel>
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
          {!loading && !error && councils.length === 0 && <Alert variant="warning">Không có dữ liệu.</Alert>}
          {!loading && councils.length > 0 && (
            <>
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

export default withAuth(Council);