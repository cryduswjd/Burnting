package BurntingClub.Burnting.controller;

import BurntingClub.Burnting.dto.BasicDTO;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BasicController {
    @GetMapping("/health/check")
    public String checkPoint() {
        String status = "UP";
        BasicDTO basicDTO = new BasicDTO();
        basicDTO.setStatus(status);
        Gson gson = new Gson();
        return gson.toJson(basicDTO);
    }
}
