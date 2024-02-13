package webshop.user.domain.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import webshop.user.domain.application.BlockMemberService;


@Service
public class MemberBlockedEventHandler {

    @Autowired
    private BlockMemberService blockMemberService;

    public MemberBlockedEventHandler(BlockMemberService blockMemberService){
        this.blockMemberService = blockMemberService;
    }


    @Async
    @EventListener(MemberBlockedEvent.class)
    public void handle(MemberBlockedEvent event) {
        blockMemberService.block(event.getMemberId());
       }

}
