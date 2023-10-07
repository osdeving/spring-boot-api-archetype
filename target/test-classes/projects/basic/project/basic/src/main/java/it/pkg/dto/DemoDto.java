package it.pkg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DemoDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("age")
    private Long age;
}
