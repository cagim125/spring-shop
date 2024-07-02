package com.orange.shop.sales;

import com.orange.shop.item.ItemRepository;
import com.orange.shop.member.CustomUser;
import com.orange.shop.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class SalesController {
    private final MemberRepository memberRepository;
    private final SaleRepository saleRepository;
    private final ItemRepository itemRepository;

    @PostMapping("/sales/{id}")
    public ResponseEntity<String> sales(@PathVariable Long id, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
//        System.out.println(user.id);
        if(auth == null || auth.getPrincipal() == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        } else {
            var item = itemRepository.findById(id).get();
            System.out.println(item);
            var member = memberRepository.findByDisplayName(auth.getName()).get();
            System.out.println(member);
            var result_title = saleRepository.findByItemName(item.getTitle());

            if(result_title.isPresent()) {
                var result = result_title.get();
                if(result.getMemberId() == member.getId()) {
                    var count = result.getCount();
                    result.setCount(count + 1);
                    saleRepository.save(result);
                    return ResponseEntity.status(200).body("장바구니이미있음");
                } else {
                    Sales sales = new Sales(item, member.getId());
                    saleRepository.save(sales);
                }
            } else {
                Sales sales = new Sales(item, member.getId());
                saleRepository.save(sales);

            }
        }
        return ResponseEntity.status(200).body("success");
    }
}


