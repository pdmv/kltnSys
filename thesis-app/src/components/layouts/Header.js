import { useContext } from "react";
import { Image, Nav, Navbar } from "react-bootstrap";
import { Link } from "react-router-dom";
import { UserContext } from "../../configs/UserContext";
import cookie from 'react-cookies'

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
    dispatch({ "type": "logout" });
  };

  return (
    <Navbar expand="lg" className="bg-body-tertiary" style={{ padding: '8px 20px', height: '77px' }}>
      <Navbar.Brand as={Link} to="/">
        Thesis Management System
      </Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="me-auto">
          <Link to="/" className="nav-link">Trang chủ</Link>
          {user ? <>
            {user.account.role === 'AFFAIR' && (
              <Link to="/thesis" className="nav-link">Khoá luận</Link>
            )}
            <Link onClick={handleLogout} className="nav-link">Đăng xuất</Link>
          </> : <>
            <Link to="/login" className="nav-link">Đăng nhập</Link>
          </>}
          {/* <NavDropdown title="Dropdown" id="basic-nav-dropdown">
            <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>
            <NavDropdown.Item href="#action/3.2">
              Another action
            </NavDropdown.Item>
            <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
            <NavDropdown.Divider />
            <NavDropdown.Item href="#action/3.4">
              Separated link
            </NavDropdown.Item>
          </NavDropdown> */}
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