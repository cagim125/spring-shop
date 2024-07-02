package com.orange.shop.sales;

import com.orange.shop.item.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private Integer price;
    private Integer count;
    private Long memberId;
    @CreationTimestamp
    private LocalDateTime created;

    public Sales(Item item, Long memberId) {
        this.itemName = item.getTitle();
        this.price = item.getPrice();
        this.count = 1;
        this.memberId = memberId;

    }
}
