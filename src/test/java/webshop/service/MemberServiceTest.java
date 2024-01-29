package webshop.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.transaction.Transactional;
import org.springframework.test.context.transaction.TestTransaction;
import webshop.User.domain.member.BalanceAddedEvent;
import webshop.User.domain.member.BalanceSubtractedEvent;
import webshop.User.domain.member.Member;
import webshop.common.event.Events;
import webshop.common.model.Money;
import webshop.exception.NegativeBalanceException;
import webshop.repository.MemberRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    public void Member_SignUp() throws Exception {

        //Given
        Member member = new Member("Kim");
        //When
        Long saveId = memberService.join(member);

        //Then
        Optional<Member> foundMember = memberRepository.findById(saveId);
        assertTrue(foundMember.isPresent(), "Member not found");
        assertEquals(member, foundMember.get());
    }

    @Test
    public void Duplicated_member_exception() throws Exception {

        //Given
        Member member1 = new Member("Kim");

        Member member2 = new Member("Kim");

        //When
        memberService.join(member1);

        //Then
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        }, "Duplicate exception thrown");


    }

    @Test
    public void Member_Balance_Added() throws Exception {

        //Given
        Member member1 = new Member("Kim");

        member1.setBalance(new Money(5000));

        memberService.join(member1);

        //When
        member1.addBalance(new Money(5000));
        //Then
        assertEquals(10000, member1.getBalance().getValue());

    }

    @Test
    public void Member_Balance_Subtracted() throws Exception {

        //Given
        Member member1 = new Member("Kim");

        member1.setBalance(new Money(5000));

        memberService.join(member1);

        //When
        member1.subtractBalance(new Money(2000));

        //Then
        assertEquals(3000, member1.getBalance().getValue());
    }

    @Test
    public void Negative_Balance_Exception() throws Exception {
        //Given
        Member member1 = new Member("Kim");

        member1.setBalance(new Money(5000));

        memberService.join(member1);

        //When
        assertThrows(NegativeBalanceException.class, () -> {
            member1.subtractBalance(new Money(9000));
        }, "NegativeBalanceException thrown");
    }

    @Test
    public void Member_Balance_Added_or_Subtracted_Zero() throws Exception {
        //Given
        Member member1 = new Member("Kim");

        member1.setBalance(new Money(5000));

        memberService.join(member1);

        //When
        member1.subtractBalance(new Money(0));

        //Then
        assertEquals(5000, member1.getBalance().getValue());

        //When
        member1.addBalance(new Money(0));

        //Then
        assertEquals(5000, member1.getBalance().getValue());
    }

    @Test
    public void Block_Member() throws Exception{

        //Given
        Member member1 = new Member("kim");

        memberService.join(member1);

        //When
        member1.block();

        //Then
        assertEquals(member1.isBlocked(),true);

        //When
        member1.unblock();

        //Then
        assertEquals(member1.isBlocked(),false);
    }
}
