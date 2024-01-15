package webshop.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.User.domain.member.Member;
import webshop.order.command.domain.Order;
import webshop.catalog.command.domain.product.Artwork;
import webshop.catalog.command.domain.product.Item;
import webshop.common.model.Money;
import webshop.exception.NotEnoughStockException;
import webshop.repository.OrderRepository;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Assertions;

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
        Item item = createArtwork("art",new Money(35000),3);
        item.setLimited();
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

    @Test
    public void ItemOrder_NotEnoughStock() throws Exception {

        //Given
        Member member = createMember();
        Item item = createArtwork("art",new Money(35000),3);
        item.setLimited();

        int orderCount = 10;

        //When

        //Then
        Assertions.assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        }, "재고 수량 부족 예외가 발생해야 합니다."); // "A NotEnoughStockException should be thrown."

    }


    private Member createMember() {
        Member member = new Member("회원1");
        em.persist(member);
        return member;
    }

    private Artwork createArtwork(String name, Money price, int stockQuantity){
        Artwork artwork = new Artwork();
        artwork.setName(name);
        artwork.setPrice(price);
        artwork.setStockQuantity(stockQuantity);
        em.persist(artwork);
        return artwork;
    }

}
