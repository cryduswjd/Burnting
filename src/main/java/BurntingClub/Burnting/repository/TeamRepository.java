package BurntingClub.Burnting.repository;

import BurntingClub.Burnting.entity.TeamEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    Optional<TeamEntity> findByTeam(String team);
    Optional<TeamEntity> findByStatusAndUid(String status, String uid);
    List<TeamEntity> findByStatus(String status);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update TeamEntity t set t.uid = :uid where t.team = :team")
    void updateUidField(@Param(value = "uid") String uid, @Param(value = "team") String team);
}
