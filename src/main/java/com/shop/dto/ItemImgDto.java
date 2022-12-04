package com.shop.dto;

import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter @Setter
public class ItemImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repimgYn;

    //멤버 변수로 modelMapper 객체를 추가
    private static ModelMapper modelMapper = new ModelMapper();

    //itemImg 엔티티 객체를 파라미터로 받아서 itemImg 객체의 자료형과 멤버변수의 이름이 같을 때
    //ItemimgDto로 값을 복사해서 반환, static 메소드로 선언해 객체를 생성하지 않아도 호출할 수 있음
    public static ItemImgDto of(ItemImg itemImg){
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
