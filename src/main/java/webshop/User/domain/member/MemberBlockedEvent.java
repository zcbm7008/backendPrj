package webshop.User.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import webshop.common.event.Event;

@Getter
public class MemberBlockedEvent extends Event {

    private Long memberId;

    public MemberBlockedEvent(Long memberId){
        super();
        this.memberId = memberId;
    }
}
