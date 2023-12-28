package webshop.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.domain.Member;
import webshop.domain.Order;
import webshop.domain.item.Artwork;
import webshop.domain.item.Item;
import webshop.repository.OrderRepository;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;


@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void Item_Order() throws Exception{

        //Given
        Member member = createMember();
        Item item = createArtwork("art",35000,true,3);
        int orderCount = 2;

        //When
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //Then
        Order getOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new AssertionError("Order not found"));


        assertEquals("주문한 상품 종류 수가 정확해야 한다.",1,getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.",35000 * 2,getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.",1,item.getStockQuantity());

    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        em.persist(member);
        return member;
    }

    private Artwork createArtwork(String name, int price, boolean isLimitedQuantity, int stockQuantity){
        Artwork artwork = new Artwork();
        artwork.setName(name);
        artwork.setPrice(price);
        artwork.setLimitedQuantity(isLimitedQuantity);
        artwork.setStockQuantity(stockQuantity);
        em.persist(artwork);
        return artwork;
    }

}
