package com.orange.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member =  memberRepository.findByDisplayName(username);
        if (member.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        var user = member.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        System.out.println(user.getDisplayName());
        if(user.getDisplayName() == "admin") {
            authorities.add(new SimpleGrantedAuthority("관리자"));
        } else {
            authorities.add(new SimpleGrantedAuthority("일반유저"));
        }
        var a = new CustomUser(user.getDisplayName(), user.getPassword(), authorities);
        a.id = user.getId();
        return a;
    }
}


