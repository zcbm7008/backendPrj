package webshop.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.User.domain.member.Member;
import webshop.order.command.domain.Order;
import webshop.order.command.domain.OrderSearch;
import webshop.catalog.command.domain.product.Artwork;
import webshop.common.model.Money;
import webshop.catalog.query.product.ItemService;
import webshop.service.MemberService;
import webshop.service.OrderService;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Transactional
public class OrderRepositoryTest {

    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void test() throws Exception {

        //Given
        Member member = createMember();
        Artwork artwork = createArtwork("art", new Money(10000), 0);
        orderService.order(member.getId(), artwork.getId(),1);

        //When
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName("회원1");

        List<Order> search = orderRepository.search(orderSearch);

        //Then
        Assertions.assertEquals(1, search.size());
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
