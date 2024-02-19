package webshop.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.catalog.query.product.ItemService;
import webshop.catalog.command.domain.product.Artwork;
import webshop.common.model.Money;
import webshop.catalog.command.domain.product.QuantityState;
import webshop.user.domain.member.MemberService;

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
