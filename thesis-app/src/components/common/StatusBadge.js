import { Badge } from "react-bootstrap";

const StatusBadge = ({ status, bg }) => {
  return (
    <Badge bg={bg}>{status}</Badge>
  );
};

export default StatusBadge;