package webshop.catalog.command.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import webshop.catalog.command.domain.product.Item;

public interface ItemRepository extends JpaRepository<Item,Long> {

}