package webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import webshop.order.command.domain.Order;
import webshop.repository.custom.CustomOrderRepository;

public interface OrderRepository extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order>, CustomOrderRepository {

}
