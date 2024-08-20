package dev.nano.livescore;

import dev.nano.livescore.model.Match;
import dev.nano.livescore.model.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class FootballApiService {

    private final WebClient webClient;

    public Flux<Match> getLiveScores() {
        return webClient.get()
                .uri("/fixtures?live=all")
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .flatMapMany(apiResponse -> Flux.fromIterable(apiResponse.getResponse()))
                .map(this::convertToMatch);
    }

    private Match convertToMatch(ApiResponse.FixtureResponse fixtureResponse) {
        Match match = new Match();
        match.setId(fixtureResponse.getFixture().getId());
        match.setHomeTeam(fixtureResponse.getTeams().getHome().getName());
        match.setAwayTeam(fixtureResponse.getTeams().getAway().getName());
        match.setHomeScore(fixtureResponse.getGoals().getHome());
        match.setAwayScore(fixtureResponse.getGoals().getAway());
        match.setStatus(fixtureResponse.getFixture().getStatus().getLong_());
        match.setLeague(fixtureResponse.getLeague().getName());
        match.setDate(LocalDateTime.parse(fixtureResponse.getFixture().getDate(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        match.setHomeLogo(fixtureResponse.getTeams().getHome().getLogo());
        match.setAwayLogo(fixtureResponse.getTeams().getAway().getLogo());
        return match;
    }
}
