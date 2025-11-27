<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 수정</title>
    <style>
        body { font-family: Arial; background: #f5f5f5; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; }
        h1 { color: #333; }
        .form-group { margin-bottom: 20px; }
        label { display: block; font-weight: bold; margin-bottom: 8px; color: #333; }
        input, textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; font-family: Arial; }
        textarea { resize: vertical; min-height: 300px; }
        .button-group { margin-top: 30px; }
        .btn { padding: 12px 20px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; margin-right: 10px; }
        .btn-submit { background: #667eea; color: white; }
        .btn-cancel { background: #999; color: white; }
        .btn:hover { opacity: 0.9; }
    </style>
</head>
<body>
    <div class="container">
        <h1>✏️ 게시글 수정</h1>

        <!-- 수정 폼 -->
        <form method="POST" action="/community/edit">
            <!-- 게시글 ID (숨겨진 필드) -->
            <input type="hidden" name="communityId" value="${community.communityId}">

            <!-- 제목 입력 -->
            <div class="form-group">
                <label for="title">제목</label>
                <input type="text" id="title" name="title" value="${community.title}" required>
            </div>

            <!-- 태그 입력 -->
            <div class="form-group">
                <label for="tag">태그</label>
                <input type="text" id="tag" name="tag" value="${community.tag}" placeholder="태그를 입력하세요 (예: AI, 정보, 질문)" required>
            </div>

            <!-- 본문 입력 -->
            <div class="form-group">
                <label for="content">내용</label>
                <textarea id="content" name="content" required>${community.content}</textarea>
            </div>

            <!-- 버튼 -->
            <div class="button-group">
                <button type="submit" class="btn btn-submit">💾 저장하기</button>
                <button type="button" class="btn btn-cancel" onclick="history.back()">취소</button>
            </div>
        </form>
    </div>
</body>
</html>