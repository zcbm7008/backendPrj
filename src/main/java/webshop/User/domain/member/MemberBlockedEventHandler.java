package webshop.User.domain.member;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import webshop.User.domain.application.BlockMemberService;


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
