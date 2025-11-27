<%@ include file="/WEB-INF/layout/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="page-wrapper">
    <div class="page-content">
        <div class="container mt-5">
            <div class="card shadow-sm p-4 mb-5">
                <h2 class="fw-bold mb-4">✏️ 게시글 수정</h2>

                <!-- 수정 폼 -->
                <form method="POST" action="/community/edit">
                    <!-- 게시글 ID (숨겨진 필드) -->
                    <input type="hidden" name="communityId" value="${community.communityId}">

                    <!-- 제목 입력 -->
                    <div class="mb-3">
                        <label for="title" class="form-label">제목</label>
                        <input type="text" class="form-control" id="title" name="title" value="${community.title}" required>
                    </div>

                    <!-- 태그 입력 -->
                    <div class="mb-3">
                        <label for="tag" class="form-label">태그</label>
                        <input type="text" class="form-control" id="tag" name="tag" value="${community.tag}" placeholder="태그를 입력하세요" required>
                    </div>

                    <!-- 본문 입력 -->
                    <div class="mb-3">
                        <label for="content" class="form-label">내용</label>
                        <textarea class="form-control" id="content" name="content" rows="10" required>${community.content}</textarea>
                    </div>

                    <!-- 버튼 -->
                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary">💾 저장하기</button>
                        <button type="button" class="btn btn-secondary" onclick="history.back()">취소</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <%@ include file="/WEB-INF/layout/footer.jsp" %>
</div>