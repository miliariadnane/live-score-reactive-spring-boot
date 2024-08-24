import React from 'react';
import LeagueList from '../components/league/LeagueList';
import { useLeagues } from '../hooks/useLeagues';

const LeagueView: React.FC = () => {
    const { leagues, loading, error } = useLeagues();

    if (loading) return <div className="loading">Loading leagues...</div>;
    if (error) return <div className="error">Error: {error.message}</div>;

    return (
        <div className="league-view">
            <h1>Football Leagues</h1>
            <LeagueList leagues={leagues} />
        </div>
    );
};

export default LeagueView;
