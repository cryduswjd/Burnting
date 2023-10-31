package BurntingClub.Burnting.entity;

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
    private String university;
    @Column
    private String major;
    @Column
    private String infotext;
    @Column
    private Long sex;
    @Column
    private String imgs;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setEmail(memberDTO.getEmail());
        memberEntity.setUid(memberDTO.getUid());
        memberEntity.setDisplayName(memberDTO.getDisplayName());
        memberEntity.setPhotoUrl(memberDTO.getPhotoUrl());
        memberEntity.setNickname(memberDTO.getNickname());
        memberEntity.setAge(memberDTO.getAge());
        memberEntity.setUniversity(memberDTO.getUniversity());
        memberEntity.setMajor(memberDTO.getMajor());
        memberEntity.setInfotext(memberDTO.getInfotext());
        memberEntity.setSex(memberDTO.getSex());
        memberEntity.setImgs(memberDTO.getImgs());
        return memberEntity;
    }
}