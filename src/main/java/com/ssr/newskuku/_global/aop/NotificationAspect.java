package com.ssr.newskuku._global.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Aspect
public class NotificationAspect {

    private final ApplicationEventPublisher eventPublisher;



}
