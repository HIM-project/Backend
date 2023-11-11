package HIM.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    private String category;

    private String restaurantName;

    private String restaurantThumbnail;

    private String restaurantLocation;

    private String restaurantNumber;

    private String crNumber;

    private LocalDateTime openingHours;


}
