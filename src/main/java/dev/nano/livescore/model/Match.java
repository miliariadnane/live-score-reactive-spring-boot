package dev.nano.livescore.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("matches")
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

