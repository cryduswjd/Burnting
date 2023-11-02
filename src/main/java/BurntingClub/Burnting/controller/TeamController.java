package BurntingClub.Burnting.controller;

import BurntingClub.Burnting.dto.TeamDTO;
import BurntingClub.Burnting.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    @PostMapping("/createTeam")
    public String createRoomCode() {
        return teamService.createTeam();
    }   //방 코드 생성
    @PostMapping("/enterTeam")
    public String enterTeam(@RequestParam String uid, @RequestParam String team) {
        return teamService.enterTeam(uid, team);
    }   //방 참여하기
}
