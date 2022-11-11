package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/thymeleaf") //url에 /thymeleaf경로로 오는 요청을 이 컨트롤러가 하겠다 !
public class ThymeleafExController {

    @GetMapping(value = "/ex01")
    public String thymeleafExample01(Model model){
        //model 객체를 이용 view에 전달할 데이터를 key, value 구조로 넣어준다.
        model.addAttribute("data","타임리프 예제 입니다.");
        //templates 폴더를 기준으로 뷰의 위치와 이름(thymeleafEx01.html)를 반환한다.
        return "thymeleafEx/thymeleafEx01";
    }
}
