package BurntingClub.Burnting.entity;

import BurntingClub.Burnting.dto.RatingDTO;
import BurntingClub.Burnting.entity.MemberEntity.MemberEntity;
import BurntingClub.Burnting.repository.MemberRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@Entity
@Table(name = "userRating")
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    @Column
    private String uid;
    @Column
    private String channel;
    @Column
    private Long rating;
    @Column
    private String opinion;
    @Column
    private Long declaration;

    public static RatingEntity toRatingEntity(RatingDTO ratingDTO) {
        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setUid(ratingDTO.getUid());
        ratingEntity.setChannel(ratingDTO.getChannel());
        ratingEntity.setRating(ratingDTO.getRating());
        ratingEntity.setOpinion(ratingDTO.getOpinion());
        ratingEntity.setDeclaration(ratingDTO.getDeclaration());
        return ratingEntity;
    }
}
