package BurntingClub.Burnting.dto.MatchedDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VotingDTO {
    private String chatChannelCode;
    private String uid;
    private String placeUrl;
    private Long totalNumber;
    private Long nowNumber;

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

    public Long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Long totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Long getNowNumber() {
        return nowNumber;
    }

    public void setNowNumber(Long nowNumber) {
        this.nowNumber = nowNumber;
    }
}
