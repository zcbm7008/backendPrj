package webshop.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.domain.Member;
import webshop.domain.item.Artwork;

@Service
public class MockCreateService {
    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;
    @Autowired
    OrderService orderService;

    @PostConstruct
    public void initCreateMock() {
        Member member = new Member();
        member.setName("회원1");

        memberService.join(member);

        Artwork artwork1 = createArtwork("art",20000,true,10);
        Artwork artwork2 = createArtwork("art",20000,false,10);

        itemService.saveItem(artwork1);
        itemService.saveItem(artwork2);

        orderService.order(member.getId(), artwork1.getId(),3);
    }

    private Artwork createArtwork(String name,int price,boolean isLimitedQuantity,int stockQuantity){
        Artwork artwork = new Artwork();
        artwork.setName(name);
        artwork.setPrice(price);
        artwork.setLimitedQuantity(isLimitedQuantity);
        artwork.setStockQuantity(stockQuantity);
        return artwork;
    }
}
