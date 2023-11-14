package BurntingClub.Burnting.controller;

import BurntingClub.Burnting.service.ChatChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class ChatChannelController {
    private final ChatChannelService chatChannelService;
    @PostMapping("/statusReady")
    public String statusReady(@RequestParam String team) {
        return chatChannelService.statusReady(team);
    }   //매칭 신청(매칭 전 준비 상태/매칭 중) & 파이어베이스 데이터베이스에 status 저장
    @PostMapping("/statusDeath")
    public String statusDeath(@RequestParam String chatChannelCode) throws ExecutionException, InterruptedException {
        return chatChannelService.statusDeath(chatChannelCode);
    }   //매칭 종료 & 파이어베이스 데이터베이스에 status 수정
}
