package BurntingClub.Burnting.dto;

import BurntingClub.Burnting.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private String email;
    private String pwd;
    private String nickname;
    private String age;
    private String university;
    private String major;
    private String infotext;
    private String token;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setEmail(memberEntity.getEmail());
        memberDTO.setPwd(memberEntity.getPwd());
//        memberDTO.setNickname(memberDTO.getNickname());
//        memberDTO.setAge(memberDTO.getAge());
//        memberDTO.setUniversity(memberDTO.getUniversity());
//        memberDTO.setMajor(memberDTO.getMajor());
//        memberDTO.setInfotext(memberDTO.getInfotext());
        memberDTO.setToken(memberEntity.getToken());
        return memberDTO;
    }
}
