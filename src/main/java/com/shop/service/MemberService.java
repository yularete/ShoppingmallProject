package com.shop.service;

import com.shop.Repository.MemberRepository;
import com.shop.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional //비지니스 로직을 담당하는 서비스 계층 클래스에 선언. 로직을 처리중 에러 발생시 변경된 데이터를 로직을 수행하기 전으로 콜백시켜줌
@RequiredArgsConstructor //final or @NonNull이 붙은 필드에 생성자를 생성해줌.
public class MemberService implements UserDetailsService { // MS가 UDS를 구현하겠다 !

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) { //이미 가입된 회원의 경우 illegalStateException 예외 발생
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { //lubU를 오버라이딩.. 로그인할 유저의 email를 파라미터로 전달받음
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException(email);
        }
        return User.builder() //UserDetail을 구현하고 있는 User 객체를 반환. 객체를 생성하기 위해 생성자로 파라미터로 넘겨줌...
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
