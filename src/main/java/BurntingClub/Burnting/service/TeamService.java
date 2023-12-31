package BurntingClub.Burnting.service;

import BurntingClub.Burnting.config.RandomCode;
import BurntingClub.Burnting.dto.TeamDTO;
import BurntingClub.Burnting.entity.MemberEntity.MemberEntity;
import BurntingClub.Burnting.entity.TeamEntity;
import BurntingClub.Burnting.repository.MemberRepository;
import BurntingClub.Burnting.repository.TeamRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {
    public final TeamRepository teamRepository;
    public final MemberRepository memberRepository;
    public String createTeam() {
        RandomCode randomCode = new RandomCode();
        String teamCode;
        TeamDTO teamDTO;
        while (true) {
            teamCode = randomCode.generateRandomStrAndAssert();
            Optional<TeamEntity> checkTeam = teamRepository.findByTeam(teamCode);
            if (!checkTeam.isPresent()) {
                    teamDTO = new TeamDTO(teamCode, "", "", null, null);
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
        Optional<TeamEntity> checkTeamInfo = teamRepository.findByTeam(team);
        Long mainGender = checkTeamInfo.get().getGender();  //팀 메인 성별
        Long mainUniversity = checkTeamInfo.get().getUniversity();  //팀 메인 학교

        if (!checkTeamInfo.isPresent()) {   //코드 오류
            return "방 코드를 다시 입력해주세요.";
        }
        else {
            String existingUids = checkTeamInfo.get().getUid(); //방장 uid get
            if (existingUids != null && !existingUids.isEmpty()) {   //방장이 있을 때

                Optional<MemberEntity> memberEntity = memberRepository.findByUid(uid);

                if (!Objects.equals(mainGender, memberEntity.get().getSex())) { //다른 성별일 때
                    return "같은 성별만 참여할 수 있습니다.";
                }
                else if (!Objects.equals(mainUniversity, memberEntity.get().getUniversity_code())) { //다른 학교일 때
                    return "같은 학교만 참여할 수 있습니다.";
                }
                else {  //방장과 같은 성별/학교일 때
                    existingUids += ", " + uid;
                    teamRepository.updateUidField(existingUids, team);

                    TeamDTO responseDTO = new TeamDTO();
                    responseDTO.setTeam(checkTeamInfo.get().getTeam());
                    responseDTO.setUid(uid);
                    responseDTO.setGender(mainGender);
                    responseDTO.setUniversity(mainUniversity);
                    Gson gson = new Gson();
                    return gson.toJson(responseDTO);
                }
            }
            else {  //방장이 없을 때
                Optional<MemberEntity> memberEntity = memberRepository.findByUid(uid);
                Long gender = memberEntity.get().getSex();
                Long university = memberEntity.get().getUniversity_code();
                existingUids = uid;
                teamRepository.updateTeamField(existingUids, gender, team, university);

                TeamDTO responseDTO = new TeamDTO();
                responseDTO.setTeam(checkTeamInfo.get().getTeam());
                responseDTO.setUid(uid);
                responseDTO.setGender(gender);
                responseDTO.setUniversity(university);
                Gson gson = new Gson();
                return gson.toJson(responseDTO);
            }
        }
    }
}