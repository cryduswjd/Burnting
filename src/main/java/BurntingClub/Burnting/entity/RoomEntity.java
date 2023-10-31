package BurntingClub.Burnting.entity;

import BurntingClub.Burnting.dto.RoomDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "room")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    @Column(unique = true)
    private String roomCode;
    @Column
    private String uid;
    @Column
    private Long status;

    public static RoomEntity toRoomEntity(RoomDTO roomDTO) {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setRoomCode(roomDTO.getRoomCode());
        roomEntity.setUid(roomDTO.getUid());
        roomEntity.setStatus(roomDTO.getStatus());
        return roomEntity;
    }
}
