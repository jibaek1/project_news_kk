package com.ssr.newskuku.domain.notification.controller;

import com.ssr.newskuku.domain.notification.Notification;
import com.ssr.newskuku.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/list")
    public String list(@RequestParam Long userId, Model model) {
        model.addAttribute("notifications", notificationService.getNotifications(userId));
        model.addAttribute("unread", notificationService.getUnreadCount(userId));
        model.addAttribute("userId", userId);
        return "notification/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id,
                         @RequestParam Long userId) {

        notificationService.deleteNotification(id, userId);

        return "redirect:/notification/list?userId=" + userId;
    }
}
