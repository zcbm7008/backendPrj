package webshop.order.query.dao;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import webshop.order.query.dto.OrderSummary;
import webshop.order.query.dto.OrderSummary_;

import java.time.LocalDateTime;

public class OrderSummarySpecs {
    public static Specification<OrderSummary> ordererId(String ordererId){
        return (Root<OrderSummary> root, CriteriaQuery<?> query,
                CriteriaBuilder cb) ->
                (Predicate) cb.equal(root.<String>get("ordererId"), ordererId);
    }

    public static Specification<OrderSummary> orderDateBetween(
            LocalDateTime from, LocalDateTime to) {
        return (Root<OrderSummary> root, CriteriaQuery<?> query,
        CriteriaBuilder cb) ->
                cb.between(root.get(String.valueOf(OrderSummary_.orderDate)),from,to);
    }
}
