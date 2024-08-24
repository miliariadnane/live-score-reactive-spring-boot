import React from 'react';
import League from 'Frontend/generated/dev/nano/livescore/model/League';

interface LeagueListProps {
    leagues: League[];
}

const LeagueList: React.FC<LeagueListProps> = ({ leagues }) => {
    return (
        <div className="league-list">
            {leagues.map((league) => (
                <div key={league.id} className="league-card">
                    <img src={league.logo} alt={league.name} className="league-logo" />
                    <div className="league-info">
                        <h3 className="league-name">{league.name}</h3>
                        <p className="league-country">{league.countryName}</p>
                        <p className="league-type">{league.type}</p>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default LeagueList;
