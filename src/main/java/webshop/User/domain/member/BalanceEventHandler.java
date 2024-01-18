package webshop.User.domain.member;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import webshop.User.domain.application.BlockMemberService;
import webshop.User.domain.application.MemberBalanceService;

@Service
public class BalanceEventHandler {

    @Autowired
    private MemberBalanceService memberBalanceService;

    public BalanceEventHandler(MemberBalanceService memberBalanceService){
        this.memberBalanceService = memberBalanceService;
    }


    @Async
    @EventListener(BalanceAddedEvent.class)
    public void addHandle(BalanceAddedEvent event) {
        memberBalanceService.addBalance(event.getMemberId(),event.getMoney());
    }

    @Async
    @EventListener(BalanceSubtractedEvent.class)
    public void subHandle(BalanceAddedEvent event) {
        memberBalanceService.subtractBalance(event.getMemberId(),event.getMoney());
    }


}
