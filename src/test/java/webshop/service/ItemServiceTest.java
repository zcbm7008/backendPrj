package webshop.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.transaction.TestTransaction;
import webshop.catalog.query.product.CategoryItem;
import webshop.user.domain.seller.Seller;
import webshop.common.model.Image;
import webshop.catalog.query.product.ItemService;
import webshop.user.domain.member.Member;
import webshop.catalog.command.domain.product.Artwork;
import webshop.catalog.command.domain.product.Item;
import webshop.common.model.Money;
import webshop.exception.NotEnoughStockException;
import webshop.catalog.command.domain.product.ItemRepository;

import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Profile("test")
@Transactional
public class ItemServiceTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;



    @Test
    public void shouldSaveItem() throws Exception{

        //Given
        Item item = createArtwork("art",new Money(35000),0);
        Long itemId = item.getId();
        //When
        itemService.saveItem(item);


        //Then
        Optional<Item> foundItem = itemRepository.findById(itemId);
        assertTrue(foundItem.isPresent(),"Item not found");
        assertEquals(item, foundItem.get());

    }

    @Test
    public void shouldAddReviewToItem() throws Exception {
        //Given
        Member member = new Member("Kim");
        Item item = createArtwork("art", new Money(1000),2);

        //When
        item.addReview(member,"this is good");
        //Then
        assertEquals(item.getReviews().size(), 1);
        assertEquals(item.getReviews().get(0).getComment().getValue(), "this is good");
    }

    @Test
    public void shouldAddImageToItem() throws Exception {
        //Given
        Image mockImage = mock(Image.class);
        Item item = createArtwork("art", new Money(1000),2);

        //When
        item.addImage(mockImage);
        //Then
        assertEquals(item.getImages().size(), 1);
        assertEquals(item.getImages().get(0).getId(), mockImage.getId());
    }

    @Test
    public void Orderable_Item_Check() throws Exception {

        //Given
        Item item = createArtwork("art", new Money(1000),2);

        //When
        item.setDiscontinued();

        //Then
        assertThrows (IllegalStateException.class, () -> {item.saleStock(1); }, "Discontinued Item can not be orderable");
    }

    @Test
    public void NotEnoughStock_Check() throws Exception {

        //Given
        Item item = createArtwork("art", new Money(1000),2);
        item.setLimited();

        //When

        //Then
        assertThrows (NotEnoughStockException.class, () -> {item.removeStock(5);}, "Not Enough Stock");

    }

    @Test
    public void Add_Stock_Check() throws Exception{
        //Given
        Item item = createArtwork("art", new Money(1000),2);
        item.setLimited();
        //When
        item.addStock(3);
        //Then
        assertEquals(5,item.getStockQuantity());

    }


    @Test
    @Commit
    public void Sale_Stock() throws Exception{

        //Given
        Member member1 = createMember();
        Seller seller = new Seller(member1);

        em.persist(seller);

        Item item = createArtwork("art", new Money(1000),2);
        seller.addSellerItem(item);

        //When
        item.saleStock(3);

        System.out.println(member1.getBalance().getValue());


        TestTransaction.end();
        TestTransaction.start();
        //Then
        assertEquals(3000,member1.getBalance().getValue());
    }

    @Test
    @Commit
    public void Sale_Stock_Rollback() throws Exception{

        //Given
        Member member1 = createMember();
        Seller seller = new Seller(member1);

        em.persist(seller);


        Item item = createArtwork("art", new Money(1000),2);
        seller.addSellerItem(item);

        item.saleStock(3);
        //When
        TestTransaction.flagForRollback();

        //Then
        TestTransaction.end();
        TestTransaction.start();

        assertEquals(0,member1.getBalance().getValue());
    }



    private Artwork createArtwork(String name, Money price, int stockQuantity){

        Artwork artwork = new Artwork();
        artwork.setName(name);
        artwork.setPrice(price);
        artwork.setStockQuantity(stockQuantity);
        em.persist(artwork);
        return artwork;
    }

    private Member createMember() {
        Member member = new Member("회원1");
        em.persist(member);
        return member;
    }
}
