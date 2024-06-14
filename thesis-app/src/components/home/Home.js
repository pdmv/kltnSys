import React from 'react';
import { Helmet } from 'react-helmet';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import '@fortawesome/fontawesome-free/css/all.min.css';

const Home = () => {
  const navigate = useNavigate();

  const cards = [
    { title: 'Niên khóa', text: 'Quản lý niên khóa học.', icon: 'calendar-alt', url: '/admins/school-years' },
    { title: 'Khoa', text: 'Quản lý các khoa đào tạo.', icon: 'university', url: '/admins/faculties' },
    { title: 'Ngành', text: 'Quản lý các ngành học.', icon: 'graduation-cap', url: '/admins/majors' },
    { title: 'Lớp', text: 'Quản lý các lớp học.', icon: 'chalkboard', url: '/admins/classes' },
    { title: 'Giảng viên', text: 'Quản lý các giảng viên.', icon: 'user-tie', url: '/admins/lecturers' },
    { title: 'Giáo vụ', text: 'Quản lý các giáo vụ.', icon: 'users', url: '/admins/staff' },
    { title: 'Sinh viên', text: 'Quản lý các sinh viên.', icon: 'user-graduate', url: '/admins/students' },
    { title: 'Quản trị viên', text: 'Quản lý các quản trị viên.', icon: 'user-shield', url: '/admins' }
  ];

  return (
    <>
      <Helmet>
        <title>Trang chủ</title>
      </Helmet>
      <Container className="mt-5">
        <h1 className="text-center mb-4">Chào mừng đến với <strong>TRANG QUẢN TRỊ</strong></h1>
        <Row className="mt-5">
          {cards.map((card, index) => (
            <Col md={3} key={index} className="mb-4">
              <Card className="shadow-sm border-0">
                <Card.Body className="text-center">
                  <i className={`fas fa-${card.icon} fa-3x mb-3`}></i>
                  <Card.Title>{card.title}</Card.Title>
                  <Card.Text>{card.text}</Card.Text>
                  <Button variant="outline-dark" onClick={() => navigate(card.url)}>Truy cập</Button>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      </Container>
    </>
  );
}

export default Home;
