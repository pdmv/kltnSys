const Title = ({ title, strong }) => {
  return (
    <h1 className="text-center mt-3 mb-4">{title} <strong>{strong}</strong></h1>
  );
};

export default Title;