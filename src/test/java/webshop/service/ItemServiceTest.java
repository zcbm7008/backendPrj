package webshop.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.domain.item.Artwork;
import webshop.domain.item.Item;
import webshop.domain.item.Money;
import webshop.exception.NotEnoughStockException;
import webshop.exception.NotLimitedItemException;
import webshop.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Transactional
public class ItemServiceTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;

    @Test
    public void saveItem() throws Exception{

        //Given
        Item item = createArtwork("art",new Money(35000),false,0);
        Long itemId = item.getId();
        //When
        itemService.saveItem(item);


        //Then
        Optional<Item> foundItem = itemRepository.findById(itemId);
        assertTrue(foundItem.isPresent(),"Item not found");
        assertEquals(item, foundItem.get());

    }

    @Test
    public void Limited_Item_Check() throws Exception {

        //Given
        Item item = createArtwork("art", new Money(1000),false,2);

        //When

        //Then
        assertThrows (NotLimitedItemException.class, () -> {item.removeStock(1); }, "LimitedItem은 removeStock 사용 불가");
        assertThrows (NotLimitedItemException.class, () -> {item.addStock(5);}, "LimitedItem은 AddStock 사용 불가");
    }

    @Test
    public void NotEnoughStock_Check() throws Exception {

        //Given
        Item item = createArtwork("art", new Money(1000),true,2);

        //When

        //Then
        assertThrows (NotEnoughStockException.class, () -> {item.removeStock(5);}, "Not Enough Stock");

    }

    @Test
    public void Add_Stock_Check() throws Exception{
        //Given
        Item item = createArtwork("art", new Money(1000),true,2);
        //When
        item.addStock(3);
        //Then
        assertEquals(5,item.getStockQuantity());

    }

    private Artwork createArtwork(String name, Money price, boolean isLimitedQuantity, int stockQuantity){

        Artwork artwork = new Artwork();
        artwork.setName(name);
        artwork.setPrice(price);
        artwork.setLimitedQuantity(isLimitedQuantity);
        artwork.setStockQuantity(stockQuantity);
        em.persist(artwork);
        return artwork;
    }
}
