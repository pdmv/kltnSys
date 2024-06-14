import StatusBadge from "./StatusBadge";

const ThesisStatusBadge = ({ status }) => {
  switch (status) {
    case 'in_progress':
      return <StatusBadge status="Đang thực hiện" bg="success" />;
    case 'submitted':
      return <StatusBadge status="Đã nộp" bg="info" />;
    case 'under_review':
      return <StatusBadge status="Đang bảo vệ" bg="warning" />;
    case 'defended':
      return <StatusBadge status="Đã bảo vệ" bg="secondary" />;
    case 'canceled':
      return <StatusBadge status="Đã huỷ" bg="dark" />;
    default:
      return '';
  }
};

export default ThesisStatusBadge;