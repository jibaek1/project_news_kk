<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/layout/header.jsp" %>

<div class="d-flex flex-column min-vh-100">
    <!-- Section -->
    <section class="py-5 flex-grow-1">
        <div class="container px-4 px-lg-5 mt-5">
            <!-- 뒤로가기 버튼 -->
            <div class="mb-4">
                <a href="/news" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left"></i> 목록으로
                </a>
            </div>

            <!-- 좌우 분할 레이아웃 -->
            <div class="row g-5">
                <!-- 왼쪽 이미지 -->
                <div class="col-lg-5">
                    <div class="sticky-top" style="top: 100px;">
                        <c:choose>
                            <c:when test="${not empty news.thumbnail}">
                                <img src="${news.thumbnail}" class="img-fluid rounded shadow-lg w-100" alt="${news.title}" style="object-fit: cover;">
                            </c:when>
                            <c:otherwise>
                                <div class="bg-light rounded shadow-lg d-flex align-items-center justify-content-center" style="height: 500px;">
                                    <div class="text-center text-muted">
                                        <i class="bi bi-image" style="font-size: 5rem;"></i>
                                        <p class="mt-3 fs-5">이미지가 없습니다</p>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <!-- 오른쪽 내용 -->
                <div class="col-lg-7">
                    <!-- 제목과 북마크 버튼 -->
                    <div class="d-flex justify-content-between align-items-start mb-3">
                        <h1 class="display-5 fw-bold" style="line-height: 1.3; flex: 1;">
                            ${news.title}
                        </h1>

                        <!-- 북마크 버튼 -->
                        <button id="bookmarkBtn"
                                class="btn ${isBookmarked ? 'btn-warning' : 'btn-outline-warning'} btn-lg ms-3"
                                style="min-width: 50px;"
                                onclick="toggleBookmark(${news.newsId})">
                            <i id="bookmarkIcon" class="bi bi-bookmark${isBookmarked ? '-fill' : ''}"></i>
                        </button>
                    </div>

                    <!-- 메타 정보 -->
                    <div class="d-flex flex-wrap gap-3 mb-4 text-muted align-items-center">
                        <span><i class="bi bi-calendar3"></i> ${news.createdAt}</span>
                        <span><i class="bi bi-pencil"></i> ${news.modifiedAt}</span>
                        <span><i class="bi bi-eye"></i> ${news.viewCount}</span>
                        <span><span class="badge bg-secondary">${news.category}</span></span>
                        <span><i class="bi bi-link-45deg"></i>
                            <a href="${news.url}" target="_blank" class="text-decoration-none">원문 보기</a>
                        </span>
                    </div>

                    <hr class="my-4">

                    <!-- AI 요약 -->
                    <div class="card border-primary shadow-sm mb-4">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0"><i class="bi bi-stars"></i> AI 요약</h5>
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

                    <div class="alert alert-info">
                        <i class="bi bi-info-circle"></i>
                        이 기사는 AI가 자동으로 요약한 내용입니다.
                        정확한 내용은 원문을 확인해주세요.
                    </div>
                </div>
            </div>
        </div>
    </section>

    <%@ include file="/WEB-INF/layout/footer.jsp" %>
</div>

<script>
    function toggleBookmark(newsId) {
        const btn = document.getElementById('bookmarkBtn');
        const icon = document.getElementById('bookmarkIcon');

        fetch('/news/bookmark/' + newsId, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                if (data.success === false) {
                    alert(data.message || '북마크 처리 중 오류가 발생했습니다.');
                    return;
                }

                // 북마크 상태에 따라 UI 변경
                if (data.bookmarked) {
                    icon.className = 'bi bi-bookmark-fill';
                    btn.classList.remove('btn-outline-warning');
                    btn.classList.add('btn-warning');
                } else {
                    icon.className = 'bi bi-bookmark';
                    btn.classList.remove('btn-warning');
                    btn.classList.add('btn-outline-warning');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('북마크 처리 중 오류가 발생했습니다.');
            });
    }
</script>

<style>
    /* 스크롤 시 왼쪽 이미지 고정 */
    @media (min-width: 992px) {
        .sticky-top { position: sticky; }
    }
    @media (max-width: 991px) {
        .sticky-top { position: relative !important; }
    }
    /* 카드 호버 효과 */
    .card { transition: transform 0.2s; }
    .card:hover { transform: translateY(-2px); }
    /* 제목 색상 */
    h1 { color: #1a1a1a; }
    /* 북마크 버튼 효과 */
    #bookmarkBtn {
        transition: all 0.3s ease;
    }
    #bookmarkBtn:hover {
        transform: scale(1.1);
    }
</style>