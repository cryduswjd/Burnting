package BurntingClub.Burnting.service;

import BurntingClub.Burnting.config.RandomCode;
import BurntingClub.Burnting.dto.BasicDTO;
import BurntingClub.Burnting.dto.MatchedDTO.*;
import BurntingClub.Burnting.dto.MemberDTO.MemberDTO;
import BurntingClub.Burnting.entity.MatchedEntity.ChatChannelEntity;
import BurntingClub.Burnting.entity.MatchedEntity.PlaceVoteEntity;
import BurntingClub.Burnting.entity.MemberEntity.MemberEntity;
import BurntingClub.Burnting.entity.TeamEntity;
import BurntingClub.Burnting.entity.UniversityEntity;
import BurntingClub.Burnting.repository.*;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class ChatChannelService {
    public final ChatChannelRepository chatChannelRepository;
    public final TeamRepository teamRepository;
    public final MemberRepository memberRepository;
    public final UniversityRepository universityRepository;
    public final RestTemplate restTemplate;
    public final PlaceVoteRepository placeVoteRepository;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;
    private String chatChannelCode = "";
    private final Lock lock = new ReentrantLock();
    @Scheduled(fixedDelay = 600000)
    public String statusReady(String team) {
        try {
            teamRepository.updateTeamStatusField("ready", team);
            Optional<TeamEntity> teamEntity = teamRepository.findByTeam(team);

            Firestore firestore = FirestoreClient.getFirestore();
            DocumentReference documentReference = firestore.collection("teams").document(team);
            documentReference.set(Map.of("status", "ready"), SetOptions.merge());
            documentReference.set(Map.of("university", teamEntity.get().getUniversity()), SetOptions.merge());

            Optional<TeamEntity> uidOfTeam = teamRepository.findByTeam(team);
            Long numberOfUid = Arrays.stream(uidOfTeam.get().getUid().split(", ")).count();

            ChannelMemberDTO channelMemberDTO = new ChannelMemberDTO();
            List<String> userInfo = new ArrayList<>();

            boolean matchSuccess = findAndMatchOtherTeam(team, numberOfUid);
            if (matchSuccess) {
                Optional<ChatChannelEntity> chatChannelEntity = chatChannelRepository.findByChannel(chatChannelCode);

                // 동기화
                synchronized (lock) {
                    channelMemberDTO.setChannel(chatChannelEntity.get().getChannel());
                    List<String> uids = List.of(chatChannelEntity.get().getUid().split(", "));

                    for (String uid : uids) {
                        Optional<MemberEntity> memberEntity = memberRepository.findByUid(uid);

                        MatchedMemberDTO matchedMemberDTO = new MatchedMemberDTO();
                        matchedMemberDTO.setUid(memberEntity.get().getUid());
                        matchedMemberDTO.setNickname(memberEntity.get().getNickname());
                        matchedMemberDTO.setPhotoUrl(memberEntity.get().getPhotoUrl());

                        JsonObject jsonMember = new JsonObject();
                        jsonMember.addProperty("uid", matchedMemberDTO.getUid());
                        jsonMember.addProperty("nickname", matchedMemberDTO.getNickname());
                        jsonMember.addProperty("photoUrl", matchedMemberDTO.getPhotoUrl());

                        userInfo.add(jsonMember.toString().replace("\\", ""));
                    }
                    channelMemberDTO.setUser(userInfo);
                    channelMemberDTO.setUinversity(teamEntity.get().getUniversity());
                }
            }

            synchronized (lock) {
                Gson gson = new Gson();
                return gson.toJson(channelMemberDTO);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "10분이 경과하였습니다.";
        }
    }
    private boolean findAndMatchOtherTeam(String team, Long numberOfUid) {
        String teamA = teamRepository.findByTeam(team).get().getUid();
        Long mainUniversity = teamRepository.findByTeam(team).get().getUniversity();

        // 같은 유저 수의 다른 팀 가져오기
        List<TeamEntity> readyTeam = teamRepository.findByStatus("ready");

        for (TeamEntity teamEntity : readyTeam) {
            Optional<TeamEntity> original = teamRepository.findByTeam(team);

            if(!Objects.equals(original.get().getTeam(), teamEntity.getTeam())) {   //매칭 신청한 팀 제외
                String readyTeamUid = teamEntity.getUid();
                String[] uids = readyTeamUid.split(", ");
                int OtherTeamNumberOfUids = uids.length;    //매칭될 팀 인원
                Long OtherTeamUniversity = teamEntity.getUniversity();

                //팀 인원/학교가 맞고 성별이 다른 경우 team table status -> matched / chatChannel insert
                if(OtherTeamNumberOfUids == numberOfUid && Objects.equals(mainUniversity, OtherTeamUniversity) && !Objects.equals(teamEntity.getGender(), original.get().getGender())) {

                    RandomCode randomCode = new RandomCode();
                    chatChannelCode = randomCode.generateRandomStrAndAssert();

                    List<String> userIds = new ArrayList<>();
                    Map<String, Object> userRoles = new HashMap<>();

                    String teamB = teamRepository.findByTeam(teamEntity.getTeam()).get().getUid();

                    userIds.addAll(Arrays.asList(teamA.split(", ")));
                    userIds.addAll(Arrays.asList(teamB.split(", ")));

                    for(String userId: userIds) {
                        userRoles.put(userId, null);
                    }

                    Firestore firestore = FirestoreClient.getFirestore();
                    RoomGroupDTO roomGroupDTO = new RoomGroupDTO(null, null, chatChannelCode, "group", userIds, userRoles, "running", mainUniversity);
                    DocumentReference documentReference = firestore.collection("rooms").document(chatChannelCode);
                    documentReference.set(roomGroupDTO);

                    //team table status -> matched
                    teamRepository.updateTeamStatusField("matched", team);
                    teamRepository.updateTeamStatusField("matched", teamEntity.getTeam());

                    DocumentReference docRefA = firestore.collection("teams").document(team);
                    DocumentReference docRefB = firestore.collection("teams").document(teamEntity.getTeam());

                    Map<String, Object> updates = new HashMap<>();
                    updates.put("roomId", chatChannelCode);
                    updates.put("status", "matched");

                    docRefA.update(updates);
                    docRefB.update(updates);

                    //chatChannel insert
                    ChatChannelDTO chatChannelDTO;
                    while (true) {
                        Optional<ChatChannelEntity> checkChannel = chatChannelRepository.findByChannel(chatChannelCode);
                        if (!checkChannel.isPresent()) {
                            chatChannelDTO = new ChatChannelDTO(chatChannelCode, teamA + ", " + teamB, "running", mainUniversity);
                            ChatChannelEntity chatChannelEntity = ChatChannelEntity.toChatChannelEntity(chatChannelDTO);
                            chatChannelRepository.save(chatChannelEntity);
                            break;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
    public String statusDeath(String chatChannelCode) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();

        //파이어베이스&DB chatChannelCode를 갖고 있는 team의 status -> death
        CollectionReference teamsCollection = firestore.collection("teams");
        Query query = teamsCollection.whereEqualTo("roomId", chatChannelCode);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            DocumentReference documentReference = firestore.collection("teams").document(document.getId());
            Map<String, Object> updates = new HashMap<>();
            updates.put("status", "death");
            ApiFuture<WriteResult> writeResultA = documentReference.update(updates);
            teamRepository.updateTeamStatusField("death", document.getId());
        }

        //파이어베이스&DB rooms status -> death
        DocumentReference documentReference = firestore.collection("rooms").document(chatChannelCode);
        Map<String, Object> updates = new HashMap<>();
        updates.put("status", "death");
        documentReference.update(updates);
        chatChannelRepository.updateChannelStatusField("death", chatChannelCode);

        BasicDTO responseDTO = new BasicDTO();
        responseDTO.setMessage(chatChannelCode + "status Death");
        Gson gson = new Gson();
        return gson.toJson(responseDTO);
    }
    public String foodList(Long universityCode) {
        Optional<UniversityEntity> universityEntity = universityRepository.findByNum(universityCode);
        String lng = universityEntity.get().getLng();
        String lat = universityEntity.get().getLat();

        String apiUrl = "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=FD6&x=" + lng + "&y=" + lat + "&radius=500";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        return responseEntity.getBody();
    }
    public String cafeList(Long universityCode) {
        Optional<UniversityEntity> universityEntity = universityRepository.findByNum(universityCode);
        String lng = universityEntity.get().getLng();
        String lat = universityEntity.get().getLat();

        String apiUrl = "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=CE7&x=" + lng + "&y=" + lat + "&radius=500";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        return responseEntity.getBody();
    }

    public String placeVote(String chatChannelCode, String uid, String placeUrl) {
        Optional<ChatChannelEntity> chatChannelEntity = chatChannelRepository.findByChannel(chatChannelCode);

        if("running".equals(chatChannelEntity.get().getStatus())) {
            PlaceVoteDTO placeVoteDTO = new PlaceVoteDTO(chatChannelCode, uid, placeUrl);
            PlaceVoteEntity placeVoteEntity = PlaceVoteEntity.toPlaceVoteEntity(placeVoteDTO);
            placeVoteRepository.save(placeVoteEntity);

            Long totalNumber = Arrays.stream(chatChannelRepository.findByChannel(chatChannelCode).get().getUid().split(", ")).count();
            Long nowNumber = placeVoteRepository.countByChannel(chatChannelCode);

            //전체 투표가 진행중일 때
            if(!nowNumber.equals(totalNumber)) {
                VotingDTO votingDTO = new VotingDTO();
                votingDTO.setChatChannelCode(chatChannelCode);
                votingDTO.setUid(uid);
                votingDTO.setPlaceUrl(placeUrl);
                votingDTO.setTotalNumber(totalNumber);
                votingDTO.setNowNumber(nowNumber);
                Gson gson = new Gson();
                return gson.toJson(votingDTO);
            }
            //전체 투표가 마감됬을 때
            else {
                List<PlaceVoteEntity> result = placeVoteRepository.findByChannel(chatChannelCode);
                Map<String, Long> dataTotal = new HashMap<>();

                for (PlaceVoteEntity index : result) {
                    String placeUrlVote = index.getPlace_url();
                    dataTotal.merge(placeUrlVote, 1L, Long::sum);
                }

                Optional<Map.Entry<String, Long>> maxEntry = dataTotal.entrySet().stream().max(Map.Entry.comparingByValue());

                if (maxEntry.isPresent()) {
                    long maxVotes = maxEntry.get().getValue();
                    boolean hasMultipleMaxEntries = dataTotal.values().stream().filter(value -> value == maxVotes).count() > 1;

                    if (hasMultipleMaxEntries) {
                        BasicDTO basicDTO = new BasicDTO();
                        basicDTO.setMessage("다시 투표해주세요. 동점이 발생했습니다.");
                        Gson gson = new Gson();
                        return gson.toJson(basicDTO);
                    }
                    else {
                        VotingDTO votingDTO = new VotingDTO();
                        votingDTO.setChatChannelCode(chatChannelCode);
                        votingDTO.setPlaceUrl(maxEntry.get().getKey());
                        votingDTO.setNowNumber(maxEntry.get().getValue()+1);
                        Gson gson = new Gson();
                        return gson.toJson(votingDTO);
                    }
                }
                else {
                    BasicDTO basicDTO = new BasicDTO();
                    basicDTO.setMessage("투표 결과가 없습니다.");
                    Gson gson = new Gson();
                    return gson.toJson(basicDTO);
                }
            }
        }
        else {
            BasicDTO basicDTO = new BasicDTO();
            basicDTO.setMessage("활성화된 채팅방이 아닙니다.");
            Gson gson = new Gson();
            return gson.toJson(basicDTO);
        }
    }
}