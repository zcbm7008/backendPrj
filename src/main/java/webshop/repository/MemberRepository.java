package webshop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import webshop.User.domain.Member.Member;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member,Long> {

	List<Member> findByName(String name);

}
