import { useEffect, useState, useCallback, useContext } from "react";
import { Card, Table, Spinner, Alert } from "react-bootstrap";
import { Bar } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';
import { authApi, endpoints } from "../../configs/APIs";
import { UserContext } from "../../configs/UserContext";
import { useNavigate } from "react-router-dom";
import withAuth from "../hoc/withAuth";
import { Helmet } from "react-helmet";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const ScoreStats = () => {
  const [data, setData] = useState({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const { user } = useContext(UserContext);
  const nav = useNavigate();

  const checkRole = useCallback(() => {
    if (user.account.role !== 'AFFAIR') {
      nav('/forbidden');
    }
  }, [user.account.role, nav]);

  const fetchData = useCallback(async () => {
    setLoading(true);
    try {
      let res = await authApi().get(`${endpoints["stats-by-avg-thesis-scores"]}?facultyId=${user.faculty.id}`);
      setData(res.data);
    } catch (error) {
      console.error(error);
      setError("Không thể tải dữ liệu thống kê.");
    } finally {
      setLoading(false);
    }
  }, [user.faculty.id]);

  useEffect(() => {
    checkRole();
    fetchData();
  }, [checkRole, fetchData]);

  const renderTableData = () => {
    return Object.entries(data).map(([year, avgScore]) => (
      <tr key={year}>
        <td>{year}</td>
        <td>{avgScore}</td>
      </tr>
    ));
  };

  const chartData = {
    labels: Object.keys(data),
    datasets: [
      {
        label: "Điểm trung bình khoá luận",
        data: Object.values(data),
        backgroundColor: "rgba(75,192,192,0.6)"
      }
    ]
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Điểm trung bình khoá luận theo năm',
      },
    },
  };

  return (
    <Card className="mt-4 mb-4">
      <Helmet>
        <title>Thống kê điểm khoá luận</title>
      </Helmet>
      <Card.Body>
        <Card.Title><h1 className="text-center mt-3 mb-4">Thống kê <strong>điểm khoá luận</strong></h1></Card.Title>
        {loading && (
          <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '200px' }}>
            <Spinner animation="border" role="status">
              <span className="visually-hidden">Loading...</span>
            </Spinner>
          </div>
        )}
        {error && <Alert variant="danger">{error}</Alert>}
        {!loading && !error && (
          <div style={{ display: 'flex', justifyContent: 'space-between' }}>
            <Table striped bordered hover style={{ flex: 1, marginRight: '20px' }}>
              <thead>
                <tr>
                  <th>Năm học</th>
                  <th>Điểm trung bình</th>
                </tr>
              </thead>
              <tbody>
                {renderTableData()}
              </tbody>
            </Table>
            <div style={{ flex: 1 }}>
              <Bar data={chartData} options={options} />
            </div>
          </div>
        )}
      </Card.Body>
    </Card>
  );
};

export default withAuth(ScoreStats);