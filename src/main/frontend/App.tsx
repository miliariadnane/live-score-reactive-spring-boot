import React, { useState, useEffect, useCallback } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { getLiveScores } from 'Frontend/generated/LiveScoreEndpoint';
import Match from 'Frontend/generated/dev/nano/livescore/model/Match';
import './main.css';

// Navbar component
const Navbar = () => (
    <nav className="navbar">
        <div className="navbar-brand">LiveScore</div>
        <div className="navbar-menu">
            <a href="/" className="navbar-item">Home</a>
            <a href="/leagues" className="navbar-item">Leagues</a>
            <a href="/about" className="navbar-item">About</a>
        </div>
    </nav>
);

// Footer component
const Footer = () => (
    <footer className="footer">
        <div className="footer-content">
            <p>© 2024 LiveScore App. All rights reserved.</p>
            <div className="footer-links">
                <a href="/privacy" className="footer-link">Privacy Policy</a>
                <a href="/terms" className="footer-link">Terms of Service</a>
                <a href="/contact" className="footer-link">Contact Us</a>
            </div>
        </div>
    </footer>
);

const useLiveScores = (league: string = '') => {
    const [matches, setMatches] = useState<Match[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<Error | null>(null);

    const updateMatches = useCallback((newMatch: Match) => {
        setMatches(prevMatches => {
            const index = prevMatches.findIndex(match => match.id === newMatch.id);
            if (index !== -1) {
                return prevMatches.map((match, i) => i === index ? newMatch : match);
            } else {
                return [...prevMatches, newMatch];
            }
        });
        setLoading(false);
    }, []);

    useEffect(() => {
        setLoading(true);
        setError(null);
        let subscription: { cancel: () => void } | null = null;

        const fetchScores = async () => {
            try {
                subscription = getLiveScores(league).onNext(updateMatches);
            } catch (err) {
                console.error('Error fetching live scores:', err);
                setError(new Error('Failed to fetch live scores. Please refresh the page.'));
                setLoading(false);
            }
        };

        fetchScores();

        return () => {
            if (subscription) {
                subscription.cancel();
            }
        };
    }, [league, updateMatches]);

    return { matches, loading, error };
};

// Updated MatchCard component
const MatchCard: React.FC<{ match: Match }> = React.memo(({ match }) => (
    <div className="match-card">
        <div className="match-league">{match.league}</div>
        <div className="match-teams">
            <div className="team">
                <img src={match.homeLogo} alt={match.homeTeam} className="team-logo" />
                <span className="team-name">{match.homeTeam}</span>
            </div>
            <div className="match-score">
                {match.homeScore !== null && match.awayScore !== null
                    ? `${match.homeScore} - ${match.awayScore}`
                    : 'vs'}
            </div>
            <div className="team">
                <img src={match.awayLogo} alt={match.awayTeam} className="team-logo" />
                <span className="team-name">{match.awayTeam}</span>
            </div>
        </div>
        <div className="match-status">{match.status}</div>
        <div className="match-date">{match.date ? new Date(match.date).toLocaleString() : 'N/A'}</div>
    </div>
));

// Updated LiveScoreView component
const LiveScoreView: React.FC = () => {
    const { matches, loading, error } = useLiveScores();

    if (loading) return <div className="loading">Loading...</div>;
    if (error) return <div className="error">Error: {error.message}</div>;

    return (
        <div className="match-card-container">
            {matches.length === 0 ? (
                <div className="no-matches">No live matches available at the moment.</div>
            ) : (
                matches.map((match) => (
                    <MatchCard key={match.id} match={match} />
                ))
            )}
        </div>
    );
};

const App: React.FC = () => {
    return (
        <Router>
            <div className="app-container">
                <Navbar />
                <main className="main-content">
                    <Routes>
                        <Route path="/" element={<LiveScoreView />} />
                    </Routes>
                </main>
                <Footer />
            </div>
        </Router>
    );
};

export default App;
