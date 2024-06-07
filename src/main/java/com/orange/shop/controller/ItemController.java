package com.orange.shop.controller;

import com.orange.shop.entity.Item;
import com.orange.shop.entity.Notice;
import com.orange.shop.entity.User;
import com.orange.shop.exception.TitleTooLongException;
import com.orange.shop.repository.ItemRepository;
import com.orange.shop.repository.NoticeRepository;
import com.orange.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Item> result = itemService.findAll();
        model.addAttribute("items", result);

        return "list.html";
    }

    @GetMapping("/write")
    public String write() {
        return "write.html";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        Optional<Item> result = itemService.findById(id);
        if (result.isPresent()) {
            model.addAttribute("item", result.get());
            return "modify.html";
        }
        return "redirect:/list";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, String title, Integer price) {
        if (title.trim().length() >= 100) {
            throw new TitleTooLongException("제목은 100자 이하로 설정해 주세요.");
        }
        if (price < 0) {
            throw new NegativeArraySizeException("가격은 음수로 설정 할 수 없어요.");
        }


        itemService.update(id, title, price);

        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Optional<Item> result = itemService.findById(id);
        if (result.isPresent()) {
            model.addAttribute("item", result.get());
        } else {
            return "redirect:/list";
        }

        return "detail.html";
    }

    @PostMapping("/add")
    public String add(String title, Integer price) {
        itemService.saveItem(title, price);
        return "redirect:/list";
    }


//    @GetMapping("/notice")
//    public String notice(Model model) {
//        List<Notice> result = noticeRepository.findAll();
//        model.addAttribute("notices", result);
//        return "notice.html";
//    }


//    @GetMapping("/user")
//    @ResponseBody
//    public String user(Model model) {
//        var object = new User();
//        object.setName("홍길동");
//        object.setAge(60);
//        object.나이설정(20);
//        System.out.println(object.getAge());
//        object.한살더하기();
//        System.out.println(object.getAge());
//        return object.getName();
//    }


}
