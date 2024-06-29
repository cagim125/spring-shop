package com.orange.shop.cart;


import com.orange.shop.item.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String user;
    private int price;
    private int count;

    public Cart() {}

    public Cart(Item item, String user, int count) {
        this.title = item.getTitle();
        this.price = item.getPrice();
        this.user = user;
        this.count = count;
    }

}
