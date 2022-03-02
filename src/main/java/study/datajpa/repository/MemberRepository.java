package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<Member> findByUserNameAndAgeGreaterThan(String userName, int age);

    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.userName = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.userName from Member m")
    List<String> findUserList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.userName, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.userName in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUserName(String userName);

    Member findMemberByUserName(String userName);

    Optional<Member> findOptionalByUserName(String userName);

    //갯수가 많을 수록(조인이 많을 수록) count 쿼리를 분리하라. -> 성능테스트 후.
    @Query(value = "select m from Member m left join m.team"
            , countQuery = "select count(m.userName) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true) // clearAutomatically = true -> 쿼리 실행 후 자동으로 영속성 컨텍스트를 지움.
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    // team 정보 한번에 긁어오기
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    //@EntityGraph(attributePaths = {"team"})
    @EntityGraph("Member.all")
    List<Member> findAll();

    // queryhint,,, -> 성능테스트 후 결정할 것,, 효과가 미미하다.
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUserName(String userName);

    // select ~ for update
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUserName(String userName);
}
