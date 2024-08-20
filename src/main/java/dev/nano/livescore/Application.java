package dev.nano.livescore;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import dev.nano.livescore.model.Match;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "live-score")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner testLiveScores(LiveScoreService liveScoreService) {
        return args -> {
            System.out.println("Fetching live scores from API...");

            Flux<Match> liveScores = liveScoreService.getLiveScoresByLeague("");

            liveScores
                    .timeout(Duration.ofSeconds(10))
                    .doOnNext(match -> System.out.println("Received match: " + match))
                    .doOnError(error -> System.err.println("Error occurred: " + error.getMessage()))
                    .doOnComplete(() -> System.out.println("Completed receiving live scores"))
                    .blockLast();

            System.out.println("Test completed.");
        };
    }
}
