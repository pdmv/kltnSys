import { useContext, useEffect, useState } from "react";
import { UserContext } from "../../configs/UserContext";
import { Alert, Form, Spinner } from "react-bootstrap";
import { Button, Col, Row } from "react-bootstrap";
import { useLocation, useNavigate } from "react-router-dom";
import APIs, { authApi, endpoints } from "../../configs/APIs";
import cookie from 'react-cookies';
import { Helmet } from "react-helmet";
import Title from "../common/Title";
import './Login.css';

const Login = () => {
  const [user, setUser] = useState({
    username: '',
    password: ''
  });
  const { user: currentUser, dispatch } = useContext(UserContext);
  const location = useLocation();
  const nav = useNavigate();
  const from = location.state?.from?.pathname || "/";
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (currentUser) {
      nav(from, { replace: true });
    }
  }, [currentUser, from, nav]);

  const change = (event, field) => {
    setUser(current => {
      return { ...current, [field]: event.target.value };
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      let res = await APIs.post(endpoints["login"], { ...user });

      cookie.remove('token');
      cookie.save('token', res.data);

      let u = await authApi().get(endpoints["current-user"]);
      dispatch({ type: 'login', payload: u.data });

      nav(from, { replace: true });
    } catch (error) {
      if (error.response && error.response.status === 401) {
        setError('Tên đăng nhập hoặc mật khẩu không đúng!');
      } else {
        setError('Đã xảy ra lỗi, vui lòng thử lại sau!');
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <Helmet>
        <title>Đăng nhập</title>
      </Helmet>
      <Row className="justify-content-md-center mt-5 mb-5">
        <Col md={4}>
          <Title title="Đăng nhập" />
          {error &&
            <Alert variant="danger">
              {error}
            </Alert>}
          <Form onSubmit={handleSubmit}>
            <Form.Group className="form-floating mb-3">
              <Form.Control
                type="text"
                placeholder="Tên đăng nhập"
                value={user.username}
                onChange={(e) => change(e, "username")}
                id="floatingEmail"
              />
              <Form.Label htmlFor="floatingEmail">Tên đăng nhập</Form.Label>
            </Form.Group>

            <Form.Group className="form-floating mb-3">
              <Form.Control
                type="password"
                placeholder="Mật khẩu"
                value={user.password}
                onChange={(e) => change(e, "password")}
                id="floatingPassword"
              />
              <Form.Label htmlFor="floatingPassword">Mật khẩu</Form.Label>
            </Form.Group>

            <Button variant="dark" type="submit" className="mt-3 w-100" size="lg" disabled={loading}>
              {loading ? <Spinner
                as="span"
                animation="border"
                size="sm"
                role="status"
                aria-hidden="true"
              /> : 'Đăng nhập'}
            </Button>
          </Form>
          <div className="mt-4 text-center">
            <h6 className="muted-text">Nếu bạn quên mật khẩu, hãy liên hệ Quản trị viên.</h6>
          </div>
        </Col>
      </Row>
    </>
  );
};

export default Login;
