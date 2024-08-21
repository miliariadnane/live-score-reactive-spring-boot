package dev.nano.livescore.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Data
public class Match {
    @Id
    private Long id;
    private String homeTeam;
    private String awayTeam;
    private Integer homeScore;
    private Integer awayScore;
    private String status;
    private String league;
    private LocalDateTime date;
    private String homeLogo;
    private String awayLogo;
}

