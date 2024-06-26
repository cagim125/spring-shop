package com.orange.shop.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member")
    public String memberPage() {
        return "member/member";
    }
    @PostMapping("/insert")
    public String member_insert(@RequestBody Map<String, Object> body) {
        memberService.insertMember(body);
        return "redirect:/member";
    }
    @GetMapping("/user")
    public String userPage(Model model) {
        List<Member> result = memberService.getMember();

        model.addAttribute("members", result);

        return "member/user";
    }
}
