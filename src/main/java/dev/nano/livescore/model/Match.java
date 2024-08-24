package dev.nano.livescore.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Data
public class Match {
    @Id
    private Long id;

    // Fixture
    private String referee;
    private String timezone;
    private LocalDateTime date;
    private Long timestamp;

    // Venue
    private Integer venueId;
    private String venueName;
    private String venueCity;

    // Status
    private String statusLong;
    private String statusShort;
    private Integer elapsed;

    // League
    private Integer leagueId;
    private String leagueName;
    private String country;
    private String leagueLogo;
    private String leagueFlag;
    private Integer season;
    private String round;

    // Home team
    private Integer homeTeamId;
    private String homeTeam;
    private String homeLogo;
    private Boolean homeWinner;

    // Away team
    private Integer awayTeamId;
    private String awayTeam;
    private String awayLogo;
    private Boolean awayWinner;

    // Goals
    private Integer homeScore;
    private Integer awayScore;

    // Detailed scores
    private Integer halftimeHome;
    private Integer halftimeAway;
    private Integer fulltimeHome;
    private Integer fulltimeAway;
    private Integer extratimeHome;
    private Integer extratimeAway;
    private Integer penaltyHome;
    private Integer penaltyAway;
}

