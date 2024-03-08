package webshop.catalog.command.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import webshop.order.query.dto.OrderSummary;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long>, JpaSpecificationExecutor<Item> {
    Page<Item> findByCategoryIdsContains(Long id, Pageable pageable);

    Page<Item> findAll(Specification<Item> spec,Pageable pageable);
}
