package dev.nano.livescore.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    private String get;
    private Parameters parameters;
    private List<Object> errors;
    private int results;
    private Paging paging;
    private List<FixtureResponse> response;

    @Data
    public static class Parameters {
        private String live;
    }

    @Data
    public static class Paging {
        private int current;
        private int total;
    }

    @Data
    public static class FixtureResponse {
        private Fixture fixture;
        private League league;
        private Teams teams;
        private Goals goals;
    }

    @Data
    public static class Fixture {
        private long id;
        private String date;
        private Status status;
    }

    @Data
    public static class Status {
        private String long_;
        private String short_;
        private int elapsed;
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
    }

    @Data
    public static class Goals {
        private Integer home;
        private Integer away;
    }
}
