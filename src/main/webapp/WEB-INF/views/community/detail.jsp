<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>${community.title}</title>
    <style>
        body { font-family: Arial; background: #f5f5f5; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; }
        h1 { color: #333; }
        .tag { background: #667eea; color: white; padding: 5px 12px; border-radius: 20px; font-size: 12px; }
        .meta { font-size: 14px; color: #999; margin: 15px 0; }
        .content { font-size: 16px; line-height: 1.8; margin: 30px 0; color: #333; }
        .btn { background: #667eea; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px; }
        .btn-back { background: #999; }
    </style>
</head>
<body>
    <div class="container">
        <div style="margin-bottom: 20px;">
            <button class="btn btn-back" onclick="history.back()">← 뒤로가기</button>
        </div>

        <span class="tag">${community.tag}</span>
        <h1>${community.title}</h1>
        <div class="meta">
            ${community.username} | ${community.createdAt} | 👁️ ${community.viewCount}
        </div>

        <c:if test="${not empty community.imgUrl}">
            <img src="${community.imgUrl}" style="max-width: 100%; margin: 20px 0; border-radius: 5px;">
        </c:if>

        <div class="content">
            ${community.content}
        </div>

        <hr style="margin: 30px 0; border: none; border-top: 1px solid #ddd;">

        <div>
            <button class="btn" onclick="alert('수정 기능은 준비 중입니다.')">수정</button>
            <button class="btn btn-back" onclick="alert('삭제 기능은 준비 중입니다.')">삭제</button>
        </div>
    </div>
</body>
</html>