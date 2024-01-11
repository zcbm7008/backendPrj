package webshop.repository.custom;

import webshop.order.command.domain.Order;
import webshop.order.command.domain.OrderSearch;

import java.util.List;

public interface CustomOrderRepository {

    public List<Order> search(OrderSearch orderSearch);
}
