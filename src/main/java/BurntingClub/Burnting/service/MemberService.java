package BurntingClub.Burnting.service;

import BurntingClub.Burnting.dto.MemberDTO.MemberDTO;
import BurntingClub.Burnting.entity.MemberEntity;
import BurntingClub.Burnting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static BurntingClub.Burnting.entity.MemberEntity.toMemberEntity;

@Service
@RequiredArgsConstructor
public class MemberService{
    public final MemberRepository memberRepository;

    public String insertMember(MemberDTO memberDTO) {
        MemberEntity memberEntity = toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
        return "User \"" + memberDTO.getUid() + "\" Insert OK";
    }
    public MemberDTO getMemberDetail(String uid) {
        Optional<MemberEntity> memberEntity = memberRepository.findByUid(uid);
        MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity.get());
        return memberDTO;
    }
    public String updateMember(MemberDTO memberDTO) {
        memberRepository.updateInfo(memberDTO.getNickname(), memberDTO.getAge(), memberDTO.getUniversity(), memberDTO.getMajor(), memberDTO.getInfotext(), memberDTO.getUid());
        return "User \"" + memberDTO.getUid() + "\" Update OK";
    }
    public String deleteMember(String uid) {
        memberRepository.deleteByUid(uid);
        return "User \"" + uid + "\" Delete OK";
    }
}