package webshop.service;


import com.google.cloud.storage.StorageException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.order.command.application.NoOrderProductException;
import webshop.order.command.application.OrderProduct;
import webshop.order.command.application.OrderRequest;
import webshop.order.command.application.PlaceOrderService;
import webshop.order.command.domain.*;
import webshop.order.query.application.OrderDetail;
import webshop.order.query.application.OrderDetailService;
import webshop.user.domain.member.Member;
import webshop.common.model.Email;
import webshop.catalog.command.domain.product.Artwork;
import webshop.catalog.command.domain.product.Item;
import webshop.common.model.Money;
import webshop.exception.NotEnoughStockException;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import webshop.user.domain.member.MemberService;
import webshop.util.MailService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PlaceOrderService placeOrderService;
    @Mock
    MemberService memberService;
    private MailService mailServiceMock;
    private Member member;
    private Order order;
    private Item item;
    private Orderer orderer;

    @BeforeEach
    public void setup() {
        member = createMember();

        item = createArtwork("art",new Money(35000),3);

        OrderItem orderItem = new OrderItem(item.getId(), item.getPrice(), 3);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        orderer = new Orderer(member.getId(), member.getName());
        OrderNo orderNo = orderRepository.nextOrderNo();
        order = new Order(orderNo,orderer,orderItems,OrderState.PAYMENT_WAITING);


    }


    @Test
    public void OrderDetailServiceTest() throws Exception{
        Optional<OrderDetail> orderDetail =  orderDetailService.getOrderDetail(order.getNumber().getNumber());

        if(orderDetail.isPresent()){
            assertEquals(orderer.getMemberId(),orderDetail.get().getOrderer().getMemberId());
        }

    }

    @Test
    public void placeOrderAndGetOrderDetailTest() throws Exception{
        //Given
        List <OrderProduct> orderProducts = new ArrayList<>();
        OrderProduct orderProduct1 = new OrderProduct(item.getId(),1);
        orderProducts.add(orderProduct1);
        OrderRequest mockedOrderRequest = mock(OrderRequest.class);

        when(mockedOrderRequest.getOrderProducts()).thenReturn(orderProducts);
        when(mockedOrderRequest.getOrdererMemberId()).thenReturn(member.getId());

        //When
        OrderNo orderNo1 = placeOrderService.placeOrder(mockedOrderRequest);
        Optional<OrderDetail> orderDetail = orderDetailService.getOrderDetail(orderNo1.getNumber());

        //Then
        orderDetail.ifPresent(detail -> assertEquals(orderer.getMemberId(), detail.getOrderer().getMemberId()));
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
