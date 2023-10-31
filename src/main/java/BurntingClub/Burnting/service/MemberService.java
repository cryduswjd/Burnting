package BurntingClub.Burnting.service;

import BurntingClub.Burnting.dto.MemberDTO.ImagesDTO;
import BurntingClub.Burnting.dto.MemberDTO.MemberDTO;
import BurntingClub.Burnting.entity.ImagesEntity;
import BurntingClub.Burnting.entity.MemberEntity;
import BurntingClub.Burnting.repository.ImagesRepository;
import BurntingClub.Burnting.repository.MemberRatingRepository;
import BurntingClub.Burnting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static BurntingClub.Burnting.entity.MemberEntity.toMemberEntity;

@Service
@RequiredArgsConstructor
public class MemberService{
    public final MemberRepository memberRepository;
    public final MemberRatingRepository memberRatingRepository;
    public final ImagesRepository imagesRepository;
    public String insertMember(MemberDTO memberDTO) {
        Optional<MemberEntity> memberEntity = memberRepository.findByUid(memberDTO.getUid());
        if (memberEntity.isPresent()) {
            memberDTO = MemberDTO.toMemberDTO(memberEntity.get());
            return memberDTO.toString();
        } else {
            MemberEntity newMemberEntity = toMemberEntity(memberDTO);
            memberRepository.save(newMemberEntity);
            return "User \"" + memberDTO.getUid() + "\" Insert Successfully";
        }
    }
    public MemberDTO getMemberDetail(String uid) {
        Optional<MemberEntity> memberEntity = memberRepository.findByUid(uid);
        MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity.get());
        return memberDTO;
    }
    public String updateMember(MemberDTO memberDTO) {
        memberRepository.updateInfo(memberDTO.getNickname(), memberDTO.getPhotoUrl(), memberDTO.getAge(), memberDTO.getUniversity(), memberDTO.getMajor(), memberDTO.getInfotext(), memberDTO.getSex(), memberDTO.getUid());
        return "User \"" + memberDTO.getUid() + "\" Update Successfully";
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
    public List<String> getFeedImgs(String uid) {
        List<ImagesEntity> imagesEntities = imagesRepository.findByUid(uid);
        List<String> imageUrls = imagesEntities.stream()
                .map(ImagesEntity::getImageUrl)
                .collect(Collectors.toList());
        return imageUrls;
    }
}