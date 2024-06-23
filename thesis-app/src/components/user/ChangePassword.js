import { useContext, useState } from "react";
import { Alert, Button, Col, Form, Row, Spinner } from "react-bootstrap";
import { Helmet } from "react-helmet";
import Title from "../common/Title";
import { authApi, endpoints } from "../../configs/APIs";
import { UserContext } from "../../configs/UserContext";
import { useNavigate } from "react-router-dom";
import cookie from 'react-cookies';
import withAuth from "../hoc/withAuth";

const ChangePassword = () => {
  const [passwords, setPasswords] = useState({
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
  });
  const nav = useNavigate();
  const { dispatch } = useContext(UserContext);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const changePassword = (event, field) => {
    setPasswords(current => ({ ...current, [field]: event.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      if (passwords.newPassword !== passwords.confirmPassword) {
        setLoading(false);
        setError('Xác nhận mật khẩu không khớp!');
        return;
      }

      await authApi().post(endpoints["change-password"], {
        oldPassword: passwords.oldPassword,
        newPassword: passwords.newPassword
      });

      dispatch({ type: 'logout' });
      cookie.remove('token');
      cookie.remove('user');

      setLoading(false);
      nav('/login');
    } catch (error) {
      // Xử lý lỗi
      if (error.response && error.response.status === 400) {
        setError(error.response.data.message);
      } else {
        setError('Đã xảy ra lỗi, vui lòng thử lại sau!');
      }
      setLoading(false);
    }
  };

  const handleBack = () => {
    nav(-1);
  }

  return (
    <>
      <Helmet>
        <title>Đổi mật khẩu</title>
      </Helmet>
      <Row className="justify-content-md-center mt-5 mb-5">
        <Col md={4}>
          <Title title="Đổi mật khẩu" />
          {error && <Alert variant="danger">{error}</Alert>}
          <Form onSubmit={handleSubmit}>
            <Form.Group className="form-floating mb-3">
              <Form.Control
                type="password"
                placeholder="Mật khẩu hiện tại"
                value={passwords.oldPassword}
                onChange={(e) => changePassword(e, "oldPassword")}
                id="floatingCurrentPassword"
              />
              <Form.Label htmlFor="floatingCurrentPassword">Mật khẩu hiện tại</Form.Label>
            </Form.Group>

            <Form.Group className="form-floating mb-3">
              <Form.Control
                type="password"
                placeholder="Mật khẩu mới"
                value={passwords.newPassword}
                onChange={(e) => changePassword(e, "newPassword")}
                id="floatingNewPassword"
              />
              <Form.Label htmlFor="floatingNewPassword">Mật khẩu mới</Form.Label>
            </Form.Group>

            <Form.Group className="form-floating mb-3">
              <Form.Control
                type="password"
                placeholder="Xác nhận mật khẩu mới"
                value={passwords.confirmPassword}
                onChange={(e) => changePassword(e, "confirmPassword")}
                id="floatingConfirmPassword"
              />
              <Form.Label htmlFor="floatingConfirmPassword">Xác nhận mật khẩu mới</Form.Label>
            </Form.Group>

            <Button variant="dark" type="submit" className="mt-3 w-100" size="lg" disabled={loading}>
              {loading ? (
                <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" />
              ) : (
                'Đổi mật khẩu'
              )}
            </Button>
            <Button variant="outline-dark" onClick={handleBack} className="mt-3 w-100" size="lg" disabled={loading}>
              Quay lại
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

export default withAuth(ChangePassword);