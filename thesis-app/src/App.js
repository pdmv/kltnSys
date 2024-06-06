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

function App() {
  return (
    <BrowserRouter>
      <UserProvider>
        <Header />
        <Container>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/thesis" element={<Thesis />} />
            <Route path="/thesis-details" element={<ThesisDetails />} />
            <Route path="/login" element={<Login />} />
          </Routes>
        </Container>
        <Footer />
      </UserProvider>
    </BrowserRouter>
  );
}

export default App;
