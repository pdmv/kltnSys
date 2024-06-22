import React, { useState, useEffect, useCallback, useContext } from 'react';
import { Form, Card, Table, Spinner, Alert, FloatingLabel } from 'react-bootstrap';
import { Bar } from 'react-chartjs-2';
import { authApi, endpoints } from "../../configs/APIs";
import withAuth from '../hoc/withAuth';
import { UserContext } from '../../configs/UserContext';
import { useNavigate } from 'react-router-dom';
import { Helmet } from 'react-helmet';
import Title from '../common/Title';

const CountStats = () => {
  const [schoolYears, setSchoolYears] = useState([]);
  const [selectedYear, setSelectedYear] = useState(null);
  const [statsData, setStatsData] = useState({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const { user } = useContext(UserContext);
  const nav = useNavigate();

  const checkRole = useCallback(() => {
    if (user.account.role !== 'AFFAIR') {
      nav('/forbidden');
    }
  }, [user.account.role, nav]);

  const fetchSchoolYears = useCallback(async () => {
    try {
      const res = await authApi().get(endpoints['school-years']); // Adjust the endpoint as needed
      setSchoolYears(res.data);
      if (res.data.length > 0) {
        setSelectedYear(res.data[0].id); // Automatically select the first year
      }
    } catch (error) {
      console.error(error);
      setError('Không thể tải dữ liệu niên khoá.');
    }
  }, []);

  const fetchStats = useCallback(async (yearId) => {
    setLoading(true);
    try {
      const res = await authApi().get(`${endpoints["stats-by-thesis-count-by-major"]}?facultyId=${user.faculty.id}&schoolYearId=${yearId}`);
      setStatsData(res.data);
    } catch (error) {
      console.error(error);
      setError('Không thể tải dữ liệu thống kê.');
    } finally {
      setLoading(false);
    }
  }, [user.faculty.id]);

  useEffect(() => {
    checkRole();
    fetchSchoolYears();
  }, [checkRole, fetchSchoolYears]);

  useEffect(() => {
    if (selectedYear) {
      fetchStats(selectedYear);
    }
  }, [selectedYear, fetchStats]);

  const handleYearChange = (e) => {
    setSelectedYear(e.target.value);
  };

  const chartData = {
    labels: Object.keys(statsData),
    datasets: [
      {
        label: 'Tần suất tham gia',
        data: Object.values(statsData),
        backgroundColor: 'rgba(75, 192, 192, 0.6)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1,
      },
    ],
  };

  return (
    <Card className="mt-4">
      <Helmet>
        <title>Thống kê tần suất tham gia</title>
      </Helmet>
      <Card.Body>
        <Card.Title><Title title="Thống kê " strong="tần suất tham gia" /></Card.Title>
        {error && <Alert variant="danger">{error}</Alert>}
        <Form.Group className="mb-3">
          <FloatingLabel controlId="floatingSelect" label="Chọn niên khoá">
            <Form.Control as="select" value={selectedYear} onChange={handleYearChange}>
              {schoolYears.map(year => (
                <option key={year.id} value={year.id}>{year.startYear} - {year.endYear}</option>
              ))}
            </Form.Control>
          </FloatingLabel>
        </Form.Group>
        {loading ? (
          <div className="d-flex justify-content-center">
            <Spinner animation="border" />
          </div>
        ) : (
          <div className="d-flex">
            <Table striped bordered hover style={{ flex: 1, marginRight: '20px' }}>
              <thead>
                <tr>
                  <th>Ngành</th>
                  <th>Tần suất tham gia</th>
                </tr>
              </thead>
              <tbody>
                {Object.entries(statsData).map(([major, count]) => (
                  <tr key={major}>
                    <td>{major}</td>
                    <td>{count}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
            <div style={{ flex: 1 }}>
              <Bar data={chartData} options={{ maintainAspectRatio: false }} />
            </div>
          </div>
        )}
      </Card.Body>
    </Card>
  );
};

export default withAuth(CountStats);