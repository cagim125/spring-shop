package com.orange.shop.item;

import com.orange.shop.comment.CommentRepository;
import com.orange.shop.config.S3Service;
import com.orange.shop.entity.Notice;
import com.orange.shop.exception.TitleTooLongException;
import com.orange.shop.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final NoticeRepository noticeRepository;
    private final ItemRepository itemRepository;
    private final S3Service s3Service;
    private final CommentRepository commentRepository;


    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/list/page/1";
    }


    @GetMapping("/list")
    public String list(Model model) {
        var result = itemService.findAll();
        model.addAttribute("items", result);
        return "list";
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
        var comments = commentRepository.findAllByParentId(id);

        if (result.isPresent()) {
            model.addAttribute("item", result.get());
            model.addAttribute("comments", comments);
        } else {
            return "redirect:/list";
        }

        return "detail.html";
    }

    @PostMapping("/add")
    public String add(String title, Integer price, String imageUrl ) {
        itemService.saveItem(title, price ,imageUrl);
        return "redirect:/list";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id , Authentication auth) {
        if (auth.getAuthorities().toString() == "관리자") {
            itemService.deleteItem(id);
            return ResponseEntity.status(200).body("삭제완료");

        } else {
            return ResponseEntity.status(200).body("일반유저");
        }

    }



    @GetMapping("/notice")
    public String notice(Model model) {
        List<Notice> result = noticeRepository.findAll();
        model.addAttribute("notices", result);
        return "notice.html";
    }

    @GetMapping("/list/page/{number}")
    public String PageList(@PathVariable Integer number, Model model) {
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(number - 1, 6));
        model.addAttribute("totalPage", result.getTotalPages());
        model.addAttribute("items", result);

        return "list";
    }

    @GetMapping("/prisigned-url")
    @ResponseBody
    public String getURL(@RequestParam String filename) {
        var result = s3Service.createPresignedUrl("test/" + filename);
        System.out.println(result);
        return result;
    }

    @PostMapping("/search")
    public String postSearch(@RequestParam String searchText, Model model) {
        var result = itemRepository.rawQuery1(searchText);
        System.out.println(result);
        model.addAttribute("items", result);
        return "list";
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
