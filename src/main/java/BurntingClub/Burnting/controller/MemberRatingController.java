package BurntingClub.Burnting.controller;

import BurntingClub.Burnting.service.MemberRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberRatingController {
    private final MemberRatingService memberRatingService;
    @GetMapping("/matchedUserList")
    public String matchedUserList(@RequestParam String roomId) {
        return memberRatingService.matchedUserList(roomId);
    }
    @PostMapping("/rating")
    public String rating(@RequestParam String roomId, @RequestBody Map<String, Object> user) {
        return memberRatingService.rating(roomId, user);
    }
}
