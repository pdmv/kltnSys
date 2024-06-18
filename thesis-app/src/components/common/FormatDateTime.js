import { format } from "date-fns";

const FormatDateTime = ({ date }) => {
  return format(new Date(date), 'dd/MM/yyyy HH:mm:ss');
};

export default FormatDateTime;
