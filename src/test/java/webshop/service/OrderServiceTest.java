package webshop.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.user.domain.member.Member;
import webshop.common.model.Email;
import webshop.order.command.domain.EmailSendingException;
import webshop.order.command.domain.Order;
import webshop.catalog.command.domain.product.Artwork;
import webshop.catalog.command.domain.product.Item;
import webshop.common.model.Money;
import webshop.exception.NotEnoughStockException;
import webshop.order.command.domain.OrderItem;
import webshop.order.command.domain.OrderState;
import webshop.repository.OrderRepository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import webshop.util.MailService;

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

    private MailService mailServiceMock;
    private Member member;
    private Order order;
    private Item item;

    @BeforeEach
    public void setup() {
        mailServiceMock = mock(MailService.class);
        member = createMember();
        member.setEmail(new Email("test@example.com"));
        item = createArtwork("art",new Money(35000),3);

        OrderItem orderItem = new OrderItem();
        OrderItem.createOrderItem(item,2);

        order = new Order();
        order.addOrderItem(orderItem);
        order.setMember(member);
        order.setMailService(mailServiceMock);
    }


    @Test
    public void Item_Order() throws Exception{

        //Given
        item.setLimited();
        int orderCount = 2;

        //When
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //Then
        Order getOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new AssertionError("Order not found"));

        System.out.println(getOrder.getOrderItems().get(0).getCount());

        assertEquals("주문한 상품 종류 수가 정확해야 한다.",1,getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.",35000 * orderCount,getOrder.getTotalPrice().getValue());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.",1,item.getStockQuantity());

    }

    @Test
    public void ItemOrder_NotEnoughStock() throws Exception {

        //Given
        item.setLimited();

        int orderCount = 10;

        //When

        //Then
        Assertions.assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        }, "재고 수량 부족 예외가 발생해야 합니다."); // "A NotEnoughStockException should be thrown."

    }

    @Test
    public void Start_Delivering() throws Exception {
        order.startDelivering();

        //Then
        verify(mailServiceMock, times(1)).sendSimpleEmail(anyString(), eq(member.getEmail()), anyString());
        assertEquals(OrderState.DELIVERY_COMPLETED, order.getState());
    }

    @Test
    public void startDelivering_Failure() throws Exception{
        // Mocking


        //When

        doThrow(new EmailSendingException()).when(mailServiceMock).sendSimpleEmail(anyString(), eq(member.getEmail()), anyString());

        order.startDelivering();

        //Then
        assertNotEquals(OrderState.DELIVERY_COMPLETED, order.getState());
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
