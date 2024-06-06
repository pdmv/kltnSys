import { useLocation } from "react-router-dom";
import Title from "../common/Title";
import { useEffect, useState } from "react";
import { authApi, endpoints } from "../../configs/APIs";
import { Spinner, Alert } from "react-bootstrap";
import FormatDate from "../common/FormatDate";
const ThesisDetails = () => {
  const location = useLocation();
  const id = location.state.selected;
  const [thesis, setThesis] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchThesis = async () => {
    setLoading(true);
    try {
      let res = await authApi().get(endpoints["thesis-details"](id));
      setThesis(res.data);
    } catch (error) {
      console.error(error);
      setError("Không thể tải dữ liệu khoá luận.");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    fetchThesis();
    console.log(thesis);
  }, []);

  return (
    <>
      {loading && (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '200px' }}>
          <Spinner animation="border" variant="primary" />
        </div>
      )}
      {error && <Alert variant="danger">{error}</Alert>}
      {thesis && <>
        <Title title="Chi tiết khoá luận" strong={thesis.name} />
        <p>ID: {thesis.id}</p>
        <p>Tên luận văn: {thesis.name}</p>
        <p>Ngày bắt đầu thực hiện: <FormatDate date={thesis.startDate}/></p>
        <p>Ngày kết thúc thực hiện: <FormatDate date={thesis.endDate} /></p>
        <p>Ngày hết hạn nộp: <FormatDate date={thesis.expDate} /></p>
        <p>Trạng thái: {thesis.status}</p>
        <p>Ghi chú: {thesis.comment}</p>
        {/* <p>Ngày tạo: {thesis.createdDate}</p>
        <p>Ngày cập nhật: {thesis.updatedDate}</p>
        <p>Active: {thesis.active.toString()}</p> */}
        <p>Năm học: {thesis.schoolYearId.startYear} - {thesis.schoolYearId.endYear}</p>
        <p>Giảng viên hướng dẫn:</p>
        <ul>
          {thesis.thesisLecturerSet.map((lecturer) => (
            <li key={lecturer.id}>{lecturer.lastName} {lecturer.firstName} - Liên hệ: {lecturer.email}</li>
          ))}
        </ul>
        <p>Sinh viên thực hiện:</p>
        <ul>
          {thesis.thesisStudentSet.map((student, index) => (
            <li key={index}>{student.lastName} {student.firstName} - Liên hệ: {student.email}</li>
          ))}
        </ul>
        <p>Giảng viên phản biện: {thesis.criticalLecturerId.lastName} {thesis.criticalLecturerId.firstName} - Liên hệ: {thesis.criticalLecturerId.email}</p>
        <p>Người tạo: {thesis.affairId.lastName} {thesis.affairId.firstName} - Liên hệ: {thesis.affairId.email}</p>
      </>
      }
    </>
  );
};

export default ThesisDetails;
