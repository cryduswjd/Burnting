package BurntingClub.Burnting.service;

import BurntingClub.Burnting.dto.BasicDTO;
import BurntingClub.Burnting.dto.MemberDTO.MemberDTO;
import BurntingClub.Burnting.dto.UniversityListDTO;
import BurntingClub.Burnting.entity.MemberEntity.ImagesEntity;
import BurntingClub.Burnting.entity.MemberEntity.MemberEntity;
import BurntingClub.Burnting.entity.UniversityEntity;
import BurntingClub.Burnting.repository.ImagesRepository;
import BurntingClub.Burnting.repository.MemberRatingRepository;
import BurntingClub.Burnting.repository.MemberRepository;
import BurntingClub.Burnting.repository.UniversityRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static BurntingClub.Burnting.entity.MemberEntity.MemberEntity.toMemberEntity;

@Service
@RequiredArgsConstructor
public class MemberService{
    public final MemberRepository memberRepository;
    public final UniversityRepository universityRepository;
    public final MemberRatingRepository memberRatingRepository;
    public final ImagesRepository imagesRepository;
    public String insertMember(MemberDTO memberDTO) {
        Optional<MemberEntity> memberEntity = memberRepository.findByUid(memberDTO.getUid());

        if (memberEntity.isPresent()) {
            Optional<MemberDTO> getUserInfo = memberRepository.getUidInfo(memberDTO.getUid());

            Gson gson = new GsonBuilder().serializeNulls().create();

            MemberDTO result = new MemberDTO();
            result.setUid(getUserInfo.get().getUid());
            result.setEmail(getUserInfo.get().getEmail());
            result.setDisplayName(getUserInfo.get().getDisplayName());
            result.setPhotoUrl(getUserInfo.get().getPhotoUrl());
            result.setNickname(getUserInfo.get().getNickname());
            result.setUniversity(getUserInfo.get().getUniversity());
            result.setMajor(getUserInfo.get().getMajor());
            result.setInfotext(getUserInfo.get().getInfotext());
            result.setSex(getUserInfo.get().getSex());

            return gson.toJson(result);
        } else {
            MemberEntity newMemberEntity = toMemberEntity(memberDTO);
            memberRepository.save(newMemberEntity);
            Gson gson = new Gson();
            return gson.toJson(memberDTO);
        }
    }
    public String getMemberDetail(String uid) {
        Optional<MemberDTO> memberDTO = memberRepository.getUidInfo(uid);

        Gson gson = new GsonBuilder().serializeNulls().create();

        if (!memberDTO.get().getUid().isEmpty()) {
            MemberDTO result = new MemberDTO();
            result.setUid(memberDTO.get().getUid());
            result.setEmail(memberDTO.get().getEmail());
            result.setDisplayName(memberDTO.get().getDisplayName());
            result.setPhotoUrl(memberDTO.get().getPhotoUrl());
            result.setNickname(memberDTO.get().getNickname());
            result.setUniversity(memberDTO.get().getUniversity());
            result.setMajor(memberDTO.get().getMajor());
            result.setInfotext(memberDTO.get().getInfotext());
            result.setSex(memberDTO.get().getSex());

            return gson.toJson(result);
        }
        else {
            BasicDTO basicDTO = new BasicDTO();
            basicDTO.setMessage("uid가 존재하지 않습니다.");
            return gson.toJson(basicDTO);
        }
    }
    public String updateMember(MemberEntity memberEntity) {
        memberRepository.updateInfo(memberEntity.getNickname(), memberEntity.getPhotoUrl(), memberEntity.getAge(), memberEntity.getUniversity_code(), memberEntity.getMajor(), memberEntity.getInfotext(), memberEntity.getSex(), memberEntity.getUid());
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(memberEntity);
    }
    public String selectUniversity() {
        Iterable<UniversityListDTO> universityListDTO = universityRepository.findByNumAndUniversity();
        Gson gson = new Gson();
        return gson.toJson(universityListDTO);
    }
    public String deleteMember(String uid) {
        memberRatingRepository.deleteByMemberEntity_Uid(uid);
        memberRepository.deleteByUid(uid);
        return "User \"" + uid + "\" Delete Successfully";
    }
    public String insertFeedImgs(String uid, List<String> images) {
        for (String image : images) {
            ImagesEntity imagesEntity = ImagesEntity.toImagesEntity(uid, image);
            imagesRepository.save(imagesEntity);
        }
        return "User \"" + uid + "\" Feed Images \"" + images + "\" Insert Successfully";
    }
    public String deleteFeedImgs(String uid, List<String> images) {
        for (String image: images) {
            imagesRepository.deleteByImageUrl(image);
        }
        return "User \"" + uid + "\" Feed Images \"" + images + "\" delete Successfully";
    }
    public String getFeedImgs(String uid) {
        List<ImagesEntity> imagesEntities = imagesRepository.findByUidOrderByNumDesc(uid);
        List<String> imageUrls = imagesEntities.stream()
                .map(imagesEntity -> imagesEntity.getImageUrl())
                .collect(Collectors.toList());
        String jsonArray = imageUrls.stream()
                .map(url -> "\"" + url + "\"")
                .collect(Collectors.joining(", ", "[", "]"));
        String jsonString = "{\"images\":" + jsonArray + "}";

        return jsonString;
    }
}