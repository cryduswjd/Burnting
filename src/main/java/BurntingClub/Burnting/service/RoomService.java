package BurntingClub.Burnting.service;

import BurntingClub.Burnting.config.RandomCode;
import BurntingClub.Burnting.dto.RoomDTO;
import BurntingClub.Burnting.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    public final RoomRepository roomRepository;
//    public String createRoomCode(String uid) {
//        String roomCode = RandomCode.randomCode.toString();
//        return "User \"" + uid + "\" Create Room Code(\"" + roomCode + ")\" Successfully";
//    }
//    public String enterRoom(String uid, String roomCode) {
//
//    }
}
