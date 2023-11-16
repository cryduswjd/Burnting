package BurntingClub.Burnting.dto.MatchedDTO;

import BurntingClub.Burnting.entity.MatchedEntity.ChatChannelEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatChannelDTO {
    private String channel;
    private String uid;
    private String status;
    private Long university;

    public static ChatChannelDTO toChatChannelDTO(ChatChannelEntity chatChannelEntity) {
        ChatChannelDTO chatChannelDTO = new ChatChannelDTO();
        chatChannelDTO.setChannel(chatChannelEntity.getChannel());
        chatChannelDTO.setUid(chatChannelEntity.getUid());
        chatChannelDTO.setStatus(chatChannelEntity.getStatus());
        chatChannelDTO.setUniversity(chatChannelEntity.getUniversity());
        return chatChannelDTO;
    }
}
