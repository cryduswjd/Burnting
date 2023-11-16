package BurntingClub.Burnting.entity;

import BurntingClub.Burnting.dto.UniversityDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "university")
public class UniversityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    @Column
    private String university;
    @Column
    private String lng; //경도 x
    @Column
    private String lat; //위도 y

    public static UniversityEntity toUniversityEntity(UniversityDTO universityDTO) {
        UniversityEntity universityEntity = new UniversityEntity();
        universityEntity.setUniversity(universityDTO.getUniversity());
        universityEntity.setLng(universityDTO.getLng());
        universityEntity.setLat(universityDTO.getLat());
        return universityEntity;
    }
}
