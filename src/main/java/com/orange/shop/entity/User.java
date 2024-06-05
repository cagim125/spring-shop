package com.orange.shop.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String name;
    private int age;

    public void 한살더하기(){
        this.age += 1;
    }
    public void 나이설정(int age) {
        if(age > 0 && age < 100){
            this.age = age;
        }
    }
}
