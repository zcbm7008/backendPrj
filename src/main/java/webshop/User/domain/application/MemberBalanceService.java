package webshop.User.domain.application;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import webshop.User.domain.member.Member;
import webshop.common.model.Money;
import webshop.repository.MemberRepository;

@AllArgsConstructor
@Service
public class MemberBalanceService {

    @Autowired
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
