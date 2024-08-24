import { useState, useEffect } from 'react';
import League from '../generated/dev/nano/livescore/model/League';
import { getLeagues } from '../services/api';

export const useLeagues = () => {
    const [leagues, setLeagues] = useState<League[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();
        const signal = controller.signal;

        const fetchLeagues = async () => {
            setLoading(true);
            setError(null);

            try {
                const fetchedLeagues = await getLeagues(signal);
                if (isMounted) {
                    setLeagues(fetchedLeagues);
                    setLoading(false);
                }
            } catch (err) {
                if (isMounted && err instanceof Error && err.name !== 'AbortError') {
                    console.error('Error fetching leagues:', err);
                    setError(err);
                    setLoading(false);
                }
            }
        };

        fetchLeagues();

        return () => {
            isMounted = false;
            controller.abort();
        };
    }, []);

    return { leagues, loading, error };
};
