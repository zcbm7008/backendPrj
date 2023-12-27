package webshop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import webshop.domain.item.Item;

import java.util.List;

public class ItemRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Item item) {
        if(item.getId()== null){
            em.persist(item);
        } else{
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i",
                Item.class).getResultList();
    }
}
