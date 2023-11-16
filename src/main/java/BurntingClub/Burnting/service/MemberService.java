package BurntingClub.Burnting.service;

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
            memberDTO = MemberDTO.toMemberDTO(memberEntity.get());
            Gson gson = new Gson();
            return gson.toJson(memberDTO);
        } else {
            MemberEntity newMemberEntity = toMemberEntity(memberDTO);
            memberRepository.save(newMemberEntity);
            Gson gson = new Gson();
            return gson.toJson(memberDTO);
        }
    }
    public String getMemberDetail(String uid) {
        Optional<MemberEntity> memberEntity = memberRepository.findByUid(uid);

        MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity.get());
        Long universityCode = memberEntity.get().getUniversity_code();

        Optional<UniversityEntity> universityEntity = universityRepository.findByNum(universityCode);
        memberDTO.setUniversity(universityEntity.get().getUniversity());

        Gson gson = new Gson();
        return gson.toJson(memberDTO);
    }
    public String updateMember(MemberEntity memberEntity) {
        memberRepository.updateInfo(memberEntity.getNickname(), memberEntity.getPhotoUrl(), memberEntity.getAge(), memberEntity.getUniversity_code(), memberEntity.getMajor(), memberEntity.getInfotext(), memberEntity.getSex(), memberEntity.getUid());
        Gson gson = new Gson();
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

    public String universityList() {
        Iterable<UniversityListDTO> universityEntitie = universityRepository.findByNumAndUniversity();
        Gson gson = new Gson();
        return gson.toJson(universityEntitie);
    }
}