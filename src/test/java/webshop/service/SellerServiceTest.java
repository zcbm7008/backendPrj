package webshop.service;


import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.user.domain.member.Member;
import webshop.user.domain.seller.Seller;
import webshop.catalog.command.domain.product.Artwork;
import webshop.common.model.Money;
import webshop.user.domain.seller.SellerService;
import webshop.user.query.seller.SellerDTO;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Transactional
public class SellerServiceTest {


    @Autowired
    EntityManager em;
    @Autowired
    SellerService sellerService;

    @Test
    public void Create_Seller() throws Exception{

        //Given
        Member member = createMember("회원1");

        Seller seller1 = new Seller(member);
        seller1.setName("seller1");

        //When
        Seller seller2 = new Seller(member);

        sellerService.join(seller1);
        sellerService.join(seller2);

        //Then
        List<Seller> sellerList = member.getSellers();

        Assertions.assertEquals(seller1.getId(), sellerList.get(0).getId());
        Assertions.assertEquals(seller1.getName(), sellerList.get(0).getName());
        Assertions.assertEquals(seller2.getId(), sellerList.get(1).getId());
        Assertions.assertEquals(seller2.getName(), sellerList.get(1).getName());

    }

    @Test
    public void Create_Seller_Items() throws Exception{
        //Given
        Member member = createMember("회원1");

        Seller seller1 = new Seller(member);
        seller1.setName("seller1");
        Seller seller2 = new Seller(member);
        seller2.setName("seller2");

        Artwork item1 = createArtwork("art1",new Money(25000),1);
        Artwork item2 = createArtwork("art2",new Money(20000),1);

        seller1.setMember(member);
        seller2.setMember(member);
        sellerService.join(seller1);
        sellerService.join(seller2);
        //When
        sellerService.addItemToSeller(seller1.getId(), item1.getId());
        sellerService.addItemToSeller(seller2.getId(), item2.getId());

        //Then
        List<Seller> sellerList = member.getSellers();

        Assertions.assertEquals(item1.getId(), sellerList.get(0).getSellerItems().get(0).getId());
        Assertions.assertEquals(item2.getId(), sellerList.get(1).getSellerItems().get(0).getId());


    }

    @Test
    public void Duplicated_Seller_exception() throws Exception{

        //Given
        Member member1 = createMember("회원1");
        Seller seller1 = new Seller(member1);
        seller1.setName("seller1");

        Member member2 = createMember("회원2");
        Seller seller2 = new Seller(member2);
        seller2.setName("seller1");

        //When
        sellerService.join(seller1);


        //Then
        assertThrows(IllegalStateException.class, () -> sellerService.join(seller2), "중복 예외 발생");


    }

    @Test
    public void Block_Seller_Member() throws Exception {

        //Given
        Member member1 = createMember("회원1");
        Seller seller1 = new Seller(member1);
        seller1.setName("seller1");

        sellerService.join(seller1);

        //When
        seller1.blockMember();

        //Then
        assertTrue("Member should be blocked", member1.isBlocked());

    }

    @Test
    public void blockedSellerCannotAddItems() throws Exception {
        //Given
        Member member1 = createMember("회원1");
        Seller seller1 = new Seller(member1);
        seller1.setName("seller1");

        sellerService.join(seller1);

        Artwork item1 = createArtwork("art1",new Money(25000),1);

        //When
        seller1.blockMember();

        //Then

        assertThrows(IllegalStateException.class, () -> {
            sellerService.addItemToSeller(seller1.getId(), item1.getId());
        }, "Blocked seller cannot add items");
    }

    @Test
    public void findSellerByMemberName() throws Exception{
        //Given
        Member member1 = createMember("회원1");
        Seller seller1 = new Seller(member1);
        Seller seller2 = new Seller(member1);

        seller1.setName("seller1");
        seller2.setName("seller2");

        sellerService.join(seller1);
        sellerService.join(seller2);

        //When
        List<SellerDTO> sellerDTOS = sellerService.getSellersWithMemberName(member1.getName());

        //Then
        assertEquals(2,sellerDTOS.size());
        assertEquals("seller1", sellerDTOS.get(0).getName());
        assertEquals("seller2", sellerDTOS.get(1).getName());
    }



    private Member createMember(String name) {
        Member member = new Member(name);
        em.persist(member);
        return member;
    }

    private Artwork createArtwork(String name, Money price, int stockQuantity){
        Artwork artwork = new Artwork();
        artwork.setName(name);
        artwork.setPrice(price);
        artwork.setStockQuantity(stockQuantity);
        em.persist(artwork);
        return artwork;
    }

}
