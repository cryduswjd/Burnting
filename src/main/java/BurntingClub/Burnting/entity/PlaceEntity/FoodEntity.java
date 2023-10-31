package BurntingClub.Burnting.entity.PlaceEntity;

import BurntingClub.Burnting.dto.PlaceDTO.FoodDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "foodlist")
public class FoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    @Column(unique = true)
    private String id;
    @Column
    private String name;
    @Column
    private String lat;
    @Column
    private String lng;
    @Column
    private String image;

    public static FoodEntity toFoodEntity(FoodDTO foodDTO) {
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setId(foodDTO.getId());
        foodEntity.setName(foodDTO.getName());
        foodEntity.setLat(foodDTO.getLat());
        foodEntity.setLng(foodDTO.getLng());
        foodEntity.setImage(foodDTO.getImage());
        return foodEntity;
    }
}
