package BurntingClub.Burnting.controller;

import BurntingClub.Burnting.service.MemberRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberRatingController {
    private final MemberRatingService memberRatingService;
    @PostMapping("/userlistForRating")
    public String ratingMember(@RequestParam String chatChannelCode) {
        return memberRatingService.userlistForRating(chatChannelCode);
    }
    @PostMapping("/rating")
    public String rating(@RequestParam String chatChannelCode, @RequestBody Map<String, Object> user) {
        return memberRatingService.rating(chatChannelCode, user);
    }
}
