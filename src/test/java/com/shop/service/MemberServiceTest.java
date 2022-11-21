package com.shop.service;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //테스트 실행 후 롤백 처리가 됨! 반복적인 테스트 가능ㅎㅎ
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired //빈 주입하려고
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){ //회원정보를 입력한 Member Entity를 만듦
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest(){ //저장하려고 요청한 값과 실제 저장된 데이터를 비교! 첫째는 기대 값, 둘째는 실제로 저장된 값을 파라미터로 넣어쥼.
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getAdress(),savedMember.getAdress());
        assertEquals(member.getPassword(),savedMember.getPassword());
        assertEquals(member.getRole(),savedMember.getRole());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest(){
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);

        Throwable e = assertThrows(IllegalArgumentException.class, () -> { //예외처리 테스트
            memberService.saveMember(member2);});
        
        assertEquals("이미 가입된 회원입니다.", e.getMessage()); //발생한 예외 메시지가 예상 결과와 맞는지 검증
    }

}