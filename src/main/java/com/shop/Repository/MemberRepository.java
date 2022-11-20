package com.shop.Repository;


import com.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email); //중복회원 검사하기 위한 쿼리 메소드 작성
}
