<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ include file="/WEB-INF/layout/header.jsp" %>

<style>
    .filter-row {
        background-color: #f8f9fa;
        border: 1px solid #dee2e6;
        border-radius: 12px;
        padding: 14px 18px;
        margin-bottom: 12px;
    }

    .filter-label {
        font-weight: 600;
        margin-right: 10px;
    }

    .filter-option {
        padding: 5px 14px;
        border: 1px solid #ced4da;
        border-radius: 20px;
        background-color: white;
        cursor: pointer;
        transition: all 0.15s ease-in-out;
        margin-right: 6px;
    }

    .filter-option:hover {
        background-color: #e9ecef;
    }

    .filter-option.active {
        background-color: #0d6efd;
        color: white;
        border-color: #0d6efd;
    }

    .article-item:hover {
        background-color: #fafafa;
    }
</style>

<div class="page-wrapper">
    <div class="page-content">
        <div class="container mt-5">

            <!-- 타이틀 -->
            <div class="text-center my-4">
                <h2 class="fw-bold" style="font-size: 2rem;">커뮤니티</h2>
            </div>

            <!-- 글쓰기 버튼 -->
            <div class="text-end mb-4">
                <button class="btn btn-primary" onclick="location.href='/community/write'">✏️ 글쓰기</button>
            </div>

            <!-- 1. 카테고리 -->
            <div class="filter-row d-flex align-items-center flex-wrap">
                <div class="filter-label">카테고리 |</div>

                <div class="filter-option active">전체</div>
                <div class="filter-option">자유</div>
                <div class="filter-option">Q&A</div>
                <div class="filter-option">정보</div>
                <div class="filter-option">유머</div>
            </div>

            <!-- 2. 나이대 -->
            <div class="filter-row d-flex align-items-center flex-wrap">
                <div class="filter-label">나이대별 관심사 |</div>

                <div class="filter-option active">전체</div>
                <div class="filter-option">20대</div>
                <div class="filter-option">30대</div>
                <div class="filter-option">40대</div>
                <div class="filter-option">50대 이상</div>
            </div>

            <!-- 3. 검색 -->
            <div class="search-bar mb-4">
                <form method="get" action="/community" class="d-flex gap-2">
                    <input class="form-control" type="text" name="keyword" placeholder="제목으로 검색" value="${keyword}">
                    <button class="btn btn-primary" type="submit">검색</button>
                </form>
            </div>

            <!-- 검색 결과 표시 -->
            <c:if test="${not empty keyword}">
                <div class="alert alert-info d-flex justify-content-between align-items-center">
                    <span>'<strong>${keyword}</strong>' 검색 결과</span>
                    <a href="/community" class="btn btn-sm btn-outline-secondary">전체 보기</a>
                </div>
            </c:if>

            <!-- 4. 커뮤니티 리스트 -->
            <div class="article-list">

                <c:choose>
                    <c:when test="${not empty communities}">
                        <c:forEach var="post" items="${communities}">
                            <div class="article-item d-flex mb-4 border-bottom pb-3"
                                 onclick="location.href='/community/detail?communityId=${post.communityId}'"
                                 style="cursor: pointer;">

                                <!-- 텍스트 -->
                                <div>
                                    <h5 class="fw-bold mb-2">${post.title}</h5>

                                    <p class="text-muted small mb-1">
                                            ${fn:length(post.content) > 80 ? fn:substring(post.content, 0, 80) : post.content}...
                                    </p>

                                    <p class="text-muted small mb-0">
                                        작성자: ${post.username != null ? post.username : '익명'} |
                                        작성일: ${post.createdAt} |
                                        조회수: ${post.viewCount}
                                    </p>
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

            <!-- 5. 페이징 -->
            <c:if test="${not empty pageLinks && fn:length(pageLinks) > 0}">
                <nav aria-label="Page navigation" class="mt-4">
                    <ul class="pagination justify-content-center">
                        <!-- 이전 버튼 -->
                        <c:if test="${currentPage > 0}">
                            <li class="page-item">
                                <a class="page-link" href="/community?page=${currentPage - 1}<c:if test='${not empty keyword}'>&keyword=${keyword}</c:if>">이전</a>
                            </li>
                        </c:if>

                        <!-- 페이지 번호들 -->
                        <c:forEach items="${pageLinks}" var="link">
                            <li class="page-item ${link.current ? 'active' : ''}">
                                <a class="page-link" href="/community?page=${link.index}<c:if test='${not empty keyword}'>&keyword=${keyword}</c:if>">
                                        ${link.number}
                                </a>
                            </li>
                        </c:forEach>

                        <!-- 다음 버튼 -->
                        <c:if test="${currentPage < fn:length(pageLinks) - 1}">
                            <li class="page-item">
                                <a class="page-link" href="/community?page=${currentPage + 1}<c:if test='${not empty keyword}'>&keyword=${keyword}</c:if>">다음</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:if>

        </div>
    </div>
</div>

<%@ include file="/WEB-INF/layout/footer.jsp" %>