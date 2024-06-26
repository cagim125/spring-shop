package com.orange.shop.member;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;



    public void insertMember(Map<String, Object> item) {
        String hashPw = passwordEncoder.encode((String) item.get("userPw"));
        Member member = new Member();
        member.setId(item.get("userid").toString());
        member.setPw(hashPw);
        member.setName(item.get("username").toString());

        var result = memberRepository.save(member);
        System.out.println(result);
    }

    public List<Member> getMember() {
       List<Member> member =  memberRepository.findAll();

       return member;
    }

}
