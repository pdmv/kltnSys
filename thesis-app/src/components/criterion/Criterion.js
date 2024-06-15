import { useContext, useEffect, useState } from "react";
import { Button, Col, Container, Row, Table, Alert, Spinner } from "react-bootstrap";
import { UserContext } from "../../configs/UserContext";
import Title from "../common/Title";
import { authApi, endpoints } from "../../configs/APIs";
import { useNavigate } from "react-router-dom";

const Criterion = () => {
  const { user } = useContext(UserContext);
  const [criteria, setCriteria] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const nav = useNavigate();

  const fetchCriteria = async () => {
    if (user) {
      setLoading(true);
      try {
        let res = await authApi().get(endpoints.criteria);
        setCriteria(res.data);
      } catch (error) {
        console.error(error);
        setError("Không thể tải dữ liệu tiêu chí.");
      } finally {
        setLoading(false);
      }
    }
  }

  const handleDetail = (id) => {
    nav("/criterion-details", { state: { selected: id } });
  };

  useEffect(() => {
    fetchCriteria();
  }, [user]);

  return (
    <Container>
      <Row>
        <Col>
          <Title title="Danh sách" strong="Tiêu chí" />
          {loading && (
            <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '200px' }}>
              <Spinner animation="border" variant="primary" />
            </div>
          )}
          {error && <Alert variant="danger">{error}</Alert>}
          {!loading && !error && criteria.length === 0 && <Alert variant="warning">Không có dữ liệu tiêu chí.</Alert>}
          {!loading && criteria.length > 0 && (
            <Table hover>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Tên tiêu chí</th>
                  <th>Ngày tạo</th>
                  <th>Ngày cập nhật</th>
                  <th>Trạng thái</th>
                  <th>Hành động</th>
                </tr>
              </thead>
              <tbody>
                {criteria.map((item, index) => (
                  <tr key={index}>
                    <td>{item.id}</td>
                    <td>{item.name}</td>
                    <td>{item.createdDate}</td>
                    <td>{item.updatedDate}</td>
                    <td>{item.active ? "Hoạt động" : "Không hoạt động"}</td>
                    <td>
                      <Button variant="outline-dark" onClick={() => handleDetail(item.id)}>Chi tiết</Button>
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

export default Criterion;
