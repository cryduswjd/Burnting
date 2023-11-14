package BurntingClub.Burnting.service;

import BurntingClub.Burnting.config.RandomCode;
import BurntingClub.Burnting.dto.BasicDTO;
import BurntingClub.Burnting.dto.ChatChannelDTO;
import BurntingClub.Burnting.dto.MatchedDTO.ChannelMemberDTO;
import BurntingClub.Burnting.dto.MatchedDTO.MatchedMemberDTO;
import BurntingClub.Burnting.dto.MatchedDTO.RoomGroupDTO;
import BurntingClub.Burnting.dto.TeamDTO;
import BurntingClub.Burnting.entity.ChatChannelEntity;
import BurntingClub.Burnting.entity.MemberEntity;
import BurntingClub.Burnting.entity.TeamEntity;
import BurntingClub.Burnting.repository.ChatChannelRepository;
import BurntingClub.Burnting.repository.MemberRepository;
import BurntingClub.Burnting.repository.TeamRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
    private String chatChannelCode = "";
    private final Lock lock = new ReentrantLock();
    @Scheduled(fixedDelay = 600000)
    public String statusReady(String team) {
        try {
            teamRepository.updateTeamStatusField("ready", team);

            Firestore firestore = FirestoreClient.getFirestore();
            DocumentReference documentReference = firestore.collection("teams").document(team);
            documentReference.set(Map.of("status", "ready"), SetOptions.merge());

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
        // 같은 유저 수의 다른 팀 가져오기
        List<TeamEntity> readyTeam = teamRepository.findByStatus("ready");

        for (TeamEntity teamEntity : readyTeam) {
            Optional<TeamEntity> original = teamRepository.findByTeam(team);

            if(!Objects.equals(original.get().getTeam(), teamEntity.getTeam())) {   //매칭 신청한 팀 제외
                String readyTeamUid = teamEntity.getUid();
                String[] uids = readyTeamUid.split(", ");
                int OtherTeamNumberOfUids = uids.length;

                //팀 인원이 맞고 성별이 다른 경우 team table status -> matched / chatChannel insert
                if(OtherTeamNumberOfUids == numberOfUid && !Objects.equals(teamEntity.getGender(), original.get().getGender())) {

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
                    RoomGroupDTO roomGroupDTO = new RoomGroupDTO(null, null, chatChannelCode, "group", userIds, userRoles, "running");
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

                    ApiFuture<WriteResult> writeResultA = docRefA.update(updates);
                    ApiFuture<WriteResult> writeResultB = docRefB.update(updates);

                    //chatChannel insert
                    ChatChannelDTO chatChannelDTO;
                    while (true) {
                        Optional<ChatChannelEntity> checkChannel = chatChannelRepository.findByChannel(chatChannelCode);
                        if (!checkChannel.isPresent()) {
                            chatChannelDTO = new ChatChannelDTO(chatChannelCode, teamA + ", " + teamB, "runnig");
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
    private String handleMatchingFailure(String team) {
        return team + " is match Failed";
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
        ApiFuture<WriteResult> writeResultA = documentReference.update(updates);
        chatChannelRepository.updateChannelStatusField("death", chatChannelCode);

        BasicDTO responseDTO = new BasicDTO();
        responseDTO.setMessage(chatChannelCode + "status Death");
        Gson gson = new Gson();
        return gson.toJson(responseDTO);
    }
}
