package HIM.project.dto;

import HIM.project.entity.type.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    @JsonProperty("category")
    private Category category;

    @JsonProperty("restaurantName")
    private String restaurantName;

    @JsonProperty("restaurantThumbnail")
    private String restaurantThumbnail;

    @JsonProperty("restaurantLocation")
    private String restaurantLocation;

    @JsonProperty("restaurantNumber")
    private String restaurantNumber;

    @JsonProperty("crNumber")
    private String crNumber;

    @JsonProperty("restaurantExplanation")
    private String restaurantExplanation;

}