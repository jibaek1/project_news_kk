package com.ssr.newskuku.domain.news.controller;

import com.ssr.newskuku.domain.login.UserInfo;
import com.ssr.newskuku.domain.news.NewsCommentService;
import com.ssr.newskuku.domain.news.dto.NewsCommentDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsCommentController {

    private final NewsCommentService service;


    public NewsCommentController(NewsCommentService service) {
        this.service = service;
    }

    // 댓글 목록
    @GetMapping("/{newsId}/comment")
    public String list(@PathVariable Long newsId, Model model) {
        List<NewsCommentDTO> comments = service.getCommentsByNewsId(newsId);
        model.addAttribute("comments", comments);
        return "news/comments/list"; // JSP 경로
    }

    // 댓글 작성
    @PostMapping("/{newsId}/comment")
    public String addComment(@PathVariable Long newsId,
                             @RequestParam String content,
                             @SessionAttribute("loginUser") UserInfo loginUser) {

        NewsCommentDTO comment = new NewsCommentDTO();
        comment.setNewsId(newsId);
        comment.setUserId(loginUser.getUserId()); // 로그인 유저 아이디
        comment.setContent(content);

        service.addComment(comment);
        return "redirect:/news/detail/" + newsId;
    }

    // 댓글 수정
    @PostMapping("/update")
    public String update(NewsCommentDTO comment) {
        service.updateComment(comment);
        return "redirect:/news/detail/" + comment.getNewsId();
    }

    // 댓글 삭제
    @GetMapping("/delete/{id}/{newsId}")
    public String delete(@PathVariable Long id, @PathVariable Long newsId) {
        service.deleteComment(id);
        return "redirect:/news/detail/" + newsId;
    }
}