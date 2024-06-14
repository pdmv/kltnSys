import React, { useContext } from "react";
import { Navigate, useLocation } from "react-router-dom";
import { UserContext } from "../../configs/UserContext";

const withAuth = (Component) => {
  const WrappedComponent = (props) => {
    const { user } = useContext(UserContext);
    const location = useLocation();

    if (!user) {
      return <Navigate to="/login" state={{ from: location }} />;
    }

    return <Component {...props} />;
  };

  return WrappedComponent;
};

export default withAuth;