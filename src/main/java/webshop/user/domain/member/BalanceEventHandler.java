package webshop.user.domain.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import webshop.user.domain.application.MemberBalanceService;

@Service
public class BalanceEventHandler {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private MemberBalanceService memberBalanceService;

    public BalanceEventHandler(MemberBalanceService memberBalanceService){
        this.memberBalanceService = memberBalanceService;
    }


    @Async
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void addHandle(BalanceAddedEvent event) {
        memberBalanceService.addBalance(event.getMemberId(),event.getMoney());

    }

    @Async
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void subHandle(BalanceSubtractedEvent event) {

        memberBalanceService.subtractBalance(event.getMemberId(),event.getMoney());
    }


}
