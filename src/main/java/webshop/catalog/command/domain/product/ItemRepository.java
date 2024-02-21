package webshop.catalog.command.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
    Page<Item> findByCategoryIdsContains(Long id, Pageable pagable);

}
