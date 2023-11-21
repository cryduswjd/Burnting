package BurntingClub.Burnting.repository;

import BurntingClub.Burnting.entity.RatingEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRatingRepository extends JpaRepository<RatingEntity, Long> {
    @Transactional
    Optional<RatingEntity> deleteByUid(String uid);
}
