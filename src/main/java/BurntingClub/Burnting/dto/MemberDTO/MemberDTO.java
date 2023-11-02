package BurntingClub.Burnting.dto.MemberDTO;

import BurntingClub.Burnting.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private String uid;
    private String email;
    private String displayName;
    private String photoUrl;
    private String nickname;
    private String age;
    private String university;
    private String major;
    private String infotext;
    private Long sex;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUid(memberEntity.getUid());
        memberDTO.setEmail(memberEntity.getEmail());
        memberDTO.setDisplayName(memberEntity.getDisplayName());
        memberDTO.setPhotoUrl(memberEntity.getPhotoUrl());
        memberDTO.setNickname(memberEntity.getNickname());
        memberDTO.setAge(memberEntity.getAge());
        memberDTO.setUniversity(memberEntity.getUniversity());
        memberDTO.setMajor(memberEntity.getMajor());
        memberDTO.setInfotext(memberEntity.getInfotext());
        memberDTO.setSex(memberEntity.getSex());
        return memberDTO;
    }
}
