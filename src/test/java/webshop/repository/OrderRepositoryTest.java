package webshop.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.order.command.domain.OrderRepository;
import webshop.user.domain.member.Member;
import webshop.catalog.command.domain.product.Artwork;
import webshop.common.model.Money;
import webshop.catalog.query.product.ItemService;
import webshop.user.domain.member.MemberService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Transactional
public class OrderRepositoryTest {

    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void test() throws Exception {

    }

    private Member createMember() {
        Member member = new Member("회원1");
        memberService.join(member);
        return member;
    }

    private Artwork createArtwork(String name, Money price,int stockQuantity){
        Artwork artwork = new Artwork();
        artwork.setName(name);
        artwork.setPrice(price);
        artwork.setStockQuantity(stockQuantity);
        itemService.saveItem(artwork);
        return artwork;
    }
}
