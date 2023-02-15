package jpabook.jpashop.controller;

import jpabook.jpashop.Service.MemberService;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        return "members/createMemberForm";
    }
    @PostMapping("/members/new")
    //@Valid -> 다시 확인해보기
    // BindinResult
    public String create(@Valid MemberForm form, BindingResult result){ // Valid, BindingResult 
        if(result.hasErrors()){// 오류가 있을경우 -> 다시 createMemberForm
            return "members/createMemberForm";  // 이름이 없을경우 데이터를 가지고 다시 redirect
        }
        Address address= new Address(form.getCity(),form.getStreet(),form.getZipcode());

        Member member =new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }
    @GetMapping("/members")
    private String list(Model model){
        List<Member> members = memberService.findMembers(); // api 만들때는 엔티티 반환X
        model.addAttribute("members",members);
        return "members/memberList";
    }

}
