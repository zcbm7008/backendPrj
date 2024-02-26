package webshop.order.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.order.command.domain.Orderer;
import webshop.order.command.domain.OrdererService;
import webshop.user.domain.member.Member;
import webshop.user.domain.member.MemberService;

@Service
public class OrdererServiceImpl implements OrdererService {

    @Autowired
    private MemberService memberService;

    @Override
    public Orderer createOrderer(Long memberId){
        Member member = memberService.findById(memberId);
        return new Orderer(member.getId(),member.getName());
    }
}
