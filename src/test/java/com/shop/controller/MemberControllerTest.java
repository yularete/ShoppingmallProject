package com.shop.controller;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc //웹 애플리케이션에서 컨트롤러를 테스트 할 때, 서블릿 컨테이너를 모킹(테스트를 위해 실제 객체와 비슷한 객체를 만드는 것)하기 위해서
@Transactional//해당 어노테이션이 적용되는 메소드를 하나의 트랜잭션(DB에서 이전 커밋 이후의 작업~작업 후 최종적으로 커밋을 날리기까지의 주기)으로 묶어준다.
@TestPropertySource(locations="classpath:application-test.properties") //새 구성 소스를 정의하고 해당 속성의 값을 재정의??
class MemberControllerTest {

    @Autowired //필요한 의존 객체의 "타입"에 해당하는 빈을 찾아 주입. 기분값이 true = 주입 대상이 없다면 구동에 실패???
    private MemberService memberService; //왜 생성자에 빈을 주입하죠...?

    //MockMvc 클래스를 이용해 실제 객체와 비슷하지만 테스트에 필요한 기능만 가지는 가짜 객체를.
    //MockMvc객체를 이용하면 웹 브라우저에서 요청을 하는 것처럼 테스트 할 수 있다.
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(String email, String password){
        //로그인 예제 진행을 위한 로그인 전 회원을 등록하는 메소드
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail(email);
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword(password);
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception{
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email, password);

        //userParameter()를 이용해 이메일을 아이디로 세팅하고 로그인 URL에 요청한다.
        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/members/login")
                .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception{
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email, password);
        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/members/login")
                .user(email).password("12345"))
                //인증되지 않은 결과 값이 출력되어 결국 테스트는 통과...
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

}