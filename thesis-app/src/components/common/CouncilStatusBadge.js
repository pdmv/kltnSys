import React from 'react';
import PropTypes from 'prop-types';
import StatusBadge from "./StatusBadge";

const CouncilStatusBadge = ({ status }) => {
  return (
    <StatusBadge
      status={status === "pending" ? "Đang bảo vệ" : "Đã khoá"}
      bg={status === "pending" ? "success" : "danger"}
    />
  );
};

CouncilStatusBadge.propTypes = {
  status: PropTypes.bool.isRequired,
};

export default CouncilStatusBadge;