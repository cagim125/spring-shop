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
        String hashPw = passwordEncoder.encode((String) item.get("password"));
        Member member = new Member();
        member.setDisplayName(item.get("username").toString());
        member.setPassword(hashPw);

        var result = memberRepository.save(member);
        System.out.println(result);
    }

    public List<Member> getMember() {
       List<Member> member =  memberRepository.findAll();

       return member;
    }

}
