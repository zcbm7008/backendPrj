package webshop.user.domain.application;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import webshop.user.domain.member.Member;
import webshop.repository.MemberRepository;

@AllArgsConstructor
@Service
public class BlockMemberService {

    private MemberRepository memberRepository;

    @Transactional
    public void block(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(NoMemberException::new);
        member.block();
    }

}
