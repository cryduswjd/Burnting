package BurntingClub.Burnting.dto;

import BurntingClub.Burnting.entity.ChatChannelEntity;
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

    public static ChatChannelDTO toChatChannelDTO(ChatChannelEntity chatChannelEntity) {
        ChatChannelDTO chatChannelDTO = new ChatChannelDTO();
        chatChannelDTO.setChannel(chatChannelEntity.getChannel());
        chatChannelDTO.setUid(chatChannelEntity.getUid());
        chatChannelDTO.setStatus(chatChannelEntity.getStatus());
        return chatChannelDTO;
    }
}
