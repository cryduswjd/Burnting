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
        Gson gson = new GsonBuilder().serializeNulls().create();

        if (memberEntity.isPresent()) {
            MemberDTO result = new MemberDTO();
            Optional<UniversityEntity> universityEntity = universityRepository.findByNum(memberEntity.get().getUniversity_code());
            result.setUid(memberEntity.get().getUid());
            result.setEmail(memberEntity.get().getEmail());
            result.setDisplayName(memberEntity.get().getDisplayName());
            result.setPhotoUrl(memberEntity.get().getPhotoUrl());
            result.setNickname(memberEntity.get().getNickname());
            result.setMajor(memberEntity.get().getMajor());
            result.setInfotext(memberEntity.get().getInfotext());
            result.setSex(memberEntity.get().getSex());
            //학교 설정이 안되어있다면
            if (memberEntity.get().getUniversity_code() == null) {
                result.setUniversity(null);
                result.setUniversityCode(null);
            } else {
                result.setUniversity(universityEntity.get().getUniversity());
                result.setUniversityCode(memberEntity.get().getUniversity_code());
            }
            return gson.toJson(result);
        }
        else {
            MemberEntity newMemberEntity = toMemberEntity(memberDTO);
            memberRepository.save(newMemberEntity);
            return gson.toJson(memberDTO);
        }
    }
    public String getMemberDetail(String uid) {
        Optional<MemberEntity> memberEntity = memberRepository.findByUid(uid);
        Gson gson = new GsonBuilder().serializeNulls().create();

        if (!memberEntity.get().getUid().isEmpty()) {
            MemberDTO result = new MemberDTO();
            Optional<UniversityEntity> universityEntity = universityRepository.findByNum(memberEntity.get().getUniversity_code());
            result.setUid(memberEntity.get().getUid());
            result.setEmail(memberEntity.get().getEmail());
            result.setDisplayName(memberEntity.get().getDisplayName());
            result.setPhotoUrl(memberEntity.get().getPhotoUrl());
            result.setNickname(memberEntity.get().getNickname());
            result.setMajor(memberEntity.get().getMajor());
            result.setInfotext(memberEntity.get().getInfotext());
            result.setSex(memberEntity.get().getSex());
            //학교 설정이 안되어있다면
            if(memberEntity.get().getUniversity_code() == null) {
                result.setUniversity(null);
                result.setUniversityCode(null);
            }
            else {
                result.setUniversity(universityEntity.get().getUniversity());
                result.setUniversityCode(memberEntity.get().getUniversity_code());
            }
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
        memberRatingRepository.deleteByUid(uid);
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