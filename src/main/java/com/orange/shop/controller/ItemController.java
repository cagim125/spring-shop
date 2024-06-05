package com.orange.shop.controller;

import com.orange.shop.entity.Item;
import com.orange.shop.entity.Notice;
import com.orange.shop.entity.User;
import com.orange.shop.repository.ItemRepository;
import com.orange.shop.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class ItemController {

    private final ItemRepository itemRepository;
    private final NoticeRepository noticeRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository, NoticeRepository noticeRepository) {
        this.itemRepository = itemRepository;
        this.noticeRepository = noticeRepository;
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Item> result =  itemRepository.findAll();
        model.addAttribute("items", result);

        return "list.html";
    }

    @GetMapping("/notice")
    public String notice(Model model) {
        List<Notice> result = noticeRepository.findAll();
        model.addAttribute("notices", result);
        return "notice.html";
    }

    @GetMapping("/write")
    public String write() {
        return "write.html";
    }

    @PostMapping("/add")
    public String add(@RequestParam Map formData) {
        Item item = new Item();

        Object titleObj = formData.get("title");
        if(titleObj != null)
            item.setTitle(titleObj.toString());

        Object priceObj = formData.get("price");
        if(priceObj != null) {
            Integer price = Integer.parseInt(priceObj.toString());
            item.setPrice(price);
        }
        itemRepository.save(item);

        return "redirect:/list";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user(Model model) {
        var object = new User();
        object.setName("홍길동");
        object.setAge(60);
        object.나이설정(20);
        System.out.println(object.getAge());
        object.한살더하기();
        System.out.println(object.getAge());
        return object.getName();
    }
}
