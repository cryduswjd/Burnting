package BurntingClub.Burnting.entity.MemberEntity;

import BurntingClub.Burnting.dto.MemberDTO.MemberDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table (name = "user")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long num;
    @Column(unique = true)
    private String uid;
    @Column
    private String email;
    @Column
    private String displayName;
    @Column
    private String photoUrl;
    @Column
    private String nickname;
    @Column
    private String age;
    @Column
    private Long university_code;
    @Column
    private String major;
    @Column
    private String infotext;
    @Column
    private Long sex;

    public Long getUniversity_code() {
        return university_code;
    }

    public void setUniversity_code(Long university_code) {
        this.university_code = university_code;
    }

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setEmail(memberDTO.getEmail());
        memberEntity.setUid(memberDTO.getUid());
        memberEntity.setDisplayName(memberDTO.getDisplayName());
        memberEntity.setPhotoUrl(memberDTO.getPhotoUrl());
        memberEntity.setNickname(memberDTO.getNickname());
        memberEntity.setAge(memberDTO.getAge());
        memberEntity.setMajor(memberDTO.getMajor());
        memberEntity.setInfotext(memberDTO.getInfotext());
        memberEntity.setSex(memberDTO.getSex());
        return memberEntity;
    }
}