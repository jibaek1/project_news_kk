<%@ include file="/WEB-INF/layout/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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

                <!-- 이 부분은 DB에서 불러와서 반복문으로 변환될 예정 -->
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

                <!-- 1개 기사 -->
                <div class="article-item d-flex mb-4 border-bottom">

                    <!-- 썸네일 -->
                    <img src="https://dummyimage.com/150x100/ced4da/6c757d"
                         class="rounded me-3" alt="thumbnail" width="150" height="100">

                    <!-- 텍스트 영역 -->
                    <div>
                        <h5 class="fw-bold mb-2">기사 제목 예시</h5>
                        <p class="text-muted small mb-1">
                            기사 요약 텍스트가 여기에 들어갑니다. 여러 줄이 들어갈 수도 있어요.
                        </p>
                        <p class="text-muted small">작성일: 2025-01-01</p>
                        <p class="text-muted small">조회수: 100</p>
                    </div>
                </div>

                <!-- 복사해서 무한 리스트 UI 구성 -->
                <div class="article-item d-flex mb-4 border-bottom">
                    <img src="https://dummyimage.com/150x100/ced4da/6c757d" class="rounded me-3"
                         width="150" height="100">
                    <div>
                        <h5 class="fw-bold mb-2">두 번째 기사 제목</h5>
                        <p class="text-muted small mb-1">
                            두 번째 기사 요약 텍스트입니다.
                        </p>
                        <p class="text-muted small">작성일: 2025-01-03</p>
                        <p class="text-muted small">조회수: 100</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/layout/footer.jsp" %>
</div>
