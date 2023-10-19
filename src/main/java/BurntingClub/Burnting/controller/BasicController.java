package BurntingClub.Burnting.controller;

import BurntingClub.Burnting.dto.BasicDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BasicController {

//    @GetMapping("/health/check")
//    public BasicDTO checkPoint() {
//        return new BasicDTO("UP");
//    }
    @GetMapping("/health/check")
    public ResponseEntity<BasicDTO> checkPoint() {
        String status = "UP";
        BasicDTO basicDTO = new BasicDTO(status);
        return new ResponseEntity<>(basicDTO, HttpStatus.OK);
    }
}
