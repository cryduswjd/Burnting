package BurntingClub.Burnting.entity.MatchedEntity;

import BurntingClub.Burnting.dto.MatchedDTO.PlaceVoteDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "place_vote")
public class PlaceVoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    @Column
    private String channel;
    @Column
    private String uid;
    @Column
    private String place_url;

    public static PlaceVoteEntity toPlaceVoteEntity(PlaceVoteDTO placeVoteDTO) {
        PlaceVoteEntity placeVoteEntity = new PlaceVoteEntity();
        placeVoteEntity.setChannel(placeVoteDTO.getChatChannelCode());
        placeVoteEntity.setUid(placeVoteDTO.getUid());
        placeVoteEntity.setPlace_url(placeVoteDTO.getPlaceUrl());
        return placeVoteEntity;
    }
}
