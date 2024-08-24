package dev.nano.livescore.model.dto.league;

import lombok.Data;

import java.util.List;

@Data
public class LeagueApiResponse {
    private List<LeagueResponse> response;

    @Data
    public static class LeagueResponse {
        private League league;
        private Country country;
        private List<Season> seasons;
    }

    @Data
    public static class League {
        private long id;
        private String name;
        private String type;
        private String logo;
    }

    @Data
    public static class Country {
        private String name;
        private String code;
        private String flag;
    }

    @Data
    public static class Season {
        private int year;
        private String start;
        private String end;
        private boolean current;
        private Coverage coverage;
    }

    @Data
    public static class Coverage {
        private Fixtures fixtures;
        private boolean standings;
        private boolean players;
        private boolean top_scorers;
        private boolean top_assists;
        private boolean top_cards;
        private boolean injuries;
        private boolean predictions;
        private boolean odds;
    }

    @Data
    public static class Fixtures {
        private boolean events;
        private boolean lineups;
        private boolean statistics_fixtures;
        private boolean statistics_players;
    }
}
