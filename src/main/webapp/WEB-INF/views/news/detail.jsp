<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--  상단 헤더/네브 --%>
<%@ include file="/WEB-INF/layout/header.jsp" %>

<!-- Section-->
<section class="py-5">
    <div class="container px-4 px-lg-5 mt-5">

        <!-- 뒤로가기 버튼 -->
        <div class="mb-4">
            <a href="/news" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left"></i> 목록으로
            </a>
        </div>

        <!-- 좌우 분할 레이아웃 -->
        <div class="row g-5">

            <!-- 왼쪽: 이미지만 -->
            <div class="col-lg-5">
                <div class="sticky-top" style="top: 100px;">
                    <!-- 썸네일 이미지 -->
                    <c:if test="${not empty news.thumbnail}">
                        <img src="${news.thumbnail}"
                             class="img-fluid rounded shadow-lg w-100"
                             alt="${news.title}"
                             style="object-fit: cover;">
                    </c:if>

                    <!-- 이미지 없을 때 -->
                    <c:if test="${empty news.thumbnail}">
                        <div class="bg-light rounded shadow-lg d-flex align-items-center justify-content-center"
                             style="height: 500px;">
                            <div class="text-center text-muted">
                                <i class="bi bi-image" style="font-size: 5rem;"></i>
                                <p class="mt-3 fs-5">이미지가 없습니다</p>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>

            <!-- 오른쪽: 모든 내용 -->
            <div class="col-lg-7">
                <!-- 제목 -->
                <h1 class="display-5 fw-bold mb-3" style="line-height: 1.3;">
                    ${news.title}
                </h1>

                <!-- 메타 정보 (작성일, 수정일, 카테고리, 원문링크) -->
                <div class="d-flex flex-wrap gap-3 mb-4 text-muted align-items-center">
                    <span>
                        <i class="bi bi-calendar3"></i> ${news.createdAt}
                    </span>
                    <span>
                        <i class="bi bi-pencil"></i> ${news.modifiedAt}
                    </span>
                    <span>
                        <span class="badge bg-secondary">${news.category}</span>
                    </span>
                    <span>
                        <i class="bi bi-link-45deg"></i>
                        <a href="${news.url}" target="_blank" class="text-decoration-none">원문 보기</a>
                    </span>
                </div>

                <hr class="my-4">

                <!-- AI 요약 섹션 -->
                <div class="card border-primary shadow-sm mb-4">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">
                            <i class="bi bi-stars"></i> AI 요약
                        </h5>
                    </div>
                    <div class="card-body" style="background: linear-gradient(135deg, #f8f9ff 0%, #fff 100%);">
                        <c:choose>
                            <c:when test="${not empty news.summary}">
                                <p class="fs-5 lh-lg mb-0" style="white-space: pre-line; color: #2c3e50;">
                                        ${news.summary}
                                </p>
                            </c:when>
                            <c:otherwise>
                                <div class="text-center text-muted py-4">
                                    <i class="bi bi-hourglass-split fs-2"></i>
                                    <p class="mt-2 mb-0">요약이 아직 생성되지 않았습니다.</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <!-- 추가 정보 -->
                <div class="alert alert-info">
                    <i class="bi bi-info-circle"></i>
                    이 기사는 AI가 자동으로 요약한 내용입니다.
                    정확한 내용은 원문을 확인해주세요.
                </div>

            </div>
        </div>

    </div>
</section>

<style>
    /* 스크롤 시 왼쪽 이미지 고정 */
    @media (min-width: 992px) {
        .sticky-top {
            position: sticky;
        }
    }

    /* 모바일에서는 일반 레이아웃 */
    @media (max-width: 991px) {
        .sticky-top {
            position: relative !important;
        }
    }

    /* 카드 호버 효과 */
    .card {
        transition: transform 0.2s;
    }

    .card:hover {
        transform: translateY(-2px);
    }

    /* 제목 스타일 */
    h1 {
        color: #1a1a1a;
    }
</style>

<%--  하단 푸터 --%>
<%@ include file="/WEB-INF/layout/footer.jsp" %>