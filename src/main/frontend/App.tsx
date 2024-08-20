import React, { useState, useEffect, useCallback } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { getLiveScores } from 'Frontend/generated/LiveScoreEndpoint';
import Match from 'Frontend/generated/dev/nano/livescore/model/Match';

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

// MatchCard component
const MatchCard: React.FC<{ match: Match }> = React.memo(({ match }) => (
    <div className="bg-white shadow-lg rounded-lg p-6 transition-transform transform hover:scale-105">
        <div className="flex justify-between items-center mb-4">
            <div className="text-gray-700 text-sm font-semibold">{match.league}</div>
            <div className="text-gray-700 text-sm font-semibold">{match.status}</div>
        </div>
        <div className="flex justify-between items-center mb-4">
            <div className="flex flex-col items-center w-2/5">
                <img src={match.homeLogo} alt={match.homeTeam} className="w-16 h-16 object-contain mb-2" />
                <div className="font-bold text-center">{match.homeTeam}</div>
            </div>
            <div className="text-2xl font-bold">
                {match.homeScore !== null && match.awayScore !== null
                    ? `${match.homeScore} - ${match.awayScore}`
                    : 'vs'}
            </div>
            <div className="flex flex-col items-center w-2/5">
                <img src={match.awayLogo} alt={match.awayTeam} className="w-16 h-16 object-contain mb-2" />
                <div className="font-bold text-center">{match.awayTeam}</div>
            </div>
        </div>
        <div className="text-gray-700 text-sm text-center">
            {match.date ? new Date(match.date).toLocaleString() : 'Date TBA'}
        </div>
    </div>
));

// LiveScoreView component
const LiveScoreView: React.FC = () => {
    const { matches, loading, error } = useLiveScores();

    if (loading) return <div className="text-center text-2xl font-bold mt-10">Loading...</div>;
    if (error) return <div className="text-center text-2xl font-bold text-red-500 mt-10">Error: {error.message}</div>;

    return (
        <div className="p-4 bg-gray-100 min-h-screen">
            <h1 className="text-4xl font-bold mb-6 text-center text-gray-800">Live Scores</h1>
            {matches.length === 0 ? (
                <div className="text-center text-xl text-gray-600">No live matches available at the moment.</div>
            ) : (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {matches.map((match) => (
                        <MatchCard key={match.id} match={match} />
                    ))}
                </div>
            )}
        </div>
    );
};

const App: React.FC = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<LiveScoreView />} />
            </Routes>
        </Router>
    );
};

export default App;
