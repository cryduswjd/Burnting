package BurntingClub.Burnting.entity.PlaceEntity;

import BurntingClub.Burnting.dto.PlaceDTO.CafeDTO;
import BurntingClub.Burnting.dto.PlaceDTO.FoodDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "cafelist")
public class CafeEntity {
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

    public static CafeEntity toCafeEntity(CafeDTO cafeDTO) {
        CafeEntity cafeEntity = new CafeEntity();
        cafeEntity.setId(cafeDTO.getId());
        cafeEntity.setName(cafeDTO.getName());
        cafeEntity.setLat(cafeDTO.getLat());
        cafeEntity.setLng(cafeDTO.getLng());
        cafeEntity.setImage(cafeDTO.getImage());
        return cafeEntity;
    }
}
