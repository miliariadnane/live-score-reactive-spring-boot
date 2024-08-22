import { getLiveScores as getLiveScoresEndpoint } from 'Frontend/generated/LiveScoreEndpoint';
import Match from 'Frontend/generated/dev/nano/livescore/model/Match';

export const getLiveScores = async (league: string = '', signal?: AbortSignal): Promise<Match[]> => {
    return new Promise((resolve, reject) => {
        let matches: Match[] = [];
        let subscription: { cancel: () => void };

        const onNext = (match: Match) => {
            matches.push(match);
        };

        const onComplete = () => {
            resolve(matches);
        };

        const onError = () => {
            console.error('Error fetching live scores');
            reject(new Error('Error fetching live scores'));
        };

        try {
            subscription = getLiveScoresEndpoint(league).onNext(onNext).onComplete(onComplete).onError(onError);
        } catch (error) {
            console.error('Error setting up live scores subscription:', error);
            reject(error);
        }

        if (signal) {
            signal.addEventListener('abort', () => {
                if (subscription) {
                    subscription.cancel();
                }
                reject(new DOMException('Aborted', 'AbortError'));
            });
        }
    });
};

export type Subscription = {
    cancel: () => void;
};

export { getLiveScoresEndpoint };
