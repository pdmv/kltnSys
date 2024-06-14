import { Helmet } from "react-helmet";
import Title from "../common/Title";

const NotFound = () => {
  return (
    <>
      <Helmet>
        <title>404 - Not Found</title>
      </Helmet>
      <div className="container">
        <div className="row">
          <div className="col-12">
            <Title title="404 - " strong="Not Found" />
            <p className="text-center">Trang bạn yêu cầu không tồn tại!</p>
          </div>
        </div>
      </div>
    </>
  );
}

export default NotFound;