package dev.nano.livescore;

import dev.nano.livescore.model.League;
import dev.nano.livescore.model.Match;
import dev.nano.livescore.model.dto.league.LeagueApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LiveScoreService {

    private final FootballApiService footballApiService;

    public Flux<Match> getLiveScoresByLeague(String league) {
        return footballApiService.getLiveScores()
                .filter(match -> league.isEmpty() ||
                        (match.getLeagueName() != null &&
                                match.getLeagueName().equalsIgnoreCase(league)));
    }

    public Mono<Match> getMatchDetails(Long matchId) {
        return footballApiService.getMatchDetails(matchId);
    }

    public Flux<League> getLeagueList() {
        return footballApiService.getLeagues();
    }
}
