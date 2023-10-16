package BurntingClub.Burnting.repository;

import BurntingClub.Burnting.dto.MemberDTO;
import BurntingClub.Burnting.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
//    public String insertMember(MemberEntity memberEntity) throws Exception;
//    public MemberDTO getMemberDetail(String id) throws Exception;
//    public String updateMember(MemberEntity memberEntity) throws Exception;
//    public String deleteMember(String id) throws Exception;

//    Optional<MemberEntity> findByEmail(String memberEmail);
//    @Modifying(clearAutomatically = true)
//    @Transactional
//    @Query(value = "update MemberEntity u set u.nickname = :nickname, u.age = :age, u.university = :university, u.major = :major, u.infotext = :infotext where u.email = :email")
//    void updateInfo(@Param(value = "nickname") String nickname, @Param(value = "age") Long age, @Param(value = "university") String university, @Param(value = "major") String major, @Param(value = "infotext") String infotext, @Param(value = "email") String email);
//    @Query(value = "select u.num, u.email, u.nickname, u.age, u.university, u.major, u.infotext from MemberEntity u where u.email = :email")
//    Optional<MemberEntity> readUser(@Param(value = "email") String email);
}