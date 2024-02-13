package webshop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.catalog.query.product.ItemService;
import webshop.catalog.command.domain.product.Item;
import webshop.user.domain.member.Member;
import webshop.order.command.domain.Order;
import webshop.order.command.domain.OrderItem;
import webshop.order.command.domain.OrderSearch;
import webshop.repository.MemberRepository;
import webshop.repository.OrderRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class OrderService {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemService itemService;

    //Order//
    public Long order(Long memberId, Long itemId, int count) {

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("Member not found with id: " + memberId));
        
        

        Item findItem = itemService.findOne(itemId)
                .orElseThrow(() -> new NoSuchElementException("Item not found with id: " + itemId));


        OrderItem orderItem = OrderItem.createOrderItem(findItem, count);

        Order order = Order.createOrder(findMember, orderItem);
        orderRepository.save(order);
        return order.getId();


    }

    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch.toSpecification());
    }
}
