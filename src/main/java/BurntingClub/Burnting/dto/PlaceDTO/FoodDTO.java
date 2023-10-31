package BurntingClub.Burnting.dto.PlaceDTO;

import BurntingClub.Burnting.entity.PlaceEntity.FoodEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FoodDTO {
    private String id;
    private String name;
    private String lat;
    private String lng;
    private String image;

    public static FoodDTO toFoodDTO(FoodEntity foodEntity) {
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setId(foodEntity.getId());
        foodDTO.setName(foodEntity.getName());
        foodDTO.setLat(foodEntity.getLat());
        foodDTO.setLat(foodEntity.getLat());
        foodDTO.setImage(foodEntity.getImage());
        return foodDTO;
    }
}
