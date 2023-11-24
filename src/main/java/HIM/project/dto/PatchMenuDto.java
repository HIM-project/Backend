package HIM.project.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchMenuDto {
    private Long menuId;
    private String foodName;
    private Integer price;
}
