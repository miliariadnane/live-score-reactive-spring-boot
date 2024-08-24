package dev.nano.livescore.services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import com.vaadin.hilla.Nonnull;
import dev.nano.livescore.LiveScoreService;
import dev.nano.livescore.model.League;
import dev.nano.livescore.model.Match;
import dev.nano.livescore.model.dto.league.LeagueApiResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Endpoint
@AnonymousAllowed
public class LiveScoreEndpoint {

    private final LiveScoreService liveScoreService;

    public LiveScoreEndpoint(LiveScoreService liveScoreService) {
        this.liveScoreService = liveScoreService;
    }

    public @Nonnull Mono<Match> getMatchDetails(Long matchId) {
        return liveScoreService.getMatchDetails(matchId);
    }

    public @Nonnull Flux<Match> getLiveScores(String league) {
        return liveScoreService.getLiveScoresByLeague(league);
    }

    public @Nonnull Flux<League> getLeagues() {
        return liveScoreService.getLeagueList();
    }
}
