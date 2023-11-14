package BurntingClub.Burnting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicDTO {

    private String message;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
