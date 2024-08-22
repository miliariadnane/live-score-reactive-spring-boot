import { useState, useEffect, useCallback } from 'react';
import Match from '../generated/dev/nano/livescore/model/Match';
import { getLiveScores } from '../services/api';

export const useLiveScores = (league: string = '') => {
    const [matches, setMatches] = useState<Match[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<Error | null>(null);

    const updateMatches = useCallback((newMatch: Match) => {
        setMatches(prevMatches => {
            const index = prevMatches.findIndex(match => match.id === newMatch.id);
            if (index !== -1) {
                // Update existing match
                return prevMatches.map((match, i) => i === index ? newMatch : match);
            } else {
                // Add new match
                return [...prevMatches, newMatch];
            }
        });
        setLoading(false);
    }, []);

    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();
        const signal = controller.signal;

        const fetchScores = async () => {
            setLoading(true);
            setError(null);

            try {
                const fetchedMatches = await getLiveScores(league, signal);
                if (isMounted) {
                    setMatches(fetchedMatches);
                    setLoading(false);
                }
            } catch (err) {
                if (isMounted && err instanceof Error && err.name !== 'AbortError') {
                    console.error('Error fetching live scores:', err);
                    setError(err);
                    setLoading(false);
                }
            }
        };

        fetchScores();

        // Set up polling for live updates
        const intervalId = setInterval(fetchScores, 60000); /* Update every minute */

        return () => {
            isMounted = false;
            controller.abort();
            clearInterval(intervalId);
        };
    }, [league]);

    const refetch = useCallback(() => {
        setLoading(true);
        getLiveScores(league)
            .then(fetchedMatches => {
                setMatches(fetchedMatches);
                setLoading(false);
            })
            .catch(err => {
                console.error('Error refetching live scores:', err);
                setError(err);
                setLoading(false);
            });
    }, [league]);

    return { matches, loading, error, refetch };
};
