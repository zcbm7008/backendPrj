package webshop.service;


import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.domain.Member;
import webshop.domain.Seller;
import webshop.repository.MemberRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
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
        Seller seller1 = new Seller();
        seller1.setName("seller1");

        Seller seller2 = new Seller();
        seller2.setName("seller2");



        //When
        sellerService.seller(member.getId(), seller1);
        sellerService.seller(member.getId(), seller2);

        //Then
        List<Seller> sellerList = member.getSellers();


        Assertions.assertEquals(seller1.getId(), sellerList.get(0).getId());
        Assertions.assertEquals(seller1.getName(), sellerList.get(0).getName());
        Assertions.assertEquals(seller2.getId(), sellerList.get(1).getId());
        Assertions.assertEquals(seller2.getName(), sellerList.get(1).getName());

    }

    @Test
    public void Duplicated_Seller_exception() throws Exception{

        //Given
        Member member1 = createMember("회원1");
        Seller seller1 = new Seller();
        seller1.setName("seller1");

        Member member2 = createMember("회원2");
        Seller seller2 = new Seller();
        seller2.setName("seller1");

        //When
        sellerService.seller(member1.getId(), seller1);

        //Then
        assertThrows(IllegalStateException.class, () -> {
            sellerService.seller(member2.getId(), seller2);
        }, "중복 예외 발생");


    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        em.persist(member);
        return member;
    }


}
