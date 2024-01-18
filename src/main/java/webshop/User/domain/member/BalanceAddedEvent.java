package webshop.User.domain.member;

import lombok.Getter;
import webshop.common.event.Event;
import webshop.common.model.Money;

@Getter
public class BalanceAddedEvent extends Event {
    private final Long memberId;
    private final Money money;

    public BalanceAddedEvent(Long memberId, Money money){
        super();
        this.memberId = memberId;
        this.money = money;
    }

}
