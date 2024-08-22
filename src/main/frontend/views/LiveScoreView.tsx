import React from 'react';
import MatchCard from '../components/match/MatchCard';
import { useLiveScores } from '../hooks/useLiveScores';

const LiveScoreView: React.FC = () => {
    const { matches, loading, error } = useLiveScores();

    if (loading) return <div className="loading">Loading...</div>;
    if (error) return <div className="error">Error: {error.message}</div>;

    return (
        <div className="match-card-container">
            {matches.map((match) => (
                <MatchCard key={match.id} match={match} />
            ))}
        </div>
    );
};

export default LiveScoreView;
