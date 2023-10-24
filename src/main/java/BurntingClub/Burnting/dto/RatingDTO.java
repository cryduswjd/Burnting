package BurntingClub.Burnting.dto;

import BurntingClub.Burnting.entity.RatingEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RatingDTO {
    private String uid;
    private String rating;
    private String opinion;

    public static RatingDTO toRatingDTO(RatingEntity ratingEntity) {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setUid(ratingEntity.getMemberEntity().getUid());
        ratingDTO.setRating(ratingEntity.getRating());
        ratingDTO.setOpinion(ratingEntity.getOpinion());
        return ratingDTO;
    }
}
