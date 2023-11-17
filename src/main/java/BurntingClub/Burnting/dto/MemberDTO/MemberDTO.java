package BurntingClub.Burnting.dto.MemberDTO;

import BurntingClub.Burnting.entity.MemberEntity.MemberEntity;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getInfotext() {
        return infotext;
    }

    public void setInfotext(String infotext) {
        this.infotext = infotext;
    }

    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUid(memberEntity.getUid());
        memberDTO.setEmail(memberEntity.getEmail());
        memberDTO.setDisplayName(memberEntity.getDisplayName());
        memberDTO.setPhotoUrl(memberEntity.getPhotoUrl());
        memberDTO.setNickname(memberEntity.getNickname());
        memberDTO.setAge(memberEntity.getAge());
        memberDTO.setMajor(memberEntity.getMajor());
        memberDTO.setInfotext(memberEntity.getInfotext());
        memberDTO.setSex(memberEntity.getSex());
        return memberDTO;
    }
}
