package webshop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webshop.domain.item.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

}
