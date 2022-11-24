package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//Web~ter를 상속받는 클래스에 @En~ty 선언하면 SpringSecurtyFilterChain이 자동으로 포함됨.
//Web~ter를 상속받아 메소드 오버라이딩을 통해 보안 설정을 커스터마이징 할 수 있음
    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http)throws Exception{
    //http 요청에 대한 보안 설정. 페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드 등에 대한 설정을 작성
        http.formLogin()
            .loginPage("/members/login") // 로그인 페이지 URL을 설정
            .defaultSuccessUrl("/")//로그인 성공 시 이동할 URL
            .usernameParameter("email")//로그인 시 사용할 파라미터 이름으로 email 지정
            .failureUrl("/members/login/error")//로그인 실패 시 이동할 URL
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))//로그아웃 URL
            .logoutSuccessUrl("/")//로그아웃 성공 시 이동할 URL
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    //BC~der의 해시 함수를 이용해 비밀번호를 암호화해 저장. 이를 빈으로 등록해 사용
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Spring Security에서 인증은 AuthenticationManager를 통해 이루어지며 auth가 am를 생성한다.
        //userDetailService를 구현하고 있는 객체로 memberService를 지정해주고, 비번 암호화를 위해 pe를 지정한다.
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

}
