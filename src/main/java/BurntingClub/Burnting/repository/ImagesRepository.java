package BurntingClub.Burnting.repository;

import BurntingClub.Burnting.entity.ImagesEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ImagesRepository extends JpaRepository<ImagesEntity, Long> {
    List<ImagesEntity> findByUid(String uid);
    @Transactional
    @Modifying
    @Query(value = "delete from ImagesEntity i where i.imageUrl = :image")
    void deleteByImageUrl(String image);
}
