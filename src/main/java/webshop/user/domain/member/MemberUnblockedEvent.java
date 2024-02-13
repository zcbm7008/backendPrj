package webshop.user.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class MemberUnblockedEvent {

    private Long memberId;
}
