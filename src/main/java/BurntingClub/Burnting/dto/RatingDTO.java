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
    private Long rating;  //점수
    private String opinion; //한줄 평가
    private Long declaration; //신고 누적횟수

    public static RatingDTO toRatingDTO(RatingEntity ratingEntity) {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setUid(ratingEntity.getMemberEntity().getUid());
        ratingDTO.setRating(ratingEntity.getRating());
        ratingDTO.setOpinion(ratingEntity.getOpinion());
        ratingDTO.setDeclaration(ratingEntity.getDeclaration());
        return ratingDTO;
    }
}
