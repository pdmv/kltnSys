import React from 'react';
import PropTypes from 'prop-types';
import StatusBadge from "./StatusBadge";

const ActiveStatusBadge = ({ status }) => {
  return (
    <StatusBadge
      status={status ? "Đang hoạt động" : "Đã đình chỉ"}
      bg={status ? "success" : "danger"}
    />
  );
};

ActiveStatusBadge.propTypes = {
  status: PropTypes.bool.isRequired,
};

export default ActiveStatusBadge;