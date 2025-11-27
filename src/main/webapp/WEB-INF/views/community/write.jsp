<%@ include file="/WEB-INF/layout/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="page-wrapper">
    <div class="page-content">
        <div class="container mt-5">

            <!-- 뒤로가기 버튼 -->
            <div class="mb-4">
                <button class="btn btn-outline-secondary" onclick="history.back()">
                    <i class="bi bi-arrow-left"></i> 뒤로가기
                </button>
            </div>

            <!-- ===================== 게시글 작성 카드 ===================== -->
            <div class="card shadow-sm p-4 mb-5">

                <h2 class="fw-bold mb-4">✍️ 새 게시글 작성</h2>

                <form method="POST" action="/community/write">

                    <!-- 제목 -->
                    <div class="mb-3">
                        <label for="title" class="form-label">제목</label>
                        <input type="text" id="title" name="title" class="form-control"
                               placeholder="게시글 제목을 입력하세요" required>
                    </div>

                    <!-- 태그 -->
                    <div class="mb-3">
                        <label for="tag" class="form-label">태그</label>
                        <input type="text" id="tag" name="tag" class="form-control"
                               placeholder="태그를 입력하세요 (예: #AI, #정보, #질문)" required>
                    </div>

                    <!-- 내용 -->
                    <div class="mb-3">
                        <label for="content" class="form-label">내용</label>
                        <textarea id="content" name="content" class="form-control"
                                  placeholder="게시글 내용을 입력하세요" required
                                  style="min-height: 300px;"></textarea>
                    </div>

                    <!-- 버튼 -->
                    <div class="d-flex gap-2 mt-4">
                        <button type="submit" class="btn btn-primary">📤 등록하기</button>
                        <button type="button" class="btn btn-outline-secondary" onclick="history.back()">취소</button>
                    </div>

                </form>
            </div>

        </div>
    </div>

    <%@ include file="/WEB-INF/layout/footer.jsp" %>
</div>

