package BurntingClub.Burnting.controller;

import BurntingClub.Burnting.dto.PlaceDTO.CafeDTO;
import BurntingClub.Burnting.dto.PlaceDTO.FoodDTO;
import BurntingClub.Burnting.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;
    @PostMapping("/update-list")
    public String updateList() {
        return placeService.updateList();
    }
    @GetMapping("https://dapi.kakao.com/v2/local/search/category.json?category_group_code=CE7&x=127.07446431849186&y=36.73644969810963&radius=500")
    public List<CafeDTO> cafeList(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

        return placeService.getCafeList();
    }
    @GetMapping("https://dapi.kakao.com/v2/local/search/category.json?category_group_code=FD6&x=127.07446431849186&y=36.73644969810963&radius=1000")
    public List<FoodDTO> foodList() {
        return placeService.getFoodList();
    }
}
