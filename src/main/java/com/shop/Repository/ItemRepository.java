package com.shop.Repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//JpaRepository는 2개의 제네릭 타입을 사용한다.
// 1. entity 타입의 크래스
// 2. 기본키 타입
//JpaRepository는 기본적인 CRUD 및 페이징 처리를 위한 메소드가 정의되어 있다.
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemNm(String itemNm);
}
