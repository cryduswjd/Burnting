package BurntingClub.Burnting.service;

import BurntingClub.Burnting.dto.MatchedDTO.ChannelMemberDTO;
import BurntingClub.Burnting.dto.MatchedDTO.MatchedMemberDTO;
import BurntingClub.Burnting.dto.RatingDTO;
import BurntingClub.Burnting.entity.ChatChannelEntity;
import BurntingClub.Burnting.entity.MemberEntity;
import BurntingClub.Burnting.entity.RatingEntity;
import BurntingClub.Burnting.repository.ChatChannelRepository;
import BurntingClub.Burnting.repository.MemberRatingRepository;
import BurntingClub.Burnting.repository.MemberRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberRatingService {
    private final MemberRepository memberRepository;
    private final MemberRatingRepository memberRatingRepository;
    private final ChatChannelRepository chatChannelRepository;
    public String userlistForRating(String chatChannelCode) {
        ChannelMemberDTO channelMemberDTO = new ChannelMemberDTO();
        List<String> userInfo = new ArrayList<>();

        Optional<ChatChannelEntity> chatChannelEntity = chatChannelRepository.findByChannel(chatChannelCode);

        channelMemberDTO.setChannel(chatChannelEntity.get().getChannel());
        List<String> uids = List.of(chatChannelEntity.get().getUid().split(", "));

        for (String uid : uids) {
            Optional<MemberEntity> memberEntity = memberRepository.findByUid(uid);

            MatchedMemberDTO matchedMemberDTO = new MatchedMemberDTO();
            matchedMemberDTO.setUid(memberEntity.get().getUid());
            matchedMemberDTO.setNickname(memberEntity.get().getNickname());
            matchedMemberDTO.setPhotoUrl(memberEntity.get().getPhotoUrl());

            Gson gson = new Gson();
            userInfo.add(gson.toJson(matchedMemberDTO).replace("\\", ""));
        }
        channelMemberDTO.setUser(userInfo);
        Gson gson = new Gson();
        return gson.toJson(channelMemberDTO);
    }

    public String rating(String chatChannelCode, Map<String, Object> user) {

        return chatChannelCode + " and " + user.toString();
    }
}
