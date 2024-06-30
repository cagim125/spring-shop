package com.orange.shop.comment;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    @PostMapping("/comment")
    public String postComment(@RequestParam String content,
                              @RequestParam Long parent,
                              Authentication auth) {
        var username = auth.getName();

        var data = new Comment();
        data.setContent(content);
        data.setUsername(username);
        data.setParentId(parent);
        commentRepository.save(data);
        return "redirect:/list";
    }
}
