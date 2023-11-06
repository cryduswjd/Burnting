package BurntingClub.Burnting.repository;

import BurntingClub.Burnting.entity.ChatChannelEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatChannelRepository extends JpaRepository<ChatChannelEntity, Long> {
    Optional<ChatChannelEntity> findByChannel(String channel);
}
