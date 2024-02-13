package webshop.user.domain.application;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.user.domain.member.Member;
import webshop.common.model.Money;
import webshop.repository.MemberRepository;

@AllArgsConstructor
@Service
public class MemberBalanceService {

    @Autowired
    private MemberRepository memberRepository;




    public void addBalance(Long memberId, Money money){
        Member member = memberRepository.findById(memberId).orElseThrow(NoMemberException::new);
        member.addBalance(money);
    }


    public void subtractBalance(Long memberId, Money money){
        Member member = memberRepository.findById(memberId).orElseThrow(NoMemberException::new);
        member.subtractBalance(money);
    }
}
