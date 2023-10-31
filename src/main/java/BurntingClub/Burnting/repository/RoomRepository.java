package BurntingClub.Burnting.repository;

import BurntingClub.Burnting.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
}
