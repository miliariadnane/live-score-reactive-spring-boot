package dev.nano.livescore;

import dev.nano.livescore.model.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class LiveScoreService {

    private final FootballApiService footballApiService;

    public Flux<Match> getLiveScoresByLeague(String league) {
        return footballApiService.getLiveScores()
                .filter(match -> league.isEmpty() || match.getLeague().equalsIgnoreCase(league));
    }
}
