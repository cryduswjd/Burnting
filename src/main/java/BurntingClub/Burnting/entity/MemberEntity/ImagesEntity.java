package BurntingClub.Burnting.entity.MemberEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "userImage")
public class ImagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    @Column
    private String uid;
    @Column
    private String imageUrl;

    public static ImagesEntity toImagesEntity(String uid, String images) {
        ImagesEntity imagesEntity = new ImagesEntity();
        imagesEntity.setUid(uid);
        imagesEntity.setImageUrl(images);
        return imagesEntity;
    }
}
