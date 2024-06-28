package com.orange.shop.cart;


import com.orange.shop.repository.ItemRepository;
import com.orange.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartRepository cartRepository;
    private final ItemService itemService;

    @GetMapping("/cart")
    public String cart(Authentication auth, Model model) {
        String username = auth.getName();
        List<Cart> result =  cartRepository.findAllByUser(username);

        model.addAttribute("carts", result);

        return "cart";
    }

    @PostMapping("/cart/add/{id}")
    public ResponseEntity<String> addCart(Authentication auth, @PathVariable Long id) {
        var cartItem = cartRepository.findById(id).get();
        if(cartItem == null) {
            var result = itemService.findById(id).get();
            Cart cart = new Cart(result, auth.getName(), 1);
            cartRepository.save(cart);
        } else {
            if(cartItem.getUser() == auth.getName()){
                if(cartRepository.existsById(id)){
                    int cartCount = cartItem.getCount();
                    cartCount += 1;
                    cartItem.setCount(cartCount);
                    cartRepository.save(cartItem);
                } else {
                    var result = itemService.findById(id).get();
                    Cart cart = new Cart(result, auth.getName(), 1);
                    cartRepository.save(cart);
                }
            } else {
                if(cartRepository.existsById(id) && cartItem.getUser() == auth.getName()){
                    int cartCount = cartItem.getCount();
                    cartCount += 1;
                    cartItem.setCount(cartCount);
                    cartRepository.save(cartItem);
                } else {
                    var result = itemService.findById(id).get();
                    Cart cart = new Cart(result, auth.getName(), 1);
                    cartRepository.save(cart);
                }
            }
        }









        return ResponseEntity.status(200).body("success");
    }
}
