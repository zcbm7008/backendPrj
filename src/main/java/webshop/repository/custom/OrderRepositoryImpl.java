package webshop.repository.custom;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;
import webshop.user.domain.member.QMember;
import webshop.order.command.domain.Order;
import webshop.order.command.domain.OrderSearch;

import webshop.order.command.domain.QOrder;

import java.util.List;

public class OrderRepositoryImpl extends QuerydslRepositorySupport implements CustomOrderRepository  {

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> search(OrderSearch orderSearch) {

        QOrder order = QOrder.order;
       QMember member = QMember.member;

       JPQLQuery<Order> query = from(order);

       if(StringUtils.hasText(orderSearch.getMemberName())) {
           query.leftJoin(order.member, member)
                   .where(member.name.eq(orderSearch.getMemberName()));
       }


       return query.select(order).fetch();

    }
}
