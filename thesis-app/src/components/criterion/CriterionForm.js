import React, { useCallback, useContext, useEffect, useState } from "react";
import { UserContext } from "../../configs/UserContext";
import { useNavigate, useParams } from "react-router-dom";
import withAuth from "../hoc/withAuth";
import { Helmet } from "react-helmet";
import Title from "../common/Title";
import { Form, Button, Spinner, Alert } from "react-bootstrap";
import { authApi, endpoints } from "../../configs/APIs";

const CriterionForm = () => {
  const { user } = useContext(UserContext);
  const nav = useNavigate();
  const { id } = useParams(); // Lấy id từ URL

  const [criterion, setCriterion] = useState({
    name: "",
    description: "",
    active: true
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const checkRole = useCallback(() => {
    if (user.account.role !== 'AFFAIR') {
      nav('/forbidden');
    }
  }, [user.account.role, nav]);

  const fetchCriterion = useCallback(async () => {
    if (id) { // Chỉ fetch dữ liệu nếu có id
      setLoading(true);
      try {
        const res = await authApi().get(endpoints["criterion-details"](id));
        setCriterion(res.data);
      } catch (error) {
        console.error("Lỗi khi lấy thông tin tiêu chí:", error);
        setError("Đã xảy ra lỗi khi lấy thông tin tiêu chí.");
      } finally {
        setLoading(false);
      }
    }
  }, [id]);

  useEffect(() => {
    checkRole();
    fetchCriterion();
  }, [checkRole, fetchCriterion]);

  const handleChange = (event) => {
    const { name, value, type, checked } = event.target;
    setCriterion((prev) => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    setSuccess("");

    try {
      if (id) {
        await authApi().put(endpoints["criterion-details"](id), criterion);
        setSuccess("Tiêu chí đã được cập nhật thành công!");
      } else {
        await authApi().post(endpoints["criterion"], criterion);
        setSuccess("Tiêu chí đã được tạo mới thành công!");
      }
    } catch (error) {
      setError("Đã xảy ra lỗi khi xử lý yêu cầu.");
    } finally {
      setLoading(false);
    }
  };

  const handleBack = () => {
    nav(-1);
  };

  return (
    <>
      <Helmet>
        <title>{id ? "Cập nhật Tiêu chí" : "Thêm Tiêu chí"}</title>
      </Helmet>
      <div className="container">
        <Title title={id ? "Cập nhật" : "Thêm"} strong="Tiêu chí" />
        {error && <Alert variant="danger">{error}</Alert>}
        {success && <Alert variant="success">{success}</Alert>}
        <Form onSubmit={handleSubmit}>
          <Form.Group className="form-floating mb-3">
            <Form.Control
              type="text"
              placeholder="Tên tiêu chí"
              name="name"
              value={criterion.name}
              onChange={handleChange}
              id="floatingName"
            />
            <Form.Label htmlFor="floatingName">Tên tiêu chí</Form.Label>
          </Form.Group>

          <Form.Group className="form-floating mb-3">
            <Form.Control
              type="text"
              placeholder="Mô tả"
              name="description"
              value={criterion.description}
              onChange={handleChange}
              id="floatingDescription"
            />
            <Form.Label htmlFor="floatingDescription">Mô tả</Form.Label>
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Check
              type="checkbox"
              label="Kích hoạt"
              name="active"
              checked={criterion.active}
              onChange={handleChange}
              id="checkboxActive"
            />
          </Form.Group>

          <div className="mb-3 d-flex justify-content-center align-items-center">
            <Button variant="dark" type="submit" className="mt-3" disabled={loading}>
              {loading ? <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" /> : <>{id ? "Cập nhật" : "Thêm"} <strong>Tiêu chí</strong></>}
            </Button>
            <Button variant="outline-dark" className="mt-3 ms-2" onClick={handleBack}>Quay lại</Button>
          </div>
        </Form>
      </div>
    </>
  );
};

export default withAuth(CriterionForm);