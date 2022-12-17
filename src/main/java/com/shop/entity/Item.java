package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item {
//실제론 여러가지 옵션 및 옵션 상품의 가격, 재고, 배송 방법에 대한 정보도 담겨있는데 최대한 간단하게 !

    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;      //상품코드

    @Column(nullable = false, length=50)
    private String itemNm;     //상품명

    @Column(name = "price", nullable = false)
    private int price;         //가격

    @Column(nullable = false)
    private int stockNumber;      //재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail;    //상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;   //상품 판매 상태

    private LocalDateTime regTime;   // 등록 시간

    private LocalDateTime updateTime;   // 수정 시간

    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber;
        if(restStock<0){
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량: " +
                    this.stockNumber + ")");
        }
        this.stockNumber = restStock;
    }
}
