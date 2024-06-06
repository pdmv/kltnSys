import { useContext, useEffect, useState } from "react";
import { Button, Col, Container, Row, Table, Alert, Spinner } from "react-bootstrap";
import { UserContext } from "../../configs/UserContext";
import Title from "../common/Title";
import { authApi, endpoints } from "../../configs/APIs";
import FormatDate from "../common/FormatDate";
import StatusBadge from "../common/StatusBadge";
import { useNavigate } from "react-router-dom";

const Thesis = () => {
  const { user } = useContext(UserContext);
  const [theses, setTheses] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const nav = useNavigate();

  const fetchTheses = async () => {
    if (user) {
      setLoading(true);
      try {
        let res = await authApi().get(`${endpoints.thesis}?facultyId=${user.account.facultyId}`);
        setTheses(res.data);
      } catch (error) {
        console.error(error);
        setError("Không thể tải dữ liệu khoá luận.");
      } finally {
        setLoading(false);
      }
    }
  }

  const getStatus = (status) => {
    switch (status) {
      case 'in_progress':
        return <StatusBadge status="Đang thực hiện" bg="success" />;
      case 'submitted':
        return <StatusBadge status="Đã nộp" bg="info" />;
      case 'under_review':
        return <StatusBadge status="Đang bảo vệ" bg="warning" />;
      case 'defended':
        return <StatusBadge status="Đã bảo vệ" bg="secondary" />;
      case 'canceled':
        return <StatusBadge status="Đã huỷ" bg="dark" />;
      default:
        return '';
    }
  };

  const handleDetail = (id) => {
    nav("/thesis-details", { state: { selected: id } });
  };

  useEffect(() => {
    fetchTheses();
  }, [user]);

  return (
    <Container>
      <Row>
        <Col>
          <Title title="Danh sách" strong="Khoá luận" />
          {loading && (
            <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '200px' }}>
              <Spinner animation="border" variant="primary" />
            </div>
          )}
          {error && <Alert variant="danger">{error}</Alert>}
          {!loading && !error && theses.length === 0 && <Alert variant="warning">Không có dữ liệu khoá luận.</Alert>}
          {!loading && theses.length > 0 && (
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
                    <td>{getStatus(item.status)}</td>
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

export default Thesis;
