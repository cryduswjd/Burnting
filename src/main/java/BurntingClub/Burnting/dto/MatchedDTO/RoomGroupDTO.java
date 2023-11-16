package BurntingClub.Burnting.dto.MatchedDTO;

import lombok.*;

import java.security.Timestamp;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomGroupDTO {
    private String imageUrl;
    private Map<String, String> metadata;
    private String channelCode;
    private String type;
    private List<String> userIds;
    private Map<String, Object> userRoles;
    private String status;
    private Long university;

    public Long getUniversity() {
        return university;
    }

    public void setUniversity(Long university) {
        this.university = university;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public void setUserRoles(Map<String, Object> userRoles) {
        this.userRoles = userRoles;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public String getType() {
        return type;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public Map<String, Object> getUserRoles() {
        return userRoles;
    }
}
