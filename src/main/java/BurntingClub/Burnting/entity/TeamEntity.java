package BurntingClub.Burnting.entity;

import BurntingClub.Burnting.dto.TeamDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "team")
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    @Column(unique = true)
    private String team;
    @Column
    private String uid;
    @Column
    private String status;
    @Column
    private Long gender;

    public static TeamEntity toRoomEntity(TeamDTO teamDTO) {
        TeamEntity teammEntity = new TeamEntity();
        teammEntity.setTeam(teamDTO.getTeam());
        teammEntity.setUid(teamDTO.getUid());
        teammEntity.setStatus(teamDTO.getStatus());
        teammEntity.setGender(teamDTO.getGender());
        return teammEntity;
    }
}
