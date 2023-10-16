package BurntingClub.Burnting.controller;

import BurntingClub.Burnting.dto.MemberDTO;
import BurntingClub.Burnting.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/insertMember")
    public String insertMember(@RequestBody MemberDTO memberDTO) throws Exception{
        System.out.println("memberDTO = " + memberDTO);
        return memberService.insertMember(memberDTO);
    }

    @GetMapping("/getMemberDetail")
    public MemberDTO getMemberDetail(@RequestParam String email) throws Exception{
        return memberService.getMemberDetail(email);
    }

    @PostMapping("/updateMember")
    public String updateMember(@RequestBody MemberDTO memberDTO) throws Exception{
        return memberService.updateMember(memberDTO);
    }

    @GetMapping("/deleteMember")
    public String deleteMember(@RequestParam String email) throws Exception{
        return memberService.deleteMember(email);
    }

//    //회원가입(이메일, 비밀번호)
//    @PostMapping("/save")
//    public MemberDTO save(@RequestBody MemberDTO memberDTO) {
//        //약어 soutp
//        System.out.println("memberDTO = " + memberDTO);
//        memberService.save(memberDTO);  //jpa에서 제공하는 자동 inset 쿼리문
//        return memberDTO;   //response json type
//    }
//    //로그인
//    @PostMapping("/login")
//    public MemberDTO login(@RequestBody MemberDTO memberDTO, HttpSession session) {
//        MemberDTO loginResult = memberService.login(memberDTO);
//        if(loginResult != null) {
//            session.setAttribute("loginEmail", loginResult.getEmail());
//            return memberDTO;
//        }
//        else {
//            return memberDTO;
//        }
//    }
//    //회원 정보 수정
//    @PostMapping("/detail")
//    public MemberDTO detail(@RequestBody MemberDTO memberDTO) {
//        memberService.detail(memberDTO);
//        return memberDTO;
//    }
//    //회원 정보 가져오기
//    @PostMapping("/userInfo")
//    public MemberDTO userInfo(@RequestBody MemberDTO memberDTO, Model model) {
//        MemberDTO result = memberService.account(memberDTO);
//        model.addAttribute("member", result);
//        return result;
//    }
//    //회원 정보 전부 가져오기
//    @PostMapping("/userInfoAll")
//    public List<MemberDTO> findAll(Model model) {
//        List<MemberDTO> memberDTOList = memberService.findAll();
//        model.addAttribute("memberList", memberDTOList);
//        return memberDTOList;
//    }
}