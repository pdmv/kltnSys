import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { UserProvider } from './configs/UserContext';
import Login from './components/login/Login';
import Header from './components/layouts/Header';
import Footer from './components/layouts/Footer';
import { Container } from 'react-bootstrap';
import Home from './components/home/Home';
import Thesis from './components/thesis/Thesis';
import ThesisDetails from './components/thesis/ThesisDetails';
import ChangePassword from './components/user/ChangePassword';
import CreateThesis from './components/thesis/CreateThesis';
import Forbidden from './components/status_pages/Forbidden';
import NotFound from './components/status_pages/NotFound';
import Criterion from './components/criterion/Criterion';
import CriterionDetails from './components/criterion/CriterionDetails';
import CriterionForm from './components/criterion/CriterionForm';
import Council from './components/council/Council';
import CreateCouncil from './components/council/CreateCouncil';
import CouncilDetails from './components/council/CouncilDetails';
import ThesisGrading from './components/council/ThesisGrading';
import ScoreStats from './components/stats/ScoreStats';
import CountStats from './components/stats/CountStats';
import Profile from './components/user/Profile';

function App() {
  return (
    <BrowserRouter>
      <UserProvider>
        <Header />
        <Container>
          <Routes>
            <Route path="/" element={<Home />} />

            <Route path="/login" element={<Login />} />
            <Route path='/user/change-password' element={<ChangePassword />} />
            <Route path='/user/profile' element={<Profile />} />

            <Route path="/thesis" element={<Thesis />} />
            <Route path="/thesis/:id" element={<ThesisDetails />} />
            <Route path="/thesis/create" element={<CreateThesis />} />

            <Route path="/criterion" element={<Criterion />} />
            <Route path="/criterion/:id" element={<CriterionDetails />} />
            <Route path="/criterion/create" element={<CriterionForm />} />
            <Route path="/criterion/edit/:id" element={<CriterionForm />} />

            <Route path="/council" element={<Council />} />
            <Route path="/council/create" element={<CreateCouncil />} />
            <Route path="/council/:id" element={<CouncilDetails />} />
            <Route path="/council/:id/thesis/:thesisId/grade" element={<ThesisGrading />} />
            
            <Route path="/stats/score-stats" element={<ScoreStats />} />
            <Route path="/stats/participation-frequency" element={<CountStats />} />

            <Route path="/forbidden" element={<Forbidden />} />
            <Route path="*" element={<NotFound />} />
          </Routes>
        </Container>
        <Footer />
      </UserProvider>
    </BrowserRouter>
  );
}

export default App;
