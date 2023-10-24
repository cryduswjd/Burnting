package BurntingClub.Burnting.service;

import BurntingClub.Burnting.dto.RatingDTO;
import BurntingClub.Burnting.entity.RatingEntity;
import BurntingClub.Burnting.repository.MemberRatingRepository;
import BurntingClub.Burnting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberRatingService {
    private final MemberRepository memberRepository;
    private final MemberRatingRepository memberRatingRepository;
    public RatingDTO putRating(RatingDTO ratingDTO) {
        RatingEntity ratingEntity = RatingEntity.toRatingEntity(ratingDTO, memberRepository);
        RatingEntity saveRatingEntity = memberRatingRepository.save(ratingEntity);
        return RatingDTO.toRatingDTO(saveRatingEntity);
    }
}
