import React, { useCallback, useContext, useEffect, useState } from "react";
import { UserContext } from "../../configs/UserContext";
import { useNavigate } from "react-router-dom";
import withAuth from "../hoc/withAuth";
import { Helmet } from "react-helmet";
import Title from "../common/Title";
import { Form, Button, Spinner, Alert } from "react-bootstrap";
import { authApi, endpoints } from "../../configs/APIs";

const CreateThesis = () => {
  const { user } = useContext(UserContext);
  const nav = useNavigate();

  const [thesis, setThesis] = useState({
    name: "",
    startDate: "",
    endDate: "",
    expDate: "",
    comment: "",
    criticalLecturerId: "",
    schoolYearId: "",
    majorId: "",
    thesisLecturerSet: [{ lecturerId: "" }],
    thesisStudentSet: []
  });

  const [schoolYears, setSchoolYears] = useState([]);
  const [majors, setMajors] = useState([]);
  const [lecturers, setLecturers] = useState([]);
  const [students, setStudents] = useState([]);
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

  const fetchMajors = useCallback(async () => {
    try {
      let res = await authApi().get(`${endpoints["majors"]}?type=faculty&kw=${user.faculty.id}`);
      setMajors(res.data);
    } catch (error) {
      console.error("Lỗi khi lấy danh sách ngành:", error);
      setError("Đã xảy ra lỗi khi lấy danh sách ngành.");
    }
  }, [user.faculty.id]);

  const fetchLecturers = useCallback(async () => {
    try {
      let res = await authApi().get(`${endpoints["lecturers"]}?type=faculty&kw=${user.faculty.id}`);
      setLecturers(res.data);
    } catch (error) {
      console.error("Lỗi khi lấy danh sách giảng viên:", error);
      setError("Đã xảy ra lỗi khi lấy danh sách giảng viên.");
    }
  }, [user.faculty.id]);

  useEffect(() => {
    checkRole();
    fetchSchoolYears();
    fetchMajors();
    fetchLecturers();
  }, [checkRole, fetchSchoolYears, fetchMajors, fetchLecturers]);

  const handleChange = (event, field) => {
    const { name, value } = event.target;
    if (name === "thesisLecturerSet") {
      const updatedThesisLecturerSet = [...thesis.thesisLecturerSet];
      updatedThesisLecturerSet[field] = { lecturerId: value };
      setThesis((prev) => ({
        ...prev,
        thesisLecturerSet: updatedThesisLecturerSet
      }));
    } else if (name === "thesisStudentSet") {
      const updatedThesisStudentSet = [...thesis.thesisStudentSet];
      updatedThesisStudentSet[field] = { studentId: value };
      setThesis((prev) => ({
        ...prev,
        thesisStudentSet: updatedThesisStudentSet
      }));
      fetchStudentInfo(value, field);
    } else {
      setThesis((prev) => ({
        ...prev,
        [field]: value
      }));
    }
  };

  const handleAddThesisLecturerSet = () => {
    if (thesis.thesisLecturerSet.length < 2) {
      setThesis((prev) => ({
        ...prev,
        thesisLecturerSet: [...prev.thesisLecturerSet, { lecturerId: "" }]
      }));
    }
  };

  const handleRemoveThesisLecturerSet = (index) => {
    if (thesis.thesisLecturerSet.length > 1) {
      const updatedThesisLecturerSet = [...thesis.thesisLecturerSet];
      updatedThesisLecturerSet.splice(index, 1);
      setThesis((prev) => ({
        ...prev,
        thesisLecturerSet: updatedThesisLecturerSet
      }));
    }
  };

  const handleAddThesisStudent = async () => {
    const newStudentId = document.getElementById("newStudentId").value;
    if (newStudentId) {
      try {
        const res = await authApi().get(`${endpoints["students"]}?facultyId=${user.faculty.id}&id=${newStudentId}`);
        const studentData = res.data[0];

        if (studentData) {
          setThesis((prev) => ({
            ...prev,
            thesisStudentSet: [...prev.thesisStudentSet, { studentId: newStudentId }]
          }));
          setStudents((prev) => [...prev, studentData]);
          document.getElementById("newStudentId").value = "";
        } else {
          setError("Không tìm thấy sinh viên với ID này. (Có thể sinh viên không thuộc khoa của bạn)");
        }
      } catch (error) {
        console.error("Lỗi khi lấy thông tin sinh viên:", error);
        setError("Đã xảy ra lỗi khi lấy thông tin sinh viên.");
      }
    } else {
      setError("Vui lòng nhập ID sinh viên.");
    }
  };

  const handleRemoveThesisStudent = (index) => {
    const updatedThesisStudentSet = [...thesis.thesisStudentSet];
    updatedThesisStudentSet.splice(index, 1);
    setThesis((prev) => ({
      ...prev,
      thesisStudentSet: updatedThesisStudentSet
    }));

    const updatedStudents = [...students];
    updatedStudents.splice(index, 1);
    setStudents(updatedStudents);
  };

  const fetchStudentInfo = useCallback(async (studentId, index) => {
    if (studentId) {
      try {
        const res = await authApi().get(`${endpoints["students"]}?id=${studentId}`);
        const studentData = res.data[0];
        const updatedStudents = [...students];
        updatedStudents[index] = studentData;
        setStudents(updatedStudents);
      } catch (error) {
        console.error("Lỗi khi lấy thông tin sinh viên:", error);
        setError("Đã xảy ra lỗi khi lấy thông tin sinh viên.");
      }
    } else {
      const updatedStudents = [...students];
      updatedStudents[index] = null;
      setStudents(updatedStudents);
    }
  }, [students]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    setSuccess("");

    const thesisData = {
      name: thesis.name,
      startDate: thesis.startDate,
      endDate: thesis.endDate,
      expDate: thesis.expDate,
      comment: thesis.comment,
      criticalLecturerId: parseInt(thesis.criticalLecturerId),
      schoolYearId: parseInt(thesis.schoolYearId),
      majorId: parseInt(thesis.majorId),
      thesisLecturerSet: thesis.thesisLecturerSet.map(lecturer => ({ lecturerId: parseInt(lecturer.lecturerId) })),
      thesisStudentSet: thesis.thesisStudentSet.map(student => ({ studentId: parseInt(student.studentId) }))
    };

    try {
      await authApi().post(endpoints["thesis"], thesisData);
      setSuccess("Khoá luận đã được tạo thành công!");
      setThesis({
        name: "",
        startDate: "",
        endDate: "",
        expDate: "",
        comment: "",
        criticalLecturerId: "",
        schoolYearId: "",
        majorId: "",
        thesisLecturerSet: [{ lecturerId: "" }],
        thesisStudentSet: []
      });
    } catch (error) {
      setError("Đã xảy ra lỗi khi tạo khoá luận.");
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
        <title>Thêm Khoá luận</title>
      </Helmet>
      <div className="container">
        <Title title="Thêm" strong="Khoá luận" />
        {error && <Alert variant="danger">{error}</Alert>}
        {success && <Alert variant="success">{success}</Alert>}
        <Form onSubmit={handleSubmit}>
          <Form.Group className="form-floating mb-3">
            <Form.Control
              type="text"
              placeholder="Tên khoá luận"
              name="name"
              value={thesis.name}
              onChange={(e) => handleChange(e, "name")}
              id="floatingName"
            />
            <Form.Label htmlFor="floatingName">Tên khoá luận</Form.Label>
          </Form.Group>

          <Form.Group className="form-floating mb-3">
            <Form.Control
              type="date"
              placeholder="Ngày bắt đầu"
              name="startDate"
              value={thesis.startDate}
              onChange={(e) => handleChange(e, "startDate")}
              id="floatingStartDate"
            />
            <Form.Label htmlFor="floatingStartDate">Ngày bắt đầu</Form.Label>
          </Form.Group>

          <Form.Group className="form-floating mb-3">
            <Form.Control
              type="date"
              placeholder="Ngày kết thúc"
              name="endDate"
              value={thesis.endDate}
              onChange={(e) => handleChange(e, "endDate")}
              id="floatingEndDate"
            />
            <Form.Label htmlFor="floatingEndDate">Ngày kết thúc</Form.Label>
          </Form.Group>

          <Form.Group className="form-floating mb-3">
            <Form.Control
              type="date"
              placeholder="Ngày hết hạn nộp"
              name="expDate"
              value={thesis.expDate}
              onChange={(e) => handleChange(e, "expDate")}
              id="floatingExpDate"
            />
            <Form.Label htmlFor="floatingExpDate">Ngày hết hạn nộp</Form.Label>
          </Form.Group>

          <Form.Group className="form-floating mb-3">
            <Form.Control
              as="select"
              value={thesis.schoolYearId}
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

          <Form.Group className="form-floating mb-3">
            <Form.Control
              as="select"
              value={thesis.majorId}
              onChange={(e) => handleChange(e, "majorId")}
              name="majorId"
              id="floatingMajorId"
            >
              <option value="">Chọn ngành</option>
              {majors.map((major) => (
                <option key={major.id} value={major.id}>
                  {major.name}
                </option>
              ))}
            </Form.Control>
            <Form.Label htmlFor="floatingMajorId">Ngành</Form.Label>
          </Form.Group>

          <Form.Group className="form-floating mb-3">
            <Form.Control
              type="text"
              placeholder="Ghi chú"
              value={thesis.comment}
              onChange={(e) => handleChange(e, "comment")}
              id="floatingComment"
            />
            <Form.Label htmlFor="floatingComment">Ghi chú</Form.Label>
          </Form.Group>

          <Form.Group className="form-floating mb-3">
            <Form.Control
              as="select"
              value={thesis.criticalLecturerId}
              onChange={(e) => handleChange(e, "criticalLecturerId")}
              name="criticalLecturerId"
              id="floatingCriticalLecturerId"
            >
              <option value="">Chọn giảng viên phản biện</option>
              {lecturers.map((lecturer) => (
                <option key={lecturer.id} value={lecturer.id}>
                  {lecturer.lastName} {lecturer.firstName} ({lecturer.email})
                </option>
              ))}
            </Form.Control>
            <Form.Label htmlFor="floatingCriticalLecturerId">Giảng viên phản biện</Form.Label>
          </Form.Group>

          {/* Danh sách giảng viên hướng dẫn */}
          {thesis.thesisLecturerSet.map((lecturer, index) => (
            <Form.Group key={index} className="mb-3 d-flex align-items-center">
              <div className="form-floating flex-grow-1">
                <Form.Control
                  as="select"
                  value={lecturer.lecturerId}
                  onChange={(e) => handleChange(e, index)}
                  name="thesisLecturerSet"
                  id={`floatingThesisLecturerSet-${index}`}
                >
                  <option value="">Chọn giảng viên hướng dẫn</option>
                  {lecturers.map((lecturer) => (
                    <option key={lecturer.id} value={lecturer.id}>
                      {lecturer.lastName} {lecturer.firstName} ({lecturer.email})
                    </option>
                  ))}
                </Form.Control>
                <Form.Label htmlFor={`floatingThesisLecturerSet-${index}`}>Giảng viên hướng dẫn</Form.Label>
              </div>
              {index > 0 && (
                <Button
                  variant="outline-danger"
                  onClick={() => handleRemoveThesisLecturerSet(index)}
                  className="ms-2"
                >
                  Xoá
                </Button>
              )}
            </Form.Group>
          ))}

          {/* Nút thêm giảng viên hướng dẫn */}
          {thesis.thesisLecturerSet.length < 2 && (
            <div className="d-flex justify-content-center">
              <Button
                variant="outline-dark"
                className="mb-3"
                onClick={handleAddThesisLecturerSet}
                disabled={thesis.thesisLecturerSet.length >= 2}
              >
                +
              </Button>
            </div>
          )}

          {/* Nhập ID sinh viên */}
          <Form.Group className="mb-3 d-flex align-items-center">
            <div className="form-floating flex-grow-1">
              <Form.Control
                type="number"
                placeholder="ID Sinh viên"
                id="newStudentId"
              />
              <Form.Label htmlFor="newStudentId">ID Sinh viên</Form.Label>
            </div>
            <Button
              variant="outline-dark"
              onClick={handleAddThesisStudent}
              className="ms-2"
              disabled={thesis.thesisStudentSet.length >= 2}
            >
              Thêm
            </Button>
          </Form.Group>

          {/* Hiển thị thông tin sinh viên */}
          {thesis.thesisStudentSet.length > 0 && thesis.thesisStudentSet.map((student, index) => (
            <Form.Group key={index} className="mb-3 d-flex align-items-center">
              <div className="form-floating flex-grow-1">
                <Form.Control
                  type="text"
                  placeholder="Thông tin sinh viên"
                  value={students[index] ? `${students[index].lastName} ${students[index].firstName} (${students[index].email})` : ""}
                  readOnly
                  id={`studentInfo-${index}`}
                />
                <Form.Label htmlFor={`studentInfo-${index}`}>Thông tin sinh viên</Form.Label>
              </div>
              <Button
                variant="outline-danger"
                onClick={() => handleRemoveThesisStudent(index)}
                className="ms-2"
              >
                Xoá
              </Button>
            </Form.Group>
          ))}

          {/* Nút submit */}
          <div className="mb-3 d-flex justify-content-center align-items-center">
            <Button variant="dark" type="submit" className="mt-3" size="lg" disabled={loading}>
              {loading ? <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" /> : <>Thêm <strong>Khoá luận</strong></>}
            </Button>
            <Button variant="outline-dark" className="mt-3 ms-2" size="lg" onClick={handleBack}>Quay lại</Button>
          </div>
        </Form>
      </div>
    </>
  );
};

export default withAuth(CreateThesis);