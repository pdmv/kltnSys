import { format } from "date-fns";

const FormatDate = ({ date }) => {
  return format(new Date(date), 'dd/MM/yyyy');
};

export default FormatDate;
