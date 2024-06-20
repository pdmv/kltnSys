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

            <Route path="/thesis" element={<Thesis />} />
            <Route path="/thesis/:id" element={<ThesisDetails />} />
            <Route path="/thesis/create" element={<CreateThesis />} />

            <Route path="/criterion" element={<Criterion />} />
            <Route path="/criterion/:id" element={<CriterionDetails />} />
            <Route path="/criterion/create" element={<CriterionForm />} />
            <Route path="/criterion/edit/:id" element={<CriterionForm />} />

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
