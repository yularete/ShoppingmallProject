package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter @Setter
@ToString
public class Member {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true) //동일한 값이 DB에 들어올 수 없도록 Unique 속성
    private String email;

    private String password;

    private String adress;

    @Enumerated(EnumType.STRING) //enum타입을 entity의 속성으로 지정 가능. String이 좋대;
    private Role role;

    //Member entity를 생성하는 메소드. ... 엔티티에 메소드 만들어서 관리하면 관리하기 편하대용
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAdress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());//Bc~der Bean을 파라미터로 넘겨 암호화
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        return member;
    }
}
