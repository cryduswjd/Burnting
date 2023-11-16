package BurntingClub.Burnting.repository;

import BurntingClub.Burnting.dto.UniversityListDTO;
import BurntingClub.Burnting.entity.UniversityEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UniversityRepository extends CrudRepository<UniversityEntity, Long> {
    @Query("SELECT new BurntingClub.Burnting.dto.UniversityListDTO(u.num, u.university) FROM UniversityEntity u")
    Iterable<UniversityListDTO> findByNumAndUniversity();
    Optional<UniversityEntity> findByNum(Long universityCode);
}
