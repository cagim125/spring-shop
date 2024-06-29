package com.orange.shop.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer price;
    private String imgUrl;


    public Item(Long id, String title, Integer price) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.imgUrl = imgUrl;
    }


}


