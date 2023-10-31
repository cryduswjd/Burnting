package BurntingClub.Burnting.dto;

import BurntingClub.Burnting.entity.RoomEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomDTO {
    private String roomCode;
    private String uid;
    private Long status;

    public static RoomDTO toRoomDTO(RoomEntity roomEntity) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomCode(roomEntity.getRoomCode());
        roomDTO.setUid(roomEntity.getUid());
        roomDTO.setStatus(roomEntity.getStatus());
        return roomDTO;
    }
}
