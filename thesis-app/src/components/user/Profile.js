import React, { useContext, useEffect, useState, useRef } from "react";
import { useNavigate, Link } from "react-router-dom";
import { Helmet } from "react-helmet";
import { Card, ListGroup, Spinner, Alert, Container, Row, Col, Button } from "react-bootstrap";
import { authApi, endpoints } from "../../configs/APIs";
import { UserContext } from "../../configs/UserContext";
import FormatDate from "../common/FormatDate";
import withAuth from "../hoc/withAuth";
import './Profile.css';

const getRole = (role) => {
  switch (role) {
    case "STUDENT":
      return "Sinh viên";
    case "LECTURER":
      return "Giảng viên";
    case "AFFAIR":
      return "Giáo vụ";
    case "ADMIN":
      return "Quản trị viên";
    default:
      return "Không xác định";
  }
}

const Profile = () => {
  const [profile, setProfile] = useState(null);
  // const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const { user, dispatch } = useContext(UserContext);
  const navigate = useNavigate();
  const [submitting, setSubmitting] = useState(false);
  const fileInputRef = useRef(null);

  useEffect(() => {
    // const fetchProfile = async () => {
    //   setLoading(true);
    //   try {
    //     let res = await authApi().get(endpoints["current-user"]);
    //     setProfile(res.data);
    //   } catch (error) {
    //     if (error.response && error.response.status === 403) {
    //       navigate("/forbidden");
    //     } else {
    //       console.error(error);
    //       setError("Không thể tải dữ liệu người dùng.");
    //     }
    //   } finally {
    //     setLoading(false);
    //   }
    // };

    // fetchProfile();
    setProfile(user);
  }, [user]);

  const handleBack = () => {
    navigate(-1);
  };

  const handleFileChange = async (event) => {
    const selectedFile = event.target.files[0];
    if (selectedFile && selectedFile.type.startsWith("image/")) {
      setSubmitting(true);
      setError(null);

      const formData = new FormData();
      formData.append("file", selectedFile);

      try {
        await authApi().post(endpoints["change-avatar"], formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });

        let user = await authApi().get(endpoints["current-user"]);
        dispatch({ type: "login", payload: user.data });
        setProfile(user.data);
      } catch (error) {
        if (error.response && error.response.status === 403) {
          navigate("/forbidden");
        } else {
          setError("Có lỗi xảy ra khi cập nhật ảnh đại diện.");
        }
      } finally {
        setSubmitting(false);
      }
    } else {
      setError("Chỉ chấp nhận các tệp ảnh.");
    }
  };

  const handleAvatarUpdate = () => {
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  };

  return (
    <>
      <Helmet>
        <title>Thông tin người dùng</title>
      </Helmet>
      <Container className="profile-container mt-5 mb-5">
        {/* {loading && (
          <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '200px' }}>
            <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" />
          </div>
        )} */}
        <div className="mt-4">
          {error && <Alert variant="danger">{error}</Alert>}
        </div>
        {profile && (
          <Card className="profile-card mt-4 mb-4">
            <Card.Body>
              <Card.Title className="mb-4">
                <Row>
                  <Col md={4}>
                    <img src={profile.account.avatar} alt="avatar" className="img-thumbnail avatar-fixed-size" />
                  </Col>
                  <Col md={8}>
                    <h2>{profile.lastName} {profile.firstName}</h2>
                    <p className="text-muted">{profile.email}</p>
                  </Col>
                </Row>
              </Card.Title>
              <ListGroup variant="flush">
                <ListGroup.Item><strong>ID:</strong> {profile.id}</ListGroup.Item>
                <ListGroup.Item><strong>Họ tên:</strong> {profile.lastName} {profile.firstName}</ListGroup.Item>
                <ListGroup.Item><strong>Giới tính:</strong> {profile.gender === "male" ? "Nam" : "Nữ"}</ListGroup.Item>
                <ListGroup.Item><strong>Email:</strong> {profile.email}</ListGroup.Item>
                <ListGroup.Item><strong>Ngày sinh:</strong> <FormatDate date={profile.dob} /></ListGroup.Item>
                <ListGroup.Item><strong>Địa chỉ:</strong> {profile.address}</ListGroup.Item>
                {profile.faculty && <>
                  <ListGroup.Item><strong>Khoa:</strong> {profile.faculty.name}</ListGroup.Item>
                </>}
                {user.account.role === "STUDENT" && <>
                  <ListGroup.Item><strong>Lớp:</strong> {profile.class.name}</ListGroup.Item>
                  <ListGroup.Item><strong>Ngành:</strong> {profile.major.name}</ListGroup.Item>
                </>}
                {/* <ListGroup.Item><strong>Tài khoản:</strong> {profile.account.username}</ListGroup.Item> */}
                <ListGroup.Item><strong>Vai trò:</strong> {getRole(profile.account.role)}</ListGroup.Item>
              </ListGroup>
              <div className="button-group mt-3">
                <input
                  type="file"
                  ref={fileInputRef}
                  style={{ display: "none" }}
                  accept="image/*"
                  onChange={handleFileChange}
                />
                <Button onClick={handleAvatarUpdate} variant="dark" disabled={submitting}>
                  {submitting ? <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" /> : <>Cập nhật <strong>ảnh đại diện</strong></>}
                </Button>
                <Link to='/user/change-password' className="btn btn-outline-dark ms-2">Đổi mật khẩu</Link>
                <Button onClick={handleBack} variant="outline-dark" className="ms-2">Quay lại</Button>
              </div>
            </Card.Body>
          </Card>
        )}
      </Container>
    </>
  );
};

export default withAuth(Profile);