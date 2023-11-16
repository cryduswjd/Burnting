package BurntingClub.Burnting.dto.MemberDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImagesDTO {
    private String uid;
    private String imageUrl;

    public static ImagesDTO toImagesDTO(String uid, String images) {
        ImagesDTO imagesDTO = new ImagesDTO();
        imagesDTO.setUid(uid);
        imagesDTO.setImageUrl(images);
        return imagesDTO;
    }
}
