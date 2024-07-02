package com.orange.shop.member;

import com.orange.shop.exception.AuthenticationException;
import com.orange.shop.sales.SaleRepository;
import com.orange.shop.sales.Sales;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final SaleRepository saleRepository;


    @GetMapping("/member")
    public String memberPage() {
        return "member/member";
    }

    @PostMapping("/insert")
    public String member_insert(@RequestBody Map<String, Object> body) {
        memberService.insertMember(body);
        return "redirect:/member";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "member/login";
    }
    @PostMapping("/login")
    public String login(String id, String pw, Model model) {


        return "redirect:/list";
    }

    @GetMapping("/my-page")
    public String myPage(Authentication auth, Model model) {

        if(auth.getName() == null ){
            throw new AuthenticationException("로그인 해주세요.");
        } else {
            var result = memberRepository.findByDisplayName(auth.getName()).get();
            List<Sales> sales = saleRepository.findAllByMemberId(result.getId());

            model.addAttribute("sales", sales);
            model.addAttribute("member", auth.getName());

            return "member/my-page";
        }

    }

    @GetMapping("/user")
    public String userPage(Model model) {
        List<Member> result = memberService.getMember();

        model.addAttribute("members", result);

        return "member/user";
    }

    @GetMapping("/user/1")
    @ResponseBody
    public MemberDto getUser() {
        var a = memberRepository.findById(1L);
        var result = a.get();
        MemberDto memberDto = new MemberDto(result.getDisplayName(), result.getId());

        return memberDto;
    }
}

class MemberDto {
    public String displayName;
    public Long id;

    MemberDto(String displayName, Long id) {
        this.displayName = displayName;
        this.id = id;
    }
}
