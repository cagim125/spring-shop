package com.orange.shop.member;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String displayName;
    private String password;
}
