package BurntingClub.Burnting.repository;

import BurntingClub.Burnting.entity.MemberEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByUid(String uid);
    Optional<MemberEntity> deleteByUid(String uid);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update MemberEntity u set u.nickname = :nickname, u.age = :age, u.university = :university, u.major = :major, u.infotext = :infotext where u.uid = :uid")
    void updateInfo(@Param(value = "nickname") String nickname, @Param(value = "age") String age, @Param(value = "university") String university, @Param(value = "major") String major, @Param(value = "infotext") String infotext, @Param(value = "uid") String uid);
}