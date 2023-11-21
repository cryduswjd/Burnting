package BurntingClub.Burnting.service;

import BurntingClub.Burnting.dto.MatchedDTO.ChannelMemberDTO;
import BurntingClub.Burnting.dto.MatchedDTO.MatchedMemberDTO;
import BurntingClub.Burnting.entity.MatchedEntity.ChatChannelEntity;
import BurntingClub.Burnting.entity.MemberEntity.MemberEntity;
import BurntingClub.Burnting.entity.RatingEntity;
import BurntingClub.Burnting.repository.ChatChannelRepository;
import BurntingClub.Burnting.repository.MemberRatingRepository;
import BurntingClub.Burnting.repository.MemberRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberRatingService {
    private final MemberRepository memberRepository;
    private final MemberRatingRepository memberRatingRepository;
    private final ChatChannelRepository chatChannelRepository;
    public String matchedUserList(String roomId) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Optional<ChatChannelEntity> userList = chatChannelRepository.findByChannel(roomId);

        Map<String, Object> userLists = new HashMap<>();
        userLists.put("roomId", roomId);

        List<Map<String, Object>> userInfoList = new ArrayList<>();

        for (String userId : userList.get().getUid().split(", ")) {
            Map<String, Object> userInfo = new HashMap<>();
            Optional<MemberEntity> memberEntity = memberRepository.findByUid(userId);
            userInfo.put("uid", memberEntity.get().getUid());
            userInfo.put("nickName", memberEntity.get().getNickname());
            userInfo.put("photoUrl", memberEntity.get().getPhotoUrl());
            userInfoList.add(userInfo);
        }

        userLists.put("users", userInfoList);

        return gson.toJson(userLists);
    }

    public String rating(String roomId, Map<String, Object> user) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        RatingEntity ratingEntity = new RatingEntity();

        if (user.containsKey("rating")) {
            List<Map<String, Object>> ratingList = (List<Map<String, Object>>) user.get("rating");

            for (Map<String, Object> rating : ratingList) {
                String uid = (String) rating.get("uid");
                int ratingValue = ((Number) rating.get("rating")).intValue();
                String opinion = (String) rating.get("opinion");

                ratingEntity.setChannel(roomId);
                ratingEntity.setUid(uid);
                ratingEntity.setRating((long) ratingValue);
                ratingEntity.setOpinion(opinion);
                memberRatingRepository.save(ratingEntity);
            }
        }

        return gson.toJson(user);
    }
}
