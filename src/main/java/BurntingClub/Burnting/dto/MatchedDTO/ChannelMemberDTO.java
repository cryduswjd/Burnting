package BurntingClub.Burnting.dto.MatchedDTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChannelMemberDTO {
    private String channel;
    private List<String> user;

    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public List<String> getUser() {
        return user;
    }
    public void setUser(List<String> user) {
        this.user = user;
    }
}
