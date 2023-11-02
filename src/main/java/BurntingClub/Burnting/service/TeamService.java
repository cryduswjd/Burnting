package BurntingClub.Burnting.service;

import BurntingClub.Burnting.config.RandomCode;
import BurntingClub.Burnting.dto.TeamDTO;
import BurntingClub.Burnting.entity.TeamEntity;
import BurntingClub.Burnting.repository.TeamRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {
    public final TeamRepository teamRepository;
    public String createTeam() {
        RandomCode randomCode = new RandomCode();
        String teamCode;
        TeamDTO teamDTO;
        while (true) {
            teamCode = randomCode.generateRandomStrAndAssert();
            Optional<TeamEntity> checkTeam = teamRepository.findByTeam(teamCode);
            if (!checkTeam.isPresent()) {
                    teamDTO = new TeamDTO(teamCode, "", "");
                    TeamEntity roomEntity = TeamEntity.toRoomEntity(teamDTO);
                    teamRepository.save(roomEntity);

                    TeamDTO responseDTO = new TeamDTO();
                    responseDTO.setTeam(teamDTO.getTeam());
                    Gson gson = new Gson();
                    return gson.toJson(responseDTO);
            }
        }
    }
    public String enterTeam(String uid, String team) {
        Optional<TeamEntity> checkTeamCode = teamRepository.findByTeam(team);
        if (!checkTeamCode.isPresent()) {
            return "방 코드를 다시 입력해주세요.";
        }
        else {
            String existingUids = checkTeamCode.get().getUid();
            if(existingUids != null && !existingUids.isEmpty()) {
                existingUids += ", " + uid;
            } else {
                existingUids = uid;
            }
            teamRepository.updateUidField(existingUids, team);

            TeamDTO responseDTO = new TeamDTO();
            responseDTO.setTeam(checkTeamCode.get().getTeam());
            responseDTO.setUid(uid);
            Gson gson = new Gson();
            return gson.toJson(responseDTO);
        }
    }
}