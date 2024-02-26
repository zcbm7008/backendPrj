package webshop.service;


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
    private MailService mailServiceMock;
    @Mock
    private Member member;
    private Order order;
    private Item item;
    private Orderer orderer;

    @BeforeEach
    public void setup() {
        mailServiceMock = mock(MailService.class);
        item = createArtwork("art",new Money(35000),3);

        OrderItem orderItem = new OrderItem(item.getId(), item.getPrice(), 3);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        orderer = new Orderer(member.getId(), member.getName());
        order = new Order(orderer,orderItems,OrderState.PAYMENT_WAITING);

        OrderNo orderNo = orderRepository.nextOrderNo();
        order.setNumber(orderNo);
    }


    @Test
    public void OrderDetailServiceTest() throws Exception{
        Optional<OrderDetail> orderDetail =  orderDetailService.getOrderDetail(order.getNumber().getNumber());

        if(orderDetail.isPresent()){
            assertEquals(orderer.getMemberId(),orderDetail.get().getOrderer().getMemberId());
        }

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
