package BurntingClub.Burnting.repository;

import BurntingClub.Burnting.entity.MatchedEntity.PlaceVoteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaceVoteRepository extends CrudRepository<PlaceVoteEntity, Long> {
    List<PlaceVoteEntity> findByChannel(String chatChannelCode);
    Long countByChannel(String chatChannelCode);
    @Query("select p.place_url, count(p.place_url) from PlaceVoteEntity p where p.channel = :channel and p.place_url = :place_url group by p.place_url")
    Long countByPlaceVote(@Param(value = "channel") String chatChannelCode, @Param(value = "place_url") String place_url);
}
