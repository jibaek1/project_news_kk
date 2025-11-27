package com.ssr.newskuku._global.aop;

import com.ssr.newskuku.domain.notification.dto.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Aspect
public class NotificationAspect {

    private final ApplicationEventPublisher eventPublisher;

    // 1. 댓글 생성 시 PUSH 알림
    @Pointcut("execution(* com.ssr.newskuku.domain.comment.service.CommentService.createComment(..))")
    public void commentCreation() {}

    @AfterReturning(pointcut = "commentCreation()")
    public void afterCommentCreation(JoinPoint joinPoint) {
        Long authorId = 1L; // 가상: 실제로는 joinPoint.getArgs() 등을 통해 게시글 작성자 ID 추출
        String message = "새로운 댓글이 달렸습니다.";
        eventPublisher.publishEvent(new NotificationEvent(authorId, message, "PUSH", false));
    }

    // 2. 공지사항 등록 시 PUSH 알림
    @Pointcut("execution(* com.ssr.newskuku.domain.notice.service.NoticeService.createNotice(..))")
    public void noticeCreation() {}

    @AfterReturning(pointcut = "noticeCreation()")
    public void afterNoticeCreation(JoinPoint joinPoint) {
        Long targetUserId = 2L; // 가상: 모든 사용자에게 보낼 경우 별도 로직 필요
        String message = "새로운 공지사항이 등록되었습니다.";
        eventPublisher.publishEvent(new NotificationEvent(targetUserId, message, "PUSH", false));
    }

    // 3. 이슈 좋아요 100개 달성 시 PUSH 알림
    @Pointcut("execution(* com.ssr.newskuku.domain.issue.service.IssueService.addLike(..))")
    public void issueLike() {}

    @AfterReturning(pointcut = "issueLike()", returning = "likeCount")
    public void afterIssueLike(JoinPoint joinPoint, Object likeCount) {
        if (likeCount instanceof Integer && (Integer) likeCount == 10) {
            Long authorId = 3L; // 가상: joinPoint.getArgs() 등을 통해 이슈 작성자 ID 추출
            String message = "당신의 이슈가 10개의 좋아요를 받았습니다!";
            eventPublisher.publishEvent(new NotificationEvent(authorId, message, "PUSH", false));
        }
    }
}
