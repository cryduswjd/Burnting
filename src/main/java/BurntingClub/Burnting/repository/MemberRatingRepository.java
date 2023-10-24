package BurntingClub.Burnting.repository;

import BurntingClub.Burnting.entity.MemberEntity;
import BurntingClub.Burnting.entity.RatingEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRatingRepository extends JpaRepository<RatingEntity, Long> {
    @Transactional
    Optional<RatingEntity> deleteByMemberEntity_Uid(String uid);
}
