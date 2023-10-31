package BurntingClub.Burnting.repository.PlaceRepository;

import BurntingClub.Burnting.entity.PlaceEntity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
}
