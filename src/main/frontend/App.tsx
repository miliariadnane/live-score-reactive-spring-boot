import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/common/Navbar';
import Footer from './components/common/Footer';
import LiveScoreView from './views/LiveScoreView';
import LeagueView from './views/LeagueView';
import MatchDetails from './components/match/MatchDetails';
import './styles/main.css';
import AboutView from "./views/AboutView";

const App: React.FC = () => {
    return (
        <Router>
            <div className="app-container">
                <Navbar />
                <main className="main-content">
                    <Routes>
                        <Route path="/" element={<LiveScoreView />} />
                        <Route path="/match/:id" element={<MatchDetails />} />
                        <Route path="/leagues" element={<LeagueView />} />
                        <Route path="/about" element={<AboutView />} />
                    </Routes>
                </main>
                <Footer />
            </div>
        </Router>
    );
};

export default App;
