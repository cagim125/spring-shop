package com.orange.shop.controller;

import com.orange.shop.entity.Item;
import com.orange.shop.entity.Notice;
import com.orange.shop.entity.User;
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

    private final ItemRepository itemRepository;
    private final NoticeRepository noticeRepository;
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService ,ItemRepository itemRepository, NoticeRepository noticeRepository) {
        this.itemRepository = itemRepository;
        this.noticeRepository = noticeRepository;
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


    @GetMapping("/notice")
    public String notice(Model model) {
        List<Notice> result = noticeRepository.findAll();
        model.addAttribute("notices", result);
        return "notice.html";
    }





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
