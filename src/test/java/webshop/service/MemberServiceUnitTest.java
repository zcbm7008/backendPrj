package webshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import webshop.user.domain.member.Member;
import webshop.common.model.Money;
import webshop.exception.NegativeBalanceException;
import webshop.repository.MemberRepository;
import webshop.user.domain.member.MemberService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class MemberServiceUnitTest {
    @Mock
    MemberRepository memberRepository;

    @Mock
    MemberService memberService;

    Member member1;

    @BeforeEach
    void Setup(){
        member1 = new Member("Kim");

        member1.setBalance(new Money(5000));

        memberService.join(member1);
    }


    @Test
    public void should_Increase_Member_Balance_When_Added() throws Exception {

        //When
        member1.addBalance(new Money(5000));
        //Then
        assertEquals(10000, member1.getBalance().getValue());

    }

    @Test
    public void should_Decrease_Member_Balance_When_Subtracted() throws Exception {

        //When
        member1.subtractBalance(new Money(2000));

        //Then
        assertEquals(3000, member1.getBalance().getValue());
    }

    @Test
    public void Negative_Balance_Exception() throws Exception {

        //When
        assertThrows(NegativeBalanceException.class, () -> {
            member1.subtractBalance(new Money(9000));
        }, "NegativeBalanceException thrown");
    }

    @Test
    public void Member_Balance_Added_or_Subtracted_Zero() throws Exception {

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
