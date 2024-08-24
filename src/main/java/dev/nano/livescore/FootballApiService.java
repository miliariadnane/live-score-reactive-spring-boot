package dev.nano.livescore;

import dev.nano.livescore.model.League;
import dev.nano.livescore.model.Match;
import dev.nano.livescore.model.dto.ApiResponse;
import dev.nano.livescore.model.dto.league.LeagueApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Mono<Match> getMatchDetails(Long matchId) {
        return webClient.get()
                .uri("/fixtures?id=" + matchId)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .flatMap(apiResponse -> Mono.justOrEmpty(apiResponse.getResponse().stream().findFirst()))
                .map(this::convertToMatch);
    }

    public Flux<League> getLeagues() {
        return webClient.get()
                .uri("/leagues")
                .retrieve()
                .bodyToMono(LeagueApiResponse.class)
                .flatMapMany(apiResponse -> Flux.fromIterable(apiResponse.getResponse()))
                .map(this::convertToLeague)
                .doOnNext(league -> System.out.println("Converted league: " + league.getName()))
                .doOnError(error -> System.err.println("Error fetching leagues: " + error.getMessage()));
    }

    private League convertToLeague(LeagueApiResponse.LeagueResponse leagueResponse) {
        League league = new League();
        league.setId(leagueResponse.getLeague().getId());
        league.setName(leagueResponse.getLeague().getName());
        league.setType(leagueResponse.getLeague().getType());
        league.setLogo(leagueResponse.getLeague().getLogo());
        league.setCountryName(leagueResponse.getCountry().getName());
        league.setCountryCode(leagueResponse.getCountry().getCode());
        league.setCountryFlag(leagueResponse.getCountry().getFlag());

        if (!leagueResponse.getSeasons().isEmpty()) {
            league.setSeason(leagueResponse.getSeasons().get(0).toString());
        }

        return league;
    }

    private Match convertToMatch(ApiResponse.FixtureResponse fixtureResponse) {
        Match match = new Match();

        match.setId(fixtureResponse.getFixture().getId());
        match.setReferee(fixtureResponse.getFixture().getReferee());
        match.setTimezone(fixtureResponse.getFixture().getTimezone());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        match.setDate(LocalDateTime.parse(fixtureResponse.getFixture().getDate(), formatter));
        match.setTimestamp(fixtureResponse.getFixture().getTimestamp());

        if (fixtureResponse.getFixture().getVenue() != null) {
            match.setVenueId(fixtureResponse.getFixture().getVenue().getId());
            match.setVenueName(fixtureResponse.getFixture().getVenue().getName());
            match.setVenueCity(fixtureResponse.getFixture().getVenue().getCity());
        }

        if (fixtureResponse.getFixture().getStatus() != null) {
            match.setStatusLong(fixtureResponse.getFixture().getStatus().getLong_());
            match.setStatusShort(fixtureResponse.getFixture().getStatus().getShort_());
            match.setElapsed(fixtureResponse.getFixture().getStatus().getElapsed());
        }

        match.setLeagueId(fixtureResponse.getLeague().getId());
        match.setLeagueName(fixtureResponse.getLeague().getName());
        match.setCountry(fixtureResponse.getLeague().getCountry());
        match.setLeagueLogo(fixtureResponse.getLeague().getLogo());
        match.setLeagueFlag(fixtureResponse.getLeague().getFlag());
        match.setSeason(fixtureResponse.getLeague().getSeason());
        match.setRound(fixtureResponse.getLeague().getRound());

        match.setHomeTeamId(fixtureResponse.getTeams().getHome().getId());
        match.setHomeTeam(fixtureResponse.getTeams().getHome().getName());
        match.setHomeLogo(fixtureResponse.getTeams().getHome().getLogo());
        match.setHomeWinner(fixtureResponse.getTeams().getHome().getWinner());

        match.setAwayTeamId(fixtureResponse.getTeams().getAway().getId());
        match.setAwayTeam(fixtureResponse.getTeams().getAway().getName());
        match.setAwayLogo(fixtureResponse.getTeams().getAway().getLogo());
        match.setAwayWinner(fixtureResponse.getTeams().getAway().getWinner());

        match.setHomeScore(fixtureResponse.getGoals().getHome());
        match.setAwayScore(fixtureResponse.getGoals().getAway());

        if (fixtureResponse.getScore() != null) {
            if (fixtureResponse.getScore().getHalftime() != null) {
                match.setHalftimeHome(fixtureResponse.getScore().getHalftime().getHome());
                match.setHalftimeAway(fixtureResponse.getScore().getHalftime().getAway());
            }
            if (fixtureResponse.getScore().getFulltime() != null) {
                match.setFulltimeHome(fixtureResponse.getScore().getFulltime().getHome());
                match.setFulltimeAway(fixtureResponse.getScore().getFulltime().getAway());
            }
            if (fixtureResponse.getScore().getExtratime() != null) {
                match.setExtratimeHome(fixtureResponse.getScore().getExtratime().getHome());
                match.setExtratimeAway(fixtureResponse.getScore().getExtratime().getAway());
            }
            if (fixtureResponse.getScore().getPenalty() != null) {
                match.setPenaltyHome(fixtureResponse.getScore().getPenalty().getHome());
                match.setPenaltyAway(fixtureResponse.getScore().getPenalty().getAway());
            }
        }

        return match;
    }
}
