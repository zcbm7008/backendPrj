package webshop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.domain.Member;
import webshop.domain.Order;
import webshop.domain.OrderItem;
import webshop.domain.OrderSearch;
import webshop.domain.item.Item;
import webshop.repository.MemberRepository;
import webshop.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

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

        Optional<Member> findMember = memberRepository.findById(memberId);
        if (findMember.isEmpty()) {
            return null;
        }

        Optional<Item> findItem = itemService.findOne(itemId);
        if (findItem.isEmpty()) {
            return null;
        }

        OrderItem orderItem = OrderItem.createOrderItem(findItem.get(), findItem.get().getPrice(), count);

        Order order = Order.createOrder(findMember.get(), orderItem);
        orderRepository.save(order);
        return order.getId();


    }

    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch.toSpecification());
    }
}
