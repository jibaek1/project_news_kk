<%@ include file="/WEB-INF/layout/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style>
    /* 라벨 + 조건 박스 전체 스타일 */
    .filter-row {
        background-color: #f8f9fa; /* Bootstrap 기본 배경과 자연스럽게 맞춤 */
        border: 1px solid #dee2e6;
        border-radius: 12px;
        padding: 14px 18px;
        margin-bottom: 12px;
    }

    /* 라벨 텍스트 */
    .filter-label {
        font-weight: 600;
        margin-right: 10px;
    }

    /* 선택 버튼 */
    .filter-option {
        padding: 5px 14px;
        border: 1px solid #ced4da;
        border-radius: 20px; /* pill style */
        background-color: white;
        cursor: pointer;
        transition: all 0.15s ease-in-out;
        margin-right: 6px;
    }

    /* hover 효과 */
    .filter-option:hover {
        background-color: #e9ecef;
    }

    /* 선택된 값(active) */
    .filter-option.active {
        background-color: #0d6efd;
        color: white;
        border-color: #0d6efd;
    }
</style>

<div class="page-wrapper">
    <div class="page-content">
        <div class="container mt-5">

            <!-- ===================== 페이지 타이틀 ===================== -->
            <div class="text-center my-4">
                <h2 class="fw-bold" style="font-size: 2rem;">기사거리</h2>
            </div>

            <!-- ===================== 1. 카테고리 ===================== -->
            <div class="filter-row d-flex align-items-center flex-wrap">
                <div class="filter-label">카테고리 |</div>
                <div class="filter-option active">전체</div>
                <div class="filter-option">정치</div>
                <div class="filter-option">오락</div>
                <div class="filter-option">경제</div>
                <div class="filter-option">건강</div>
                <div class="filter-option">IT</div>
            </div>

            <!-- ===================== 2. 나이대별 관심사 ===================== -->
            <div class="filter-row d-flex align-items-center flex-wrap">
                <div class="filter-label">나이대별 관심사 |</div>
                <div class="filter-option active">전체</div>
                <div class="filter-option">20대</div>
                <div class="filter-option">30대</div>
                <div class="filter-option">40대</div>
                <div class="filter-option">50대 이상</div>
            </div>

            <!-- ===================== 3. 검색바 ===================== -->
            <div class="search-bar mb-4">
                <form class="d-flex gap-2">
                    <input class="form-control" type="text" placeholder="제목으로 검색" />
                    <button class="btn btn-primary" type="submit">검색</button>
                </form>
            </div>

            <!-- ===================== 4. 기사 리스트 (B 스타일) ===================== -->
            <div class="article-list">
                <c:choose>
                    <c:when test="${not empty news}">
                        <c:forEach var="news" items="${news}">
                            <div class="article-item d-flex mb-4 border-bottom"
                                 onclick="location.href='/news/detail/${news.newsId}'"
                                 style="cursor: pointer;">

                                <!-- 썸네일 (DB 값 없으면 기본 이미지) -->
                                <img src="${empty news.thumbnail ? 'https://dummyimage.com/150x100/ced4da/6c757d' : news.thumbnail}"
                                     class="rounded me-3" alt="thumbnail" width="150" height="100">

                                <!-- 텍스트 영역 -->
                                <div>
                                    <h5 class="fw-bold mb-2">${news.title}</h5>
                                    <p class="text-muted small mb-1">
                                            ${fn:length(news.content) > 80 ? fn:substring(news.content, 0, 80) : news.content}...
                                    </p>
                                    <p class="text-muted small">작성일: ${news.createdAt}</p>
                                    <p class="text-muted small">조회수: ${news.viewCount}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>

                    <c:otherwise>
                        <div class="text-center text-muted py-5">
                            <p>아직 게시글이 없습니다.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/layout/footer.jsp" %>
</div>
