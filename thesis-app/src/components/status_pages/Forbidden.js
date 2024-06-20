import { Helmet } from "react-helmet";
import Title from "../common/Title";

const Forbidden = () => {
  return (
    <>
      <Helmet>
        <title>403 - Forbiden</title>
      </Helmet>
      <div className="container">
        <div className="row">
          <div className="col-12">
            <Title title="403 - " strong="Forbiden" />
            <p className="text-center">Bạn không có quyền truy cập trang này!</p>
          </div>
        </div>
      </div>
    </>
  );
}

export default Forbidden;