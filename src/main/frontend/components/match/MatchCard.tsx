import React from 'react';
import { Link } from 'react-router-dom';
import Match from 'Frontend/generated/dev/nano/livescore/model/Match';

const MatchCard: React.FC<{ match: Match }> = ({ match }) => (
    <Link to={`/match/${match.id}`} className="match-card">
        <div className="match-league">{match.leagueName}</div>
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
        <div className="match-status">{match.statusLong}</div>
        <div className="match-date">{match.date ? new Date(match.date).toLocaleString() : 'N/A'}</div>
    </Link>
);

export default MatchCard;
