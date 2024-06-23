import { useContext, useState, useEffect } from "react";
import { Image, Nav, Navbar, NavDropdown } from "react-bootstrap";
import { Link, useNavigate, useLocation } from "react-router-dom";
import { UserContext } from "../../configs/UserContext";
import cookie from 'react-cookies';
import './Header.css';

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
  const nav = useNavigate();
  const location = useLocation();
  const [currentPage, setCurrentPage] = useState("");

  useEffect(() => {
    // Xử lý khi đường dẫn thay đổi
    setCurrentPage(location.pathname);
  }, [location]);

  const handleLogout = () => {
    cookie.remove('token');
    dispatch({ type: "logout" });
    nav('/');
  };

  const handleProfile = () => {
    nav('/user/profile')
  };

  const handlePasswordChange = () => {
    nav('/user/change-password');
  };

  return (
    <Navbar expand="lg" className="bg-body-tertiary" style={{ padding: '8px 20px', height: '77px' }}>
      <Navbar.Brand as={Link} to="/" className="navbar-brand">
        Hệ thống quản lý khoá luận
      </Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="me-auto">
          <Link to="/" className={currentPage === "/" ? "nav-link active-link" : "nav-link"}>Trang chủ</Link>
          {user && (
            <>
              {(user.account.role === 'AFFAIR' || user.account.role === 'LECTURER') && (
                <>
                  <Link to="/thesis" className={location.pathname.startsWith("/thesis") ? "nav-link active-link" : "nav-link"}>Khoá luận</Link>
                  <Link to="/criterion" className={location.pathname.startsWith("/criterion") ? "nav-link active-link" : "nav-link"}>Tiêu chí</Link>
                  <Link to="/council" className={location.pathname.startsWith("/council") ? "nav-link active-link" : "nav-link"}>Hội đồng</Link>
                </>
              )}
              {user.account.role === 'AFFAIR' && (
                <NavDropdown title="Thống kê" id="nav-dropdown" className={location.pathname.startsWith("/stats") ? "active-link" : ""}>
                  <NavDropdown.Item as={Link} to="/stats/score-stats">
                    Điểm khoá luận
                  </NavDropdown.Item>
                  <NavDropdown.Item as={Link} to="/stats/participation-frequency">
                    Tần suất tham gia
                  </NavDropdown.Item>
                </NavDropdown>
              )}
              {user.account.role === 'STUDENT' && (
                <>
                  <Link to="/thesis" className={location.pathname.startsWith("/thesis") ? "nav-link active-link" : "nav-link"}>Khoá luận</Link>
                </>
              )}
            </>
          )}
        </Nav>
        {user ? (
          <NavDropdown
            title={
              <div className="d-flex align-items-center">
                <div className="d-flex flex-column align-items-end text-end me-3">
                  <small>{getGreeting()}</small>
                  <strong>{user.lastName} {user.firstName}</strong>
                </div>
                <Image
                  src={user.account.avatar}
                  alt="user-avatar"
                  style={{ width: '47px', cursor: 'pointer' }}
                  className="rounded-pill"
                />
              </div>
            }
            id="basic-nav-dropdown"
            align="end"
            className="no-caret-dropdown"
          >
            <NavDropdown.Item onClick={handleProfile} className="dropdown-item-hover">Thông tin người dùng</NavDropdown.Item>
            <NavDropdown.Item onClick={handlePasswordChange} className="dropdown-item-hover">Đổi mật khẩu</NavDropdown.Item>
            <NavDropdown.Divider />
            <NavDropdown.Item onClick={handleLogout} className="dropdown-item-hover">Đăng xuất</NavDropdown.Item>
          </NavDropdown>
        ) : (
            <Link to="/login" className={currentPage === "/login" ? "nav-link active-link" : "nav-link"}>Đăng nhập</Link>
        )}
      </Navbar.Collapse>
    </Navbar>
  );
}

export default Header;