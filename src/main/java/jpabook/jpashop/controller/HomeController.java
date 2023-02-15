package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class HomeController {
    //Logger log= LoggerFactory.getLogger(getClass()); -> Lombok에서 @Slf4j 지원
    @RequestMapping("/")
    public String home(){
        log.info("home controller 입력!");
        return "home";// templates -> home.html로 간다.
    }
}
