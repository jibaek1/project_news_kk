<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="kr">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <title>News for everyone - NewsKuKu</title>

    <link rel="icon" type="image/x-icon" href="/bootstrap/assets/favicon.ico" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
    <link href="/bootstrap/css/styles.css" rel="stylesheet" />
</head>

<style>
    html, body { height: 100%; }
    .page-wrapper { min-height: 100vh; display: flex; flex-direction: column; }
    .page-content { flex: 1; }
    .menu-center a { font-size: 1.1rem; font-weight: 600; }
</style>

<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container px-4 px-lg-5">

        <a class="navbar-brand d-flex align-items-center" href="/">
            <img src="/img/newsKuKu_Logo.png" alt="logo" width="32" height="32" class="me-2">
            <span class="fw-bold">NewsKuKu</span>
        </a>

        <!-- 모바일 햄버거 -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- 펼쳐지는 메뉴 -->
        <div class="collapse navbar-collapse" id="navbarSupportedContent">

            <div class="menu-center d-flex justify-content-evenly flex-grow-1">
                <a class="nav-link mx-2" href="/news">기사거리</a>
                <a class="nav-link mx-2" href="/notice">공지사항</a>
            </div>
        </div>

        <!-- 우측 메뉴 -->
        <div class="d-flex align-items-center">

            <sec:authorize access="isAuthenticated()">
                <sec:authentication property="principal" var="principal"/>

                <!-- 닉네임 안전 처리 -->
                <c:choose>
                    <c:when test="${not empty principal.attributes.oauthNickname}">
                        <a href="/regist" class="navbar-text me-3"
                            style="text-decoration: underline; color: inherit;">
                            ${principal.attributes.oauthNickname}님
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="/regist" class="navbar-text me-3"
                            style="text-decoration: underline; color: inherit;">
                            사용자님
                        </a>
                    </c:otherwise>
                </c:choose>

                <!-- 알림 -->
                <a href="/notifications" class="btn btn-outline-dark me-3 position-relative">
                    <i class="bi bi-bell"></i>

                    <c:if test="${unreadCount > 0}">
                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                            ${unreadCount}
                        </span>
                    </c:if>
                </a>

               <!-- 드롭다운 -->
               <div class="dropdown">
                   <button class="btn btn-outline-secondary" id="dropdownMenuButton"
                           data-bs-toggle="dropdown" aria-expanded="false">
                       <i class="bi bi-list"></i>
                   </button>

                   <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">

                       <!-- 북마크 보기 (통일된 UI + 새창) -->
                       <li>
                           <a class="dropdown-item"
                              href="/bookmark/findAllBookMark?userId=${userId}"
                              onclick="window.open(this.href, 'bookmarkList', 'width=500,height=700'); return false;">
                               북마크 보기
                           </a>
                       </li>

                       <!-- 내 정보 수정 -->
                       <li><a class="dropdown-item" href="/regist">내 정보 수정</a></li>

                       <li><hr class="dropdown-divider"></li>

                       <!-- 로그아웃 -->
                       <li><a class="dropdown-item" href="/logout">로그아웃</a></li>
                   </ul>
               </div>


            </sec:authorize>

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
     <!-- 🔥 햄버거 버튼 작동을 위한 Bootstrap JS (추가됨) -->
        <!-- 변경됨: 반드시 body 끝부분에 위치해야 collapse 정상 작동 -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>

</html>
