package webshop.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.transaction.Transactional;
import webshop.domain.Member;
import webshop.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Transactional
public class MemberServiceTest {
	
	
	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;




	
    @Test
    public void SignUp() throws Exception {

        //Given
        Member member = new Member();
        member.setName("kim");

        //When
        Long saveId = memberService.join(member);

        //Then
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void duplicated_member_exception() throws Exception{

        //Given
        Member member1 = new Member();
        member1.setName("Kim");

        Member member2 = new Member();
        member2.setName("Kim");

        //when
        memberService.join(member1);

        //Then
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        }, "중복 예외 발생");
        

    }



}
