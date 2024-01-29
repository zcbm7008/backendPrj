package webshop.repository;


import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import webshop.User.domain.member.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member,Long> {
	List<Member> findByName(String name);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@QueryHints({
			@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")
	})
	@Query("select m from Member m where m.id = :id")
	Optional<Member> findByIdForUpdate(@Param("id") Long memberId);

}
