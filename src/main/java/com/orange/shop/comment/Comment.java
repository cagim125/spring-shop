package com.orange.shop.comment;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String username;
    @Column(length = 1000)
    public String content;
    public Long parentId;

}
