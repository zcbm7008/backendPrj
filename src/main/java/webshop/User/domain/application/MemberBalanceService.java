package webshop.User.domain.application;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import webshop.User.domain.member.Member;
import webshop.common.model.Money;
import webshop.repository.MemberRepository;
@AllArgsConstructor
@Service
public class MemberBalanceService {

    private MemberRepository memberRepository;

    @Transactional
    public void addBalance(Long memberId, Money money){
        Member member = memberRepository.findById(memberId).orElseThrow(NoMemberException::new);
        member.addBalance(money);
    }

    @Transactional
    public void subtractBalance(Long memberId, Money money){
        Member member = memberRepository.findById(memberId).orElseThrow(NoMemberException::new);
        member.subtractBalance(money);
    }
}
