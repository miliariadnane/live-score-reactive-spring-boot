package dev.nano.livescore.services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import com.vaadin.hilla.Nonnull;
import dev.nano.livescore.LiveScoreService;
import dev.nano.livescore.model.Match;
import reactor.core.publisher.Flux;

@Endpoint
@AnonymousAllowed
public class LiveScoreEndpoint {

    private final LiveScoreService liveScoreService;

    public LiveScoreEndpoint(LiveScoreService liveScoreService) {
        this.liveScoreService = liveScoreService;
    }

    public @Nonnull Flux<Match> getLiveScores(String league) {
        return liveScoreService.getLiveScoresByLeague(league);
    }
}
