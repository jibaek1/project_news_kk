<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>커뮤니티</title>
    <style>
        body { font-family: Arial; background: #f5f5f5; padding: 20px; }
        .container { max-width: 900px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; }

        /* 헤더 스타일 */
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .header h1 {
            margin: 0;
            color: #333;
        }

        .post { padding: 15px; margin: 15px 0; border: 1px solid #ddd; border-radius: 5px; cursor: pointer; }
        .post:hover { background: #f9f9f9; }
        .post-title { font-size: 18px; font-weight: bold; color: #667eea; }
        .post-meta { font-size: 12px; color: #999; margin: 10px 0; }
        .tag { background: #667eea; color: white; padding: 3px 8px; border-radius: 3px; font-size: 12px; }
        .btn { background: #667eea; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; }
        .empty { text-align: center; color: #999; padding: 40px; }
    </style>
</head>
<body>
    <div class="container">
        <!-- 헤더: 커뮤니티 (가운데), 글쓰기 버튼 (오른쪽) -->
        <div class="header">
            <h1>📰 커뮤니티</h1>
            <button class="btn" onclick="location.href='/community/write'">✏️ 글쓰기</button>
        </div>

        <c:choose>
            <c:when test="${not empty communities}">
                <c:forEach var="post" items="${communities}">
                    <div class="post" onclick="location.href='/community/id?communityId=${post.communityId}'"
                        <span class="tag">${post.tag}</span>
                        <div class="post-title">${post.title}</div>
                        <div class="post-meta">
                            ${post.username} | ${post.createdAt}
                        </div>
                        <div>${fn:substring(post.content, 0, 100)}...</div>
                        <div class="post-meta">👁️ ${post.viewCount}</div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="empty">
                    <p>아직 게시글이 없습니다.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>