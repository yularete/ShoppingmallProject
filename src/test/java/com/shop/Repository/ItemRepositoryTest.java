package com.shop.Repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired //ItemRepository를 사용하기 위해 Autowired 어노테이션을 이용해 Bean을 주입.
    ItemRepository itemRepository;

//    @Test
//    @DisplayName("상품 저장 테스트")
//    public void createItemTest(){
//        Item item = new Item();
//        item.setItemNm("테스트 상품");
//        item.setPrice(10000);
//        item.setItemDetail("테스트 상품 상세 설명");
//        item.setItemSellStatus(ItemSellStatus.SELL);
//        item.setStockNumber(100);
//        item.setRegTime(LocalDateTime.now());
//        item.setUpdateTime(LocalDateTime.now());
//        Item savedItem = itemRepository.save(item);
//        System.out.println(savedItem.toString());
//    }

    public void createItemList(){
        for(int i=1;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }
//
//    @Test
//    @DisplayName("상품명 조회 테스트")
//    public void findByItemNmTest(){
//        this.createItemList(); //테스트 상품을 만드는 메소드를 실행하여 조회할 대상을 만들어 줌 !
//        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1"); //메소드 호출 뒤 파라미터로 "테스트 상품1" 상품명 전달
//        for(Item item : itemList){
//            System.out.println(item.toString());
//        }
//    }

//    @Test
//    @DisplayName("상품명, 상품상세설명 or 테스트")
//    public void findByItemNmOrItemDetailTest(){
//        this.createItemList();
//        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1","테스트 상품 상세 설명5");
//        for(Item item : itemList){ // item의 내용을 itemList에 할당해주는?
//            System.out.println(item.toString());
//        }
//    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for (Item item : itemList){
            System.out.println(item.toString());
        }
    }

}