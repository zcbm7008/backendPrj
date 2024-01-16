package webshop.User.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import webshop.common.event.Event;

@AllArgsConstructor
@Getter
public class MemberBlockedEvent extends Event {

    private Long memberId;
}
