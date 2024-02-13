package webshop.user.domain.member;

import lombok.Getter;
import webshop.common.model.Money;

@Getter
public class BalanceSubtractedEvent {

    private Long memberId;
    private Money money;

    public BalanceSubtractedEvent(Long memberId, Money money){
        super();
        this.memberId = memberId;
        this.money = money;
    }
}
