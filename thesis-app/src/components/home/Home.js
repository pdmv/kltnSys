import { Helmet } from "react-helmet";
import { Container, Row, Col, Button, Card } from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import './Home.css';  // Thêm đường dẫn đến file CSS tùy chỉnh

const Home = () => {
  return (
    <>
      <Helmet>
        <title>Trang chủ</title>
      </Helmet>

      <Container fluid className="bg text-center p-5 mt-5">
        <h1 className="display-4">Hệ thống quản lý khoá luận</h1>
        <p className="lead">Nền tảng hỗ trợ quản lý và theo dõi khoá luận một cách hiệu quả và tiện lợi.</p>
        <Button variant="dark" size="lg">Bắt đầu ngay</Button>
      </Container>

      <Container className="mt-5 mb-5">
        <Row className="d-flex align-items-stretch">
          <Col md={4} className="mb-4">
            <Card className="h-100">
              <Card.Body>
                <Card.Title>Quản lý khoá luận</Card.Title>
                <Card.Text>
                  Dễ dàng quản lý thông tin khoá luận của sinh viên, bao gồm tiến độ và kết quả.
                </Card.Text>
                <Button variant="dark">Tìm hiểu thêm</Button>
              </Card.Body>
            </Card>
          </Col>
          <Col md={4} className="mb-4">
            <Card className="h-100">
              <Card.Body>
                <Card.Title>Quản lý tiêu chí</Card.Title>
                <Card.Text>
                  Quản lý các tiêu chí để thêm vào hội đồng, bao gồm thêm, sửa, cập nhật tiêu chí.
                </Card.Text>
                <Button variant="dark">Tìm hiểu thêm</Button>
              </Card.Body>
            </Card>
          </Col>
          <Col md={4} className="mb-4">
            <Card className="h-100">
              <Card.Body>
                <Card.Title>Quản lý hội đồng</Card.Title>
                <Card.Text>
                  Quản lý việc thành lập hội đồng bảo vệ khoá luận, dễ dàng cập nhật các thay đổi từ hội đồng.
                </Card.Text>
                <Button variant="dark">Tìm hiểu thêm</Button>
              </Card.Body>
            </Card>
          </Col>
        </Row>
        <Row className="d-flex align-items-stretch">
          <Col md={4} className="mb-4">
            <Card className="h-100">
              <Card.Body>
                <Card.Title>Thống kê và báo cáo</Card.Title>
                <Card.Text>
                  Xem các thống kê và báo cáo chi tiết về tình hình khoá luận của khoa.
                </Card.Text>
                <Button variant="dark">Tìm hiểu thêm</Button>
              </Card.Body>
            </Card>
          </Col>
          <Col md={4} className="mb-4">
            <Card className="h-100">
              <Card.Body>
                <Card.Title>Quản lý thông tin cá nhân người dùng</Card.Title>
                <Card.Text>
                  Dễ dàng quản lý thông tin của mình, bao gồm cập nhật ảnh đại diện.
                </Card.Text>
                <Button variant="dark">Tìm hiểu thêm</Button>
              </Card.Body>
            </Card>
          </Col>
          <Col md={4} className="mb-4">
            <Card className="h-100">
              <Card.Body>
                <Card.Title>Quản lý tài khoản</Card.Title>
                <Card.Text>
                  Thay đổi mật khẩu tài khoản cá nhân bất cứ lúc nào.
                </Card.Text>
                <Button variant="dark">Tìm hiểu thêm</Button>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </>
  );
}

export default Home;