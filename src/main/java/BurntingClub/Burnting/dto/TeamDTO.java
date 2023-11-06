package BurntingClub.Burnting.dto;

import BurntingClub.Burnting.entity.TeamEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamDTO {
    private String team;
    private String uid;
    private String status;
    private Long gender;

    public static TeamDTO toTeamDTO(TeamEntity teamEntity) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeam(teamEntity.getTeam());
        teamDTO.setUid(teamEntity.getUid());
        teamDTO.setStatus(teamEntity.getStatus());
        teamDTO.setGender(teamEntity.getGender());
        return teamDTO;
    }
}
