package BurntingClub.Burnting.service;

import BurntingClub.Burnting.dto.MemberDTO;
import BurntingClub.Burnting.entity.MemberEntity;
import BurntingClub.Burnting.repository.MemberRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static BurntingClub.Burnting.entity.MemberEntity.toMemberEntity;

@Service
@RequiredArgsConstructor
public class MemberService{
    public static final String COLLECTION_NAME = "users";
    public final MemberRepository memberRepository;

    public String insertMember(MemberDTO memberDTO) throws Exception{
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(memberDTO.getEmail()).set(memberDTO);

        MemberEntity memberEntity = toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);

        return "User Insert OK";
    }

    public MemberDTO getMemberDetail(String email) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(email);
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = apiFuture.get();
        MemberDTO memberDTO = null;
        if(documentSnapshot.exists()){
            memberDTO = documentSnapshot.toObject(MemberDTO.class);
            return memberDTO;
        }
        else{
            return null;
        }
    }
    public String updateMember(MemberDTO memberDTO) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<com.google.cloud.firestore.WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(memberDTO.getEmail()).set(memberDTO);

        return "User Update OK";
    }
    public String deleteMember(String email) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(email).delete();
        return "Document email: "+email+" delete";
    }

//    public void save(MemberDTO memberDTO) {
//        //1. dto -> entity 변환
//        //2. repository의 save메서드 호출
//        MemberEntity memberEntity = toMemberEntity(memberDTO);
//        memberRepository.save(memberEntity);    //save는 jpa가 제공해주는 메서드
//        //repository의 save메서드 호출 (조건. entity객체를 넘겨주어야 함)
//    }
//    public MemberDTO login(MemberDTO memberDTO) {
//        Optional<MemberEntity> byEmail = memberRepository.findByEmail(memberDTO.getEmail());
//        if(byEmail.isPresent()) {
//            MemberEntity memberEntity = byEmail.get();
//
//            if(memberEntity.getPwd().equals(memberDTO.getPwd())) {
//                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
//                return dto;
//            }
//            else {
//                return null;
//            }
//        }
//        else {
//            return null;
//        }
//    }
//    public void detail(MemberDTO memberDTO) {
//        memberRepository.updateInfo(memberDTO.getNickname(), memberDTO.getAge(), memberDTO.getUniversity(), memberDTO.getMajor(), memberDTO.getInfotext(), memberDTO.getEmail());
//    }
//    public MemberDTO account(MemberDTO memberDTO) {
//        Optional<MemberEntity> memberEntity = memberRepository.findAll(memberDTO);
//        if(memberEntity.isPresent()) {
//            return MemberDTO.toMemberDTO(memberEntity);
//        }
//        else {
//            return null;
//        }
//    }
//    public List<MemberDTO> findAll() {
//        List<MemberEntity> memberEntityList = memberRepository.findAll();
//        List<MemberDTO> memberDtoList = new ArrayList<>();
//
//        for(MemberEntity memberEntity: memberEntityList) {
//            memberDtoList.add(MemberDTO.toMemberDTO(memberEntity));
//        }
//        return memberDtoList;
//    }
}