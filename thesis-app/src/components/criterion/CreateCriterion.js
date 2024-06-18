import React, { useCallback, useContext, useEffect, useState } from "react";
import { UserContext } from "../../configs/UserContext";
import { useNavigate } from "react-router-dom";
import withAuth from "../hoc/withAuth";
import { Helmet } from "react-helmet";
import Title from "../common/Title";
import { Form, Button, Spinner, Alert } from "react-bootstrap";
import { authApi, endpoints } from "../../configs/APIs";

const CreateCriterion = () => {
  const { user } = useContext(UserContext);
  const nav = useNavigate();

  const [criterion, setCriterion] = useState({
    name: "",
    description: ""
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const checkRole = useCallback(() => {
    if (user.account.role !== 'AFFAIR') {
      nav('/forbidden');
    }
  }, [user.account.role, nav]);

  useEffect(() => {
    checkRole();
  }, [checkRole]);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setCriterion((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    setSuccess("");

    try {
      await authApi().post(endpoints["criterion"], criterion);
      setSuccess("Tiêu chí đã được tạo thành công!");
      setCriterion({
        name: "",
        description: ""
      });
    } catch (error) {
      setError("Đã xảy ra lỗi khi tạo tiêu chí.");
    } finally {
      setLoading(false);
    }
  };

  const handleBack = () => {
    nav(-1);
  }

  return (
    <>
      <Helmet>
        <title>Thêm Tiêu chí</title>
      </Helmet>
      <div className="container">
        <Title title="Thêm" strong="Tiêu chí" />
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

          <div className="mb-3 d-flex justify-content-center align-items-center">
            <Button variant="dark" type="submit" className="mt-3" disabled={loading}>
              {loading ? <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" /> : <>Thêm <strong>Tiêu chí</strong></>}
            </Button>
            <Button variant="outline-dark" className="mt-3 ms-2" onClick={handleBack}>Quay lại</Button>
          </div>
        </Form>
      </div>
    </>
  );
};

export default withAuth(CreateCriterion);