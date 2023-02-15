package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Hello {
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","안녕하세요!");
        return "HelloController";
    }
}
