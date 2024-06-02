import { useContext } from "react";
import { Container, Image, Nav, Navbar } from "react-bootstrap";
import { Link } from "react-router-dom";
import { UserContext } from "../../configs/UserContext";
import cookie from 'react-cookies'

const Header = () => {
  const { user, dispatch } = useContext(UserContext);

  const handleLogout = () => {
    cookie.remove('token');
    cookie.remove('user');
    dispatch({ "type": "logout" });
  };

  return (
    <Navbar expand="lg" className="bg-body-tertiary">
      <Container>
        <Navbar.Brand as={Link} to="/">
          Thesis Management System
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Link to="/" className="nav-link">Trang chủ</Link>
            {user ? <>
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
            <Navbar.Text className="d-flex align-items-center">
              <span className="me-2">
                <Link to="/" className="nav-link">Chào <strong>{user.firstName}</strong>!</Link>
              </span>
              <Image src={user.account.avatar} alt="user-avatar" style={{ width: '40px' }} className="rounded-pill" />
            </Navbar.Text>
          )}
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default Header;