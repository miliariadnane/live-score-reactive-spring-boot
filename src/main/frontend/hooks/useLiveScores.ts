import { useState, useEffect, useCallback } from 'react';
import Match from '../generated/dev/nano/livescore/model/Match';
import { getLiveScores } from '../services/api';

export const useLiveScores = (league: string = '') => {
    const [matches, setMatches] = useState<Match[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<Error | null>(null);

    const fetchScores = useCallback(async () => {
        setLoading(true);
        setError(null);

        try {
            const fetchedMatches = await getLiveScores(league);
            setMatches(fetchedMatches);
            setLoading(false);
        } catch (err) {
            if (err instanceof Error) {
                console.error('Error fetching live scores:', err);
                setError(err);
                setLoading(false);
            }
        }
    }, [league]);

    useEffect(() => {
        fetchScores();

        const intervalId = setInterval(fetchScores, 60000); // Update every minute

        return () => clearInterval(intervalId);
    }, [fetchScores]);

    const refetch = useCallback(() => {
        fetchScores();
    }, [fetchScores]);

    return { matches, loading, error, refetch };
};
