package BurntingClub.Burnting.dto.MatchedDTO;

import BurntingClub.Burnting.entity.MatchedEntity.PlaceVoteEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlaceVoteDTO {
    private String chatChannelCode;
    private String uid;
    private String placeUrl;

    public String getChatChannelCode() {
        return chatChannelCode;
    }

    public void setChatChannelCode(String chatChannelCode) {
        this.chatChannelCode = chatChannelCode;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPlaceUrl() {
        return placeUrl;
    }

    public void setPlaceUrl(String placeUrl) {
        this.placeUrl = placeUrl;
    }
}
