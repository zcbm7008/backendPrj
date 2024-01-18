package webshop.User.domain.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import webshop.User.domain.application.MemberBalanceService;

@Service
public class BalanceEventHandler {

    @Autowired
    private MemberBalanceService memberBalanceService;

    public BalanceEventHandler(MemberBalanceService memberBalanceService){
        this.memberBalanceService = memberBalanceService;
    }


    @Async
    @TransactionalEventListener(
            classes = BalanceAddedEvent.class,
            phase = TransactionPhase.AFTER_COMMIT

    )
    public void addHandle(BalanceAddedEvent event) {
        memberBalanceService.addBalance(event.getMemberId(),event.getMoney());
    }

    @Async
    @TransactionalEventListener(
            classes = BalanceSubtractedEvent.class,
            phase = TransactionPhase.AFTER_COMMIT

    )
    public void subHandle(BalanceAddedEvent event) {
        memberBalanceService.subtractBalance(event.getMemberId(),event.getMoney());
    }


}
