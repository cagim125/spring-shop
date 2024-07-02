package com.orange.shop.sales;

import com.orange.shop.item.ItemRepository;
import com.orange.shop.member.CustomUser;
import com.orange.shop.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesController {
    private final MemberRepository memberRepository;
    private final SaleRepository saleRepository;
    private final ItemRepository itemRepository;

    @PostMapping("/sales/{id}")
    public ResponseEntity<String> sales(@PathVariable Long id, Authentication auth) {
        if (auth == null || auth.getPrincipal() == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        CustomUser user = (CustomUser) auth.getPrincipal();
        Long userId = user.id;
        var item = itemRepository.findById(id).orElse(null);

        if (item == null) {
            return ResponseEntity.status(404).body("Item not found");
        }

        var resultTitleList = saleRepository.findAllByItemName(item.getTitle());
        var resultOptional = resultTitleList.stream()
                .filter(s -> s.getMember().getId().equals(userId))
                .findFirst();
        if (resultOptional.isPresent()) {
            Sales result = resultOptional.get();
            var count = result.getCount();
            result.setCount(count + 1);
            saleRepository.save(result);
            return ResponseEntity.status(200).body("Item already in cart, count updated");
        } else {
            Sales sales = new Sales(item, userId);
            saleRepository.save(sales);
            return ResponseEntity.status(200).body("Item added to cart");
        }

    }

    @GetMapping("/sales/all")
    public String getSalesAll() {
        List<Sales> result = saleRepository.findAll();
        System.out.println(result.get(0));
        return "list";
    }
}


