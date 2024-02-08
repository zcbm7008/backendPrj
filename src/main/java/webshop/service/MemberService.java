package webshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import webshop.User.domain.member.Member;
import webshop.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
	@Autowired
	PasswordEncoder passwordEncoder;

		@Autowired
		MemberRepository memberRepository;
		
		public Long join(Member member) {

			validateDuplicateMember(member);
			String encPassword = passwordEncoder.encode(member.getPassword());
			member.setPassword(encPassword);
			memberRepository.save(member);

			return member.getId();

		}

		private void validateDuplicateMember(Member member) {
			List<Member> findMembers =
					memberRepository.findByName(member.getName());
			if(!findMembers.isEmpty()) {
				throw new IllegalStateException("이미 존재하는 회원입니다.");
			}
		}

		public Member authenticate(String name, String password){
			Member member = memberRepository.findOneByName(name).orElseThrow(IllegalStateException::new);
			if (member != null && member.getPassword().equals(password)){
				return member;
			}
			return null;


		}

		public List<Member> findMembers() {
			return memberRepository.findAll();
	}

		
		
}
