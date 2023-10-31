package BurntingClub.Burnting.controller;

import BurntingClub.Burnting.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
//    @PostMapping("/createRoomCode")
//    public String createRoomCode(@RequestParam String uid) {
//        return roomService.createRoomCode(uid);
//    }   //방 생성하기
//    @PostMapping("/enterRoom")
//    public String enterRoom(@RequestParam String uid, @RequestParam String roomCode) {
//        return roomService.enterRoom(uid, roomCode);
//    }   //방 참여하기
}
