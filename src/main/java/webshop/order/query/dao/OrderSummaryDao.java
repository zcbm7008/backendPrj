package webshop.order.query.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import webshop.order.query.dto.OrderSummary;
import webshop.order.query.dto.OrderView;

import java.util.List;

public interface OrderSummaryDao extends Repository<OrderSummary,String> {
    List<OrderSummary> findByOrdererId(Long OrdererId);
    List<OrderSummary> findByOrdererId(Long ordererId, Sort sort);
    List<OrderSummary> findByOrdererId(Long ordererId, Pageable pageable);
    List<OrderSummary> findByOrdererIdOrderByNumberDesc(Long ordererId);

    List<OrderSummary> findAll(Specification<OrderSummary> spec);
    List<OrderSummary> findAll(Specification<OrderSummary> spec, Sort sort);
    List<OrderSummary> findAll(Specification<OrderSummary> spec, Pageable pageable);

    Page<OrderSummary> findAll(Pageable pageable);

    @Query("""
            select new webshop.order.query.dto.OrderView(
                o.number, o.state, m.name, m.id, i.name
            )
            from Order o join o.orderItems oI, Member m, Item i
            where o.orderer.memberId = :ordererId
            and o.orderer.memberId = m.id
            and index(oI) = 0
            and oI.itemId = i.id
            order by o.number.number desc
            """)
    List<OrderView> findOrderView(String ordererId);
}
