<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>내 북마크 목록</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" />

    <style>
        body {
            background: #f7f7f7;
            padding: 20px;
        }

        .bookmark-container {
            max-width: 700px;
            margin: 0 auto;
        }

        .bookmark-card {
            background: #ffffff;
            border-radius: 10px;
            padding: 20px 25px;
            border: 1px solid #e5e5e5;
            margin-bottom: 15px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.04);
        }

        .bookmark-title {
            font-size: 1.1rem;
            font-weight: 600;
            color: #333;
        }

        .bookmark-meta {
            font-size: 0.85rem;
            color: #777;
        }

        .open-btn {
            font-size: 0.85rem;
        }
    </style>
</head>

<body>

<div class="bookmark-container">

    <h3 class="fw-bold mb-4">내 북마크 목록</h3>

    <c:if test="${empty list}">
        <div class="alert alert-warning text-center">
            북마크한 기사가 없습니다.
        </div>
    </c:if>

    <!-- 북마크 리스트 -->
    <c:forEach var="item" items="${list}">

        <div class="bookmark-card">

            <!-- 제목 -->
            <div class="bookmark-title">
                ${item.title}
            </div>

            <div class="bookmark-meta mt-2">
                카테고리: ${item.category} |
                등록일: ${item.createdAt}
            </div>

            <div class="mt-3 d-flex justify-content-end">
                <a href="/news/detail/${item.newsId}"
                   target="_blank"
                   class="btn btn-primary btn-sm open-btn">
                    기사 열기
                </a>
            </div>

        </div>

    </c:forEach>

</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
