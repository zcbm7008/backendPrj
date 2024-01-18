package webshop.User.domain.application;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import webshop.User.domain.member.Member;
import webshop.repository.MemberRepository;

@AllArgsConstructor
@Service
public class BlockMemberService {

    private MemberRepository memberRepository;

    @Transactional
    public void block(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalStateException::new);
        member.block();
    }

}
