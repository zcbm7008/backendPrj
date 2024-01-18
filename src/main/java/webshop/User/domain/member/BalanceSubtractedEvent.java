package webshop.User.domain.member;

import lombok.Getter;
import webshop.common.model.Money;

@Getter
public class BalanceSubtractedEvent {

    private final Long memberId;
    private final Money money;

    public BalanceSubtractedEvent(Long memberId, Money money){
        super();
        this.memberId = memberId;
        this.money = money;
    }
}
