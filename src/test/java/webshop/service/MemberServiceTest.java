package webshop.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.transaction.Transactional;
import webshop.domain.Member;
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
    public void SignUp() throws Exception {

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
    public void Duplicated_member_exception() throws Exception{

        //Given
        Member member1 = new Member("Kim");

        Member member2 = new Member("Kim");

        //When
        memberService.join(member1);

        //Then
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        }, "중복 예외 발생");
        

    }





}
