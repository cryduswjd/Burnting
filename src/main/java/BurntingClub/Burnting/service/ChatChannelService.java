package BurntingClub.Burnting.service;

import BurntingClub.Burnting.config.RandomCode;
import BurntingClub.Burnting.dto.ChatChannelDTO;
import BurntingClub.Burnting.dto.MatchedDTO.ChannelMemberDTO;
import BurntingClub.Burnting.dto.MatchedDTO.MatchedMemberDTO;
import BurntingClub.Burnting.dto.MemberDTO.MemberDTO;
import BurntingClub.Burnting.dto.TeamDTO;
import BurntingClub.Burnting.entity.ChatChannelEntity;
import BurntingClub.Burnting.entity.MemberEntity;
import BurntingClub.Burnting.entity.TeamEntity;
import BurntingClub.Burnting.repository.ChatChannelRepository;
import BurntingClub.Burnting.repository.MemberRepository;
import BurntingClub.Burnting.repository.TeamRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
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
                    String teamB = teamRepository.findByTeam(teamEntity.getTeam()).get().getUid();

                    //team table status -> matched
                    teamRepository.updateTeamStatusField("matched", team);
                    teamRepository.updateTeamStatusField("matched", teamEntity.getTeam());

                    Firestore firestore = FirestoreClient.getFirestore();
                    DocumentReference docRefA = firestore.collection("teams").document(team);
                    DocumentReference docRefB = firestore.collection("teams").document(teamEntity.getTeam());

                    Map<String, Object> updates = new HashMap<>();
                    updates.put("status", "matched");

                    ApiFuture<WriteResult> writeResultA = docRefA.update(updates);
                    ApiFuture<WriteResult> writeResultB = docRefB.update(updates);

                    //chatChannel insert
                    RandomCode randomCode = new RandomCode();
                    ChatChannelDTO chatChannelDTO;
                    while (true) {
                        chatChannelCode = randomCode.generateRandomStrAndAssert();
                        Optional<ChatChannelEntity> checkChannel = chatChannelRepository.findByChannel(chatChannelCode);
                        if (!checkChannel.isPresent()) {
                            chatChannelDTO = new ChatChannelDTO(chatChannelCode, teamA + ", " + teamB);
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
}
