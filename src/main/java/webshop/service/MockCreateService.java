package webshop.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.catalog.query.product.ItemService;
import webshop.User.domain.Member.Member;
import webshop.catalog.command.domain.product.Artwork;
import webshop.common.model.Money;
import webshop.catalog.command.domain.product.QuantityState;

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
        Member member = new Member("테스트회원1");

        memberService.join(member);

        Artwork artwork1 = createArtwork("testart1",new Money(20000),true,10);
        Artwork artwork2 = createArtwork("testart2",new Money(20000),false,10);

        itemService.saveItem(artwork1);
        itemService.saveItem(artwork2);

        orderService.order(member.getId(), artwork1.getId(),3);
    }

    private Artwork createArtwork(String name, Money price, boolean isLimitedQuantity, int stockQuantity){
        Artwork artwork = new Artwork();
        artwork.setName(name);
        artwork.setPrice(price);
        artwork.setQuantityState(QuantityState.Limited);
        artwork.setStockQuantity(stockQuantity);
        return artwork;
    }
}
