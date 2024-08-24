package dev.nano.livescore;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import dev.nano.livescore.model.Match;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "live-score")
public class LiveScoreApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(LiveScoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner testAllFeatures(LiveScoreService liveScoreService) {
        return args -> {

            AtomicReference<Long> matchIdRef = new AtomicReference<>();
            testLiveScores(liveScoreService, matchIdRef);

            Long matchId = matchIdRef.get();
            if (matchId != null) {
                testMatchDetails(liveScoreService, matchId);
            } else {
                System.out.println("No match ID available for details test.");
            }

            testLeagues(liveScoreService);
        };
    }

    private void testLiveScores(LiveScoreService liveScoreService, AtomicReference<Long> matchIdRef) {
        System.out.println("Fetching live scores from API...");

        Flux<Match> liveScores = liveScoreService.getLiveScoresByLeague("");

        liveScores
                .timeout(Duration.ofSeconds(10))
                .doOnNext(match -> {
                    System.out.println("Received match: " + match);
                    if (matchIdRef.get() == null) {
                        matchIdRef.set(match.getId());
                    }
                })
                .doOnError(error -> System.err.println("Error occurred: " + error.getMessage()))
                .doOnComplete(() -> System.out.println("Completed receiving live scores"))
                .blockLast();

        System.out.println("Live scores test completed.");
    }

    private void testMatchDetails(LiveScoreService liveScoreService, Long matchId) {
        System.out.println("\nTesting match details for ID: " + matchId);

        try {
            Mono<Match> matchDetailsMono = liveScoreService.getMatchDetails(matchId);

            Match matchDetails = matchDetailsMono
                    .timeout(Duration.ofSeconds(10))
                    .block();

            if (matchDetails != null) {
                System.out.println("Match details received:");
                System.out.println("ID: " + matchDetails.getId());
                System.out.println("Home Team: " + matchDetails.getHomeTeam());
                System.out.println("Away Team: " + matchDetails.getAwayTeam());
                System.out.println("Score: " + matchDetails.getHomeScore() + " - " + matchDetails.getAwayScore());
                System.out.println("Status: " + matchDetails.getStatusLong());
                System.out.println("League: " + matchDetails.getLeagueName());
                System.out.println("Date: " + matchDetails.getDate());
            } else {
                System.out.println("No match details found for ID: " + matchId);
            }
        } catch (Exception e) {
            System.err.println("Error fetching match details: " + e.getMessage());
        }

        System.out.println("Match details test completed.");
    }

    private void testLeagues(LiveScoreService liveScoreService) {
        System.out.println("\nFetching leagues...");
        liveScoreService.getLeagueList()
                .take(5)
                .subscribe(
                        league -> System.out.println("League: " + league.getName() +
                                ", Country: " + league.getCountryName() +
                                ", Season: " + league.getSeason()),
                        error -> System.err.println("Error fetching leagues: " + error.getMessage()),
                        () -> System.out.println("Completed fetching leagues")
                );
    }
}
