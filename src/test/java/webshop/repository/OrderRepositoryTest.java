package webshop.repository;

import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.domain.Member;
import webshop.domain.Order;
import webshop.domain.OrderSearch;
import webshop.domain.item.Artwork;
import webshop.service.ItemService;
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
        Artwork artwork = createArtwork("art", 10000, false, 0);
        orderService.order(member.getId(), artwork.getId(),1);

        //When
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName("회원1");

        List<Order> search = orderRepository.search(orderSearch);

        //Then
        Assertions.assertEquals(1, search.size());
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        memberService.join(member);
        return member;
    }

    private Artwork createArtwork(String name, int price, boolean isLimitedQuantity, int stockQuantity){
        Artwork artwork = new Artwork();
        artwork.setName(name);
        artwork.setPrice(price);
        artwork.setLimitedQuantity(isLimitedQuantity);
        artwork.setStockQuantity(stockQuantity);
        itemService.saveItem(artwork);
        return artwork;
    }
}
