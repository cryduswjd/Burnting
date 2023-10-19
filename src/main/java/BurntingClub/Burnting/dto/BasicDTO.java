package BurntingClub.Burnting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicDTO {

    private String status;

    public BasicDTO(String status) {
        this.status = status;
    }
}
