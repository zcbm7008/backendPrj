package webshop.repository.custom;

import webshop.domain.Order;
import webshop.domain.OrderSearch;

import java.util.List;

public interface CustomOrderRepository {

    public List<Order> search(OrderSearch orderSearch);
}
