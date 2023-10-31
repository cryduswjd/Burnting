package BurntingClub.Burnting.service;

import BurntingClub.Burnting.dto.PlaceDTO.CafeDTO;
import BurntingClub.Burnting.dto.PlaceDTO.FoodDTO;
import BurntingClub.Burnting.entity.PlaceEntity.CafeEntity;
import BurntingClub.Burnting.entity.PlaceEntity.FoodEntity;
import BurntingClub.Burnting.repository.PlaceRepository.CafeRepository;
import BurntingClub.Burnting.repository.PlaceRepository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    public final FoodRepository foodRepository;
    public final CafeRepository cafeRepository;
    public String updateList() {

        return "List Update Successfully";
    }
    public List<CafeDTO> getCafeList() {
        List<CafeEntity> cafeList = cafeRepository.findAll();
        List<CafeDTO> cafeDTO = null;
        for (int i=0; i<cafeList.size(); i++) {
            cafeDTO.set(i, CafeDTO.toCafeDTO(cafeList.get(i)));
        }
        return cafeDTO;
    }
    public List<FoodDTO> getFoodList() {
        List<FoodEntity> foodList = foodRepository.findAll();
        List<FoodDTO> foodDTO = null;
        for (int i=0; i<foodList.size(); i++) {
            foodDTO.set(i, FoodDTO.toFoodDTO(foodList.get(i)));
        }
        return foodDTO;
    }
}
