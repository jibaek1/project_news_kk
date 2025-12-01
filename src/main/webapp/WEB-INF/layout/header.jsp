<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="kr">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>News for everyone - NewsKuKu</title>

    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="/bootstrap/assets/favicon.ico" />
    <!-- Bootstrap icons-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="/bootstrap/css/styles.css" rel="stylesheet" />
</head>

<style>
    html, body {
        height: 100%;
    }
    /* 페이지 전체 레이아웃 */
    .page-wrapper {
        min-height: 100vh;             /* 화면 높이 꽉 채움 */
        display: flex;
        flex-direction: column;
    }
    /* 본문 영역은 자동 확장 */
    .page-content {
        flex: 1;
    }
    .menu-center a {
        font-size: 1.1rem; /* 원하는 크기로 변경 */
        font-weight: 600; /* 필요하면 굵기 조절 */
    }
</style>

<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container px-4 px-lg-5">

            <!-- LEFT: LOGO -->
            <a class="navbar-brand d-flex align-items-center" href="/">
                <img src="/img/newsKuKu_Logo.png" alt="logo" width="32" height="32" class="me-2">
                <span class="fw-bold">NewsKuKu</span>
            </a>

            <!-- Toggle button for mobile -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <!-- CENTER + RIGHT -->
            <div class="collapse navbar-collapse" id="navbarSupportedContent">

                <!-- CENTER MENU (기사거리 / 커뮤니티 / 공지사항) -->
                <div class="menu-center d-flex justify-content-evenly flex-grow-1">
                    <a class="nav-link mx-2" href="/news">기사거리</a>
                    <a class="nav-link mx-2" href="/community">커뮤니티</a>
                    <a class="nav-link mx-2" href="/notice">공지사항</a>
                </div>
            </div>

            <!-- RIGHT: Login 상태에 따라 표시 -->
            <div class="d-flex align-items-center">

                <%-- 1. 로그인 상태일 때 --%>
                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal" var="principal"/>

                    <%-- 사용자 이름 (마이페이지 링크) --%>
                    <a href="/regist" class="navbar-text me-3" style="text-decoration: underline; color: inherit;">
                        ${principal.attributes.oauthNickname}님
                    </a>

                    <%-- 알림 뱃지 --%>
                    <a href="/notifications" class="btn btn-outline-dark me-3 position-relative">
                        <i class="bi bi-bell"></i>
                        <c:if test="${unreadCount > 0}">
                            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                                ${unreadCount}
                                <span class="visually-hidden">unread messages</span>
                            </span>
                        </c:if>
                    </a>

                    <%-- 햄버거 드롭다운 메뉴 --%>
                    <div class="dropdown">
                        <button class="btn btn-outline-secondary" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="bi bi-list"></i>
                        </button>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
                            <li><a class="dropdown-item" href="/my/bookmarks">북마크 보기</a></li>
                            <li><a class="dropdown-item" href="/regist">내 정보 수정</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="/logout">로그아웃</a></li>
                        </ul>
                    </div>
                </sec:authorize>

                <%-- 2. 비로그인 상태일 때 --%>
                <sec:authorize access="isAnonymous()">
                    <a class="btn btn-outline-dark" href="/auth/login">
                        로그인
                    </a>
                </sec:authorize>

            </div>
        </div>
    </nav>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>