package com.ssr.newskuku._global.advice;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    //private final MenuInfoService menuInfoService;

    //@ModelAttribute("menus")
    //public List<MenuInfo> menus() {
    //    return menuInfoService.getVisibleTrueAllMenus();
    //}

}
