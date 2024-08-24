import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getMatchDetails } from 'Frontend/generated/LiveScoreEndpoint';
import Match from 'Frontend/generated/dev/nano/livescore/model/Match';

const MatchDetails: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [match, setMatch] = useState<Match | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        let isMounted = true;
        setLoading(true);
        setError(null);

        const fetchMatchDetails = async () => {
            try {
                const matchData = await getMatchDetails(Number(id));
                if (isMounted) {
                    setMatch(matchData);
                }
            } catch (err) {
                console.error('Error fetching match details:', err);
                if (isMounted) {
                    setError('Failed to fetch match details');
                }
            } finally {
                if (isMounted) {
                    setLoading(false);
                }
            }
        };

        fetchMatchDetails().then(r => console.log('Match details:', r));

        return () => {
            isMounted = false;
        };
    }, [id]);

    if (loading) return <div className="loading">Loading match details...</div>;
    if (error) return <div className="error">{error}</div>;
    if (!match) return <div className="not-found">Match not found</div>;

    console.log('Rendering match:', match);

    return (
        <div className="match-details">
            <h2>{match.homeTeam || 'Home Team'} vs {match.awayTeam || 'Away Team'}</h2>
            <div className="match-info">
                <div className="match-date">{match.date ? new Date(match.date).toLocaleString() : 'Date not available'}</div>
                <p><strong>Referee:</strong> {match.referee || 'N/A'}</p>
                <p><strong>Venue:</strong> {match.venueName ? `${match.venueName}, ${match.venueCity}` : 'N/A'}</p>
                <p><strong>League:</strong> {match.leagueName || 'N/A'}</p>
                <p><strong>Season:</strong> {match.season || 'N/A'}</p>
                <p><strong>Round:</strong> {match.round || 'N/A'}</p>
                <p><strong>Status:</strong> {match.statusLong} {match.elapsed ? `(${match.elapsed}')` : ''}</p>
            </div>
            <div className="teams-info">
                <div className="team home">
                    {match.homeLogo && <img src={match.homeLogo} alt={match.homeTeam} className="team-logo" />}
                    <h3>{match.homeTeam}</h3>
                    {match.homeWinner && <span className="winner-badge">Winner</span>}
                </div>
                <div className="score">
                    <h3>{match.homeScore ?? '-'} - {match.awayScore ?? '-'}</h3>
                </div>
                <div className="team away">
                    {match.awayLogo && <img src={match.awayLogo} alt={match.awayTeam} className="team-logo" />}
                    <h3>{match.awayTeam}</h3>
                    {match.awayWinner && <span className="winner-badge">Winner</span>}
                </div>
            </div>
            <div className="detailed-scores">
                <h4>Detailed Scores:</h4>
                <p><strong>Halftime:</strong> {match.halftimeHome ?? '-'} - {match.halftimeAway ?? '-'}</p>
                <p><strong>Fulltime:</strong> {match.fulltimeHome ?? '-'} - {match.fulltimeAway ?? '-'}</p>
                {(match.extratimeHome !== null && match.extratimeAway !== null) && (
                    <p><strong>Extra Time:</strong> {match.extratimeHome} - {match.extratimeAway}</p>
                )}
                {(match.penaltyHome !== null && match.penaltyAway !== null) && (
                    <p><strong>Penalties:</strong> {match.penaltyHome} - {match.penaltyAway}</p>
                )}
            </div>
            <Link to="/" className="back-button">Back to Live Scores</Link>
        </div>
    );
};

export default MatchDetails;
