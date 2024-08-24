package dev.nano.livescore.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    private List<FixtureResponse> response;

    @Data
    public static class FixtureResponse {
        private Fixture fixture;
        private League league;
        private Teams teams;
        private Goals goals;
        private Score score;
    }

    @Data
    public static class Fixture {
        private long id;
        private String referee;
        private String timezone;
        private String date;
        private long timestamp;
        private Periods periods;
        private Venue venue;
        private Status status;
    }

    @Data
    public static class Periods {
        private Integer first;
        private Integer second;
    }

    @Data
    public static class Venue {
        private Integer id;
        private String name;
        private String city;
    }

    @Data
    public static class Status {
        private String long_;
        private String short_;
        private Integer elapsed;
    }

    @Data
    public static class League {
        private int id;
        private String name;
        private String country;
        private String logo;
        private String flag;
        private int season;
        private String round;
    }

    @Data
    public static class Teams {
        private Team home;
        private Team away;
    }

    @Data
    public static class Team {
        private int id;
        private String name;
        private String logo;
        private Boolean winner;
    }

    @Data
    public static class Goals {
        private Integer home;
        private Integer away;
    }

    @Data
    public static class Score {
        private Goals halftime;
        private Goals fulltime;
        private Goals extratime;
        private Goals penalty;
    }
}
