package BurntingClub.Burnting.entity;

import BurntingClub.Burnting.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table (name = "user")
public class MemberEntity {

    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long num;   //Long = BigInt

    @Column(unique = true)  //unique 제약조건 추가
    private String email;
    @Column
    private String pwd;
    @Column
    private String token;
//    @Column
//    private String nickname;
//    @Column
//    private String age;
//    @Column
//    private String university;
//    @Column
//    private String major;
//    @Column
//    private String infotext;

    public Long getNum() {
        return num;
    }
    public void setNum(Long num) {
        this.num = num;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setEmail(memberDTO.getEmail());
        memberEntity.setPwd(memberDTO.getPwd());
        memberEntity.setToken(memberDTO.getToken());
        return memberEntity;
    }
//
//    public String getNickname() {
//        return nickname;
//    }
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }
//
//    public String getAge() {
//        return age;
//    }
//    public void setAge(String age) {
//        this.age = age;
//    }
//
//    public String getUniversity() {
//        return university;
//    }
//    public void setUniversity(String university) {
//        this.university = university;
//    }
//
//    public String getMajor() {
//        return major;
//    }
//    public void setMajor(String major) {
//        this.major = major;
//    }
//
//    public String getInfotext() {
//        return infotext;
//    }
//    public void setInfotext(String infotext) {
//        this.infotext = infotext;
//    }
}