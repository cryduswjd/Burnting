package BurntingClub.Burnting.controller;

import BurntingClub.Burnting.dto.MemberDTO.MemberDTO;
import BurntingClub.Burnting.dto.MemberDTO.TokenDTO;
import BurntingClub.Burnting.service.MemberService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {
    @PostMapping("/create-token")
    public TokenDTO createToken(@RequestParam String uid) throws FirebaseAuthException {
        String token = FirebaseAuth.getInstance().createCustomToken(uid);
        TokenDTO tokenDTO = new TokenDTO(token);
        return tokenDTO;
    }

    private final MemberService memberService;
    @PostMapping("/insertMember")
    public String insertMember(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        MemberDTO memberDTO = new MemberDTO();
        try {
            val accessToken = authorization.split(" ")[1];
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(accessToken);

            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();

            UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);

            String displayName = userRecord.getDisplayName();
            String photoUrl = userRecord.getPhotoUrl();

            memberDTO.setUid(uid);
            memberDTO.setEmail(email);
            memberDTO.setDisplayName(displayName);
            memberDTO.setPhotoUrl(photoUrl);

        } catch (IllegalArgumentException | FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
        }
        return memberService.insertMember(memberDTO);
    }
    @GetMapping("/getMemberDetail")
    public MemberDTO getMemberDetail(@RequestParam String uid) {
        return memberService.getMemberDetail(uid);
    }
    @PostMapping("/updateMember")
    public String updateMember(@RequestBody MemberDTO memberDTO) {
        return memberService.updateMember(memberDTO);
    }
    @PostMapping("/deleteMember")
    public String deleteMember(@RequestParam String uid) {
        return memberService.deleteMember(uid);
    }
    @PostMapping("/insertFeedImgs")
    public String insertFeedImgs(@RequestParam String uid, @RequestBody Map<String, Object> images) {
        List<String> imageUrl = (List<String>) images.get("images");
        return memberService.insertFeedImgs(uid, imageUrl);
    }
    @PostMapping("/deleteFeedImgs")
    public String deleteFeedImgs(@RequestParam String uid, @RequestBody Map<String, Object> images) {
        List<String> imageUrl = (List<String>) images.get("images");
        return memberService.deleteFeedImgs(uid, imageUrl);
    }
    @GetMapping("/getFeedImgs")
    public String getFeedImgs(@RequestParam String uid) {
        return memberService.getFeedImgs(uid);
    }
}