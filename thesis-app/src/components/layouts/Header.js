import { useContext } from "react";
import { Image, Nav, Navbar, NavDropdown } from "react-bootstrap";
import { Link } from "react-router-dom";
import { UserContext } from "../../configs/UserContext";
import cookie from 'react-cookies';

const getGreeting = () => {
  const currentHour = new Date().getHours();
  if (currentHour <= 10) {
    return "Buổi sáng năng động!";
  }
  if (currentHour <= 12) {
    return "Buổi trưa thư giản!";
  }
  if (currentHour <= 18) {
    return "Buổi chiều vui vẻ!";
  }
  if (currentHour <= 21) {
    return "Buổi tối hạnh phúc!";
  }
  return "Đêm đã khuya rồi!";
};

const Header = () => {
  const { user, dispatch } = useContext(UserContext);

  const handleLogout = () => {
    cookie.remove('token');
    cookie.remove('user');
    dispatch({ type: "logout" });
  };

  return (
    <Navbar expand="lg" className="bg-light" style={{ padding: '8px 20px', height: '77px' }}>
      <Navbar.Brand as={Link} to="/">
        Thesis Management System
      </Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="me-auto mb-2 mb-lg-0">
          <Link to="/" className="nav-link active" aria-current="page">Trang chủ</Link>
          {user ? (
            <>
              {user.account.role === 'ADMIN' && (
                <>
                  <NavDropdown title="Đào tạo" id="basic-nav-dropdown">
                    <NavDropdown.Item as={Link} to="/admins/school-years">Niên khoá</NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/admins/faculties">Khoa đào tạo</NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/admins/majors">Ngành đào tạo</NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/admins/classes">Lớp học</NavDropdown.Item>
                  </NavDropdown>
                  <NavDropdown title="Người dùng" id="basic-nav-dropdown">
                    <NavDropdown.Item as={Link} to="/admins/affairs">Giáo vụ</NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/admins/lecturers">Giảng viên</NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/admins/students">Sinh viên</NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/admins">Quản trị viên</NavDropdown.Item>
                  </NavDropdown>
                </>
              )}
              <Link to="/thesis" className="nav-link">Khoá luận</Link>
              <Link to="/criterion" className="nav-link">Tiêu chí</Link> {/* Link to Criterion */}
              <Link onClick={handleLogout} className="nav-link">Đăng xuất</Link>
            </>
          ) : (
            <Link to="/login" className="nav-link">Đăng nhập</Link>
          )}
        </Nav>
        {user && (
          <Navbar.Text className="d-flex align-items-center ms-auto">
            <div className="d-flex flex-column align-items-end text-end me-2">
              <small>{getGreeting()}</small>
              <strong>{user.lastName} {user.firstName}</strong>
            </div>
            <Image src={user.account.avatar} alt="user-avatar" style={{ width: '40px' }} className="rounded-pill ms-2" />
          </Navbar.Text>
        )}
      </Navbar.Collapse>
    </Navbar>
  );
}

export default Header;
