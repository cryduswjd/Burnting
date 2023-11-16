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
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update TeamEntity t set t.uid = :uid where t.team = :team")
    void updateUidField(@Param(value = "uid") String uid, @Param(value = "team") String team);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update TeamEntity t set t.uid = :uid, t.gender = :gender, t.university = :university where t.team = :team")
    void updateTeamField(@Param(value = "uid") String uid, @Param(value = "gender") Long gender, @Param(value = "team") String team, @Param(value = "university") Long university);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update TeamEntity t set t.status = :status where t.team = :team")
    void updateTeamStatusField(@Param(value = "status") String status, @Param(value = "team") String team);
    List<TeamEntity> findByStatus(String status);
}
