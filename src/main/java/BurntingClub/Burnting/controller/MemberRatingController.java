package BurntingClub.Burnting.controller;

import BurntingClub.Burnting.dto.RatingDTO;
import BurntingClub.Burnting.service.MemberRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberRatingController {
    private final MemberRatingService memberRatingService;
    @PostMapping("/rating")
    public RatingDTO ratingMember(@RequestBody RatingDTO ratingDTO) {
        System.out.println("ratingDTO = " + ratingDTO);
        return memberRatingService.putRating(ratingDTO);
    }
}
