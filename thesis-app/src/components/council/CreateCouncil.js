import React, { useCallback, useContext, useEffect, useState } from "react";
import { UserContext } from "../../configs/UserContext";
import { useNavigate } from "react-router-dom";
import withAuth from "../hoc/withAuth";
import { Helmet } from "react-helmet";
import Title from "../common/Title";
import { Form, Button, Spinner, Alert } from "react-bootstrap";
import { authApi, endpoints } from "../../configs/APIs";

const CreateCouncil = () => {
  const { user } = useContext(UserContext);
  const nav = useNavigate();

  const [council, setCouncil] = useState({
    name: "",
    schoolYearId: "",
    meetingDate: "",
    criterions: [{ id: "", weight: "" }],
    lecturers: [
      { id: "", position: "president" },
      { id: "", position: "critical" },
      { id: "", position: "secretary" }
    ],
    theses: [{ id: "" }]
  });

  const [schoolYears, setSchoolYears] = useState([]);
  const [lecturers, setLecturers] = useState([]);
  const [theses, setTheses] = useState([]);
  const [criterions, setCriterions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const checkRole = useCallback(() => {
    if (user.account.role !== 'AFFAIR') {
      nav('/forbidden');
    }
  }, [user.account.role, nav]);

  const fetchSchoolYears = useCallback(async () => {
    try {
      let res = await authApi().get(endpoints["school-years"]);
      setSchoolYears(res.data);
    } catch (error) {
      console.error("Lỗi khi lấy danh sách năm học:", error);
      setError("Đã xảy ra lỗi khi lấy danh sách năm học.");
    }
  }, []);

  const fetchLecturers = useCallback(async () => {
    try {
      let res = await authApi().get(`${endpoints["lecturers"]}?type=faculty&kw=${user.faculty.id}&pageSize=50`);
      setLecturers(res.data);
    } catch (error) {
      console.error("Lỗi khi lấy danh sách giảng viên:", error);
      setError("Đã xảy ra lỗi khi lấy danh sách giảng viên.");
    }
  }, [user.faculty.id]);

  const fetchTheses = useCallback(async () => {
    if (!council.schoolYearId) return;
    try {
      let res = await authApi().get(`${endpoints["thesis"]}?facultyId=${user.faculty.id}&schoolYearId=${council.schoolYearId}&status=submitted&pageSize=50`);
      setTheses(res.data);
    } catch (error) {
      console.error("Lỗi khi lấy danh sách khoá luận:", error);
      setError("Đã xảy ra lỗi khi lấy danh sách khoá luận.");
    }
  }, [council.schoolYearId, user.faculty.id]);

  const fetchCriterions = useCallback(async () => {
    try {
      let res = await authApi().get(`${endpoints["criterion"]}?facultyId=${user.faculty.id}&pageSize=50`);
      setCriterions(res.data);
    } catch (error) {
      console.error("Lỗi khi lấy danh sách tiêu chí:", error);
      setError("Đã xảy ra lỗi khi lấy danh sách tiêu chí.");
    }
  }, [user.faculty.id]);

  useEffect(() => {
    checkRole();
    fetchSchoolYears();
    fetchLecturers();
    fetchCriterions();
  }, [checkRole, fetchSchoolYears, fetchLecturers, fetchCriterions]);

  useEffect(() => {
    fetchTheses();
  }, [council.schoolYearId, fetchTheses]);

  const handleChange = (event, field, index, subField) => {
    const { value } = event.target;

    if (field === "meetingDate") {
      const formattedDate = formatDateTime(value);
      setCouncil((prev) => ({
        ...prev,
        [field]: formattedDate,
      }));
    } else if (field === "lecturers" || field === "criterions") {
      const updatedField = [...council[field]];
      updatedField[index][subField] = value;
      setCouncil((prev) => ({
        ...prev,
        [field]: updatedField,
      }));
    } else if (field === "theses") {
      const updatedTheses = [...council.theses];
      updatedTheses[index] = { id: value };
      setCouncil((prev) => ({
        ...prev,
        theses: updatedTheses,
      }));
    } else {
      setCouncil((prev) => ({
        ...prev,
        [field]: value,
      }));
    }
  };

  const handleAddField = (field, maxItems, newItem) => {
    if (council[field].length < maxItems) {
      setCouncil((prev) => ({
        ...prev,
        [field]: [...prev[field], newItem]
      }));
    }
  };

  const handleRemoveField = (field, index, minItems) => {
    if (council[field].length > minItems) {
      const updatedField = [...council[field]];
      updatedField.splice(index, 1);
      setCouncil((prev) => ({
        ...prev,
        [field]: updatedField
      }));
    }
  };

  const validateWeights = () => {
    return council.criterions.every(criterion => parseFloat(criterion.weight) > 0);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    setSuccess("");

    if (!validateWeights()) {
      setError("Trọng số của tiêu chí phải lớn hơn 0.");
      setLoading(false);
      return;
    }

    const councilData = {
      ...council,
      schoolYearId: parseInt(council.schoolYearId),
      criterions: council.criterions.map(criterion => ({
        id: parseInt(criterion.id),
        weight: parseFloat(criterion.weight)
      })),
      lecturers: council.lecturers.map(lecturer => ({
        id: parseInt(lecturer.id),
        position: lecturer.position
      })),
      theses: council.theses.map(thesis => ({ id: parseInt(thesis.id) }))
    };

    try {
      await authApi().post(endpoints["council"], councilData);
      console.log(councilData.meetingDate)
      setSuccess("Hội đồng đã được thêm thành công!");
      setCouncil({
        name: "",
        schoolYearId: "",
        meetingDate: "",
        criterions: [{ id: "", weight: "" }],
        lecturers: [
          { id: "", position: "president" },
          { id: "", position: "critical" },
          { id: "", position: "secretary" }
        ],
        theses: [{ id: "" }]
      });
    } catch (error) {
      setError("Đã xảy ra lỗi khi thêm hội đồng.");
    } finally {
      setLoading(false);
    }
  };

  const handleBack = () => {
    nav(-1);
  };

  const formatDateTime = (datetimeLocal) => {
    const date = new Date(datetimeLocal);
    const year = date.getFullYear();
    const month = padWithZero(date.getMonth() + 1);
    const day = padWithZero(date.getDate());
    const hours = padWithZero(date.getHours());
    const minutes = padWithZero(date.getMinutes());
    return `${year}-${month}-${day} ${hours}:${minutes}`;
  };

  const padWithZero = (number) => {
    return number.toString().padStart(2, "0");
  };

  return (
    <>
      <Helmet>
        <title>Thêm Hội đồng</title>
      </Helmet>
      <div className="container">
        <Title title="Thêm" strong="Hội đồng" />
        {error && <Alert variant="danger">{error}</Alert>}
        {success && <Alert variant="success">{success}</Alert>}
        <Form onSubmit={handleSubmit}>
          <Form.Group className="form-floating mb-3">
            <Form.Control
              type="text"
              placeholder="Tên hội đồng"
              name="name"
              value={council.name}
              onChange={(e) => handleChange(e, "name")}
              id="floatingName"
            />
            <Form.Label htmlFor="floatingName">Tên hội đồng</Form.Label>
          </Form.Group>

          <Form.Group className="form-floating mb-3">
            <Form.Control
              type="datetime-local"
              placeholder="Ngày họp"
              name="meetingDate"
              value={council.meetingDate}
              onChange={(e) => handleChange(e, "meetingDate")}
              id="floatingMeetingDate"
            />
            <Form.Label htmlFor="floatingMeetingDate">Ngày họp</Form.Label>
          </Form.Group>

          <Form.Group className="form-floating mb-3">
            <Form.Control
              as="select"
              value={council.schoolYearId}
              onChange={(e) => handleChange(e, "schoolYearId")}
              name="schoolYearId"
              id="floatingSchoolYearId"
            >
              <option value="">Chọn năm học</option>
              {schoolYears.map((year) => (
                <option key={year.id} value={year.id}>
                  {year.startYear}-{year.endYear}
                </option>
              ))}
            </Form.Control>
            <Form.Label htmlFor="floatingSchoolYearId">Năm học</Form.Label>
          </Form.Group>

          <h5>Tiêu chí</h5>
          {council.criterions.map((criterion, index) => (
            <Form.Group key={index} className="mb-3 d-flex align-items-center">
              <div className="form-floating flex-grow-1">
                <Form.Control
                  as="select"
                  value={criterion.id}
                  onChange={(e) => handleChange(e, "criterions", index, "id")}
                  name="criterions"
                  id={`floatingCriterionId-${index}`}
                >
                  <option value="">Chọn tiêu chí</option>
                  {criterions.map((crit) => (
                    <option key={crit.id} value={crit.id}>
                      {crit.name}
                    </option>
                  ))}
                </Form.Control>
                <Form.Label htmlFor={`floatingCriterionId-${index}`}>Tiêu chí</Form.Label>
              </div>
              <div className="form-floating flex-grow-1 ms-2">
                <Form.Control
                  type="number"
                  placeholder="Trọng số"
                  value={criterion.weight}
                  onChange={(e) => handleChange(e, "criterions", index, "weight")}
                  id={`floatingCriterionWeight-${index}`}
                />
                <Form.Label htmlFor={`floatingCriterionWeight-${index}`}>Trọng số</Form.Label>
              </div>
              {council.criterions.length > 1 && (
                <Button
                  variant="danger"
                  onClick={() => handleRemoveField("criterions", index, 1)}
                  className="ms-2"
                >
                  Xóa
                </Button>
              )}
            </Form.Group>
          ))}
          <div className="d-flex justify-content-center">
            <Button variant="outline-dark" onClick={() => handleAddField("criterions", 10, { id: "", weight: "" })}>Thêm tiêu chí</Button>
          </div>

          <h5>Thành viên hội đồng</h5>
          {council.lecturers.map((lecturer, index) => (
            <Form.Group key={index} className="mb-3 d-flex align-items-center">
              <div className="form-floating flex-grow-1">
                <Form.Control
                  as="select"
                  value={lecturer.id}
                  onChange={(e) => handleChange(e, "lecturers", index, "id")}
                  name="lecturers"
                  id={`floatingLecturerId-${index}`}
                >
                  <option value="">Chọn giảng viên</option>
                  {lecturers.map((lec) => (
                    <option key={lec.id} value={lec.id}>
                      {lec.lastName} {lec.firstName}
                    </option>
                  ))}
                </Form.Control>
                <Form.Label htmlFor={`floatingLecturerId-${index}`}>Giảng viên</Form.Label>
              </div>
              <div className="form-floating flex-grow-1 ms-2">
                <Form.Control
                  as="select"
                  value={lecturer.position}
                  onChange={(e) => handleChange(e, "lecturers", index, "position")}
                  name="position"
                  id={`floatingLecturerPosition-${index}`}
                >
                  <option value="president">Chủ tịch</option>
                  <option value="critical">Phản biện</option>
                  <option value="secretary">Thư ký</option>
                  <option value="member">Thành viên</option>
                </Form.Control>
                <Form.Label htmlFor={`floatingLecturerPosition-${index}`}>Vị trí</Form.Label>
              </div>
              {council.lecturers.length > 3 && (
                <Button
                  variant="danger"
                  onClick={() => handleRemoveField("lecturers", index, 3)}
                  className="ms-2"
                >
                  Xóa
                </Button>
              )}
            </Form.Group>
          ))}
          <div className="d-flex justify-content-center">
            <Button variant="outline-dark" onClick={() => handleAddField("lecturers", 5, { id: "", position: "member" })}>
              Thêm thành viên
            </Button>
          </div>

          <h5>Khoá luận</h5>
          {council.theses.map((thesis, index) => (
            <Form.Group key={index} className="mb-3 d-flex align-items-center">
              <div className="form-floating flex-grow-1">
                <Form.Control
                  as="select"
                  value={thesis.id}
                  onChange={(e) => handleChange(e, "theses", index, "id")}
                  name="theses"
                  id={`floatingThesisId-${index}`}
                >
                  <option value="">Chọn khoá luận</option>
                  {theses.map((th) => (
                    <option key={th.id} value={th.id}>
                      {th.name}
                    </option>
                  ))}
                </Form.Control>
                <Form.Label htmlFor={`floatingThesisId-${index}`}>Khoá luận</Form.Label>
              </div>
              {council.theses.length > 1 && (
                <Button
                  variant="danger"
                  onClick={() => handleRemoveField("theses", index, 1)}
                  className="ms-2"
                >
                  Xóa
                </Button>
              )}
            </Form.Group>
          ))}
          <div className="d-flex justify-content-center">
            <Button
              variant="outline-dark"
              onClick={() => handleAddField("theses", 5, { id: "" })}
            >
              Thêm khoá luận
            </Button>
          </div>

          <div className="mb-3 d-flex justify-content-center align-items-center">
            <Button variant="dark" type="submit" className="mt-3" disabled={loading}>
              {loading ? <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" /> : <>Thêm <strong>Hội đồng</strong></>}
            </Button>
            <Button variant="outline-dark" className="mt-3 ms-2" onClick={handleBack}>Quay lại</Button>
          </div>
        </Form>
      </div>
    </>
  );
};

export default withAuth(CreateCouncil);