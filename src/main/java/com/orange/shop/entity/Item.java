package com.orange.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;

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


    public Item(Long id, String title, Integer price ) {
        this.id = id;
        this.title = title;
        this.price = price;
    }


}


