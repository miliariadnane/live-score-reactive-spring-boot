package dev.nano.livescore.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class League {
    @Id
    private Long id;
    private String name;
    private String type;
    private String logo;
    private String countryName;
    private String countryCode;
    private String countryFlag;
    private String season;
}
