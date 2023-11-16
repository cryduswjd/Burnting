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
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private MemberEntity memberEntity;
    @Column
    private Long rating;
    @Column
    private String opinion;
    @Column
    private Long declaration;

    public static RatingEntity toRatingEntity(RatingDTO ratingDTO, MemberRepository memberRepository) {
        RatingEntity ratingEntity = new RatingEntity();
        Optional<MemberEntity> memberEntity = memberRepository.findByUid(ratingDTO.getUid());
        ratingEntity.setMemberEntity(memberEntity.get());
        ratingEntity.setRating(ratingDTO.getRating());
        ratingEntity.setOpinion(ratingDTO.getOpinion());
        ratingEntity.setDeclaration(ratingDTO.getDeclaration());
        return ratingEntity;
    }
}
