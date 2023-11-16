package BurntingClub.Burnting.repository;

import BurntingClub.Burnting.entity.MatchedEntity.ChatChannelEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatChannelRepository extends JpaRepository<ChatChannelEntity, Long> {
    Optional<ChatChannelEntity> findByChannel(String channel);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update ChatChannelEntity c set c.status = :status where c.channel = :channel")
    void updateChannelStatusField(@Param(value = "status") String status, @Param(value = "channel") String chatChannelCode);
}
