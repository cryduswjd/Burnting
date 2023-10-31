package BurntingClub.Burnting.dto.PlaceDTO;

import BurntingClub.Burnting.entity.PlaceEntity.CafeEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CafeDTO {
    private String id;
    private String name;
    private String lat;
    private String lng;
    private String image;

    public static CafeDTO toCafeDTO(CafeEntity cafeEntity) {
        CafeDTO cafeDTO = new CafeDTO();
        cafeDTO.setId(cafeEntity.getId());
        cafeDTO.setName(cafeEntity.getName());
        cafeDTO.setLat(cafeEntity.getLat());
        cafeDTO.setLat(cafeEntity.getLat());
        cafeDTO.setImage(cafeEntity.getImage());
        return cafeDTO;
    }
}
