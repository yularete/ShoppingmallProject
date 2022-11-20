package com.shop.service;

import com.shop.Repository.MemberRepository;
import com.shop.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional //비지니스 로직을 담당하는 서비스 계층 클래스에 선언. 로직을 처리중 에러 발생시 변경된 데이터를 로직을 수행하기 전으로 콜백시켜줌
@RequiredArgsConstructor //final or @NonNull이 붙은 필드에 생성자를 생성해줌.
public class MemberService {

        private final MemberRepository memberRepository;

        public Member saveMember(Member member){
            validateDuplicateMember(member);
            return memberRepository.save(member);
        }

        private void validateDuplicateMember(Member member){ //이미 가입된 회원의 경우 illegalStateException 예외 발생
            Member findMember = memberRepository.findByEmail(member.getEmail());
            if(findMember != null){
                throw new IllegalStateException("이미 가입된 회원입니다.");
            }
        }
}
