package webshop.user.domain.member;

import lombok.Getter;
import webshop.common.event.Event;
import webshop.common.model.Money;

@Getter
public class BalanceAddedEvent extends Event {
    private Long memberId;
    private Money money;

    public BalanceAddedEvent(Long memberId, Money money){
        super();
        this.memberId = memberId;
        this.money = money;
    }

}
