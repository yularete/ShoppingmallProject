package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//Web~ter를 상속받는 클래스에 @En~ty 선언하면 SpringSecurtyFilterChain이 자동으로 포함됨.
//Web~ter를 상속받아 메소드 오버라이딩을 통해 보안 설정을 커스터마이징 할 수 있음

    @Override
    protected void configure(HttpSecurity http)throws Exception{
    //http 요청에 대한 보안 설정. 페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드 등에 대한 설정을 작성
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    //BC~der의 해시 함수를 이용해 비밀번호를 암호화해 저장. 이를 빈으로 등록해 사용
    }



}
