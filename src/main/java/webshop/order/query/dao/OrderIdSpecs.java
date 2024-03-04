package webshop.order.query.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import webshop.order.query.dto.OrderSummary;
import webshop.order.query.dto.OrderSummary_;

@AllArgsConstructor
public class OrderIdSpecs implements Specification<OrderSummary> {

    private String ordererId;
    @Override
    public Predicate toPredicate(Root<OrderSummary> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder cb){
        return cb.equal(root.get(String.valueOf(OrderSummary_.ordererId)), ordererId);
    }

}
