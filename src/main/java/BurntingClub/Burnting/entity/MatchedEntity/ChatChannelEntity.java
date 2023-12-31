package BurntingClub.Burnting.entity.MatchedEntity;

import BurntingClub.Burnting.dto.MatchedDTO.ChatChannelDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "ChatChannel")
public class ChatChannelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    @Column(unique = true)
    private String channel;
    @Column
    private String uid;
    @Column
    private String status;
    @Column
    private Long university;

    public static ChatChannelEntity toChatChannelEntity(ChatChannelDTO chatChannelDTO) {
        ChatChannelEntity chatChannelEntity = new ChatChannelEntity();
        chatChannelEntity.setChannel(chatChannelDTO.getChannel());
        chatChannelEntity.setUid(chatChannelDTO.getUid());
        chatChannelEntity.setStatus(chatChannelDTO.getStatus());
        chatChannelEntity.setUniversity(chatChannelDTO.getUniversity());
        return chatChannelEntity;
    }
}
