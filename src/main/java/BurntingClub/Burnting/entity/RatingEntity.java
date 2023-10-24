package BurntingClub.Burnting.entity;

import BurntingClub.Burnting.dto.RatingDTO;
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
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private MemberEntity memberEntity;
    @Column
    private String rating;
    @Column
    private String opinion;

    public static RatingEntity toRatingEntity(RatingDTO ratingDTO, MemberRepository memberRepository) {
        RatingEntity ratingEntity = new RatingEntity();
        Optional<MemberEntity> memberEntity = memberRepository.findByUid(ratingDTO.getUid());
        ratingEntity.setMemberEntity(memberEntity.get());
        ratingEntity.setRating(ratingDTO.getRating());
        ratingEntity.setOpinion(ratingDTO.getOpinion());
        return ratingEntity;
    }
}
