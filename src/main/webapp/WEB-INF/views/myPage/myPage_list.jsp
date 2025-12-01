<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/layout/header.jsp" %>

<style>
    .mypage-wrapper {
        min-height: calc(100vh - 120px);
        height: 40%;
    }

    .mypage-sidebar {
        width: 220px;
        background: #f8f9fa;
        border-right: 1px solid #dee2e6;
        padding: 20px;

        display: flex;
        flex-direction: column;
        justify-content: space-between; /* 핵심 */
        height: 100%;                   /* 핵심 */
    }

    .sidebar-item {
        padding: 12px 16px;
        cursor: pointer;
        border-radius: 8px;
        margin-bottom: 6px;
        color: #333;
        transition: 0.15s;
        font-weight: 500;
    }

    .sidebar-item:hover {
        background-color: #e9ecef;
    }

    .sidebar-item.active {
        background-color: #0d6efd;
        color: white;
    }

    .mypage-content {
        padding: 30px;
        min-height: 500px;
    }
</style>

<div class="container mypage-wrapper d-flex">

    <!-- ====================== 좌측 사이드바 ====================== -->
    <div class="mypage-sidebar">

        <!-- 상단 메뉴 -->
        <div>
            <div class="sidebar-item active">내 프로필</div>
            <div class="sidebar-item">북마크한 기사거리</div>
        </div>

        <!-- 하단 메뉴 -->
        <div>
            <div class="sidebar-item text-danger">로그아웃</div>
        </div>

    </div>

    <!-- ====================== 우측 컨텐츠 영역 ====================== -->
<div class="mypage-content flex-grow-1">

    <!-- 기본 첫 화면은 “내 프로필” -->
    <h4 class="fw-bold mb-4">내 프로필</h4>

    <p class="text-muted">이곳에 내 프로필 정보가 출력될 예정입니다.</p>

    <!-- 여기부터 동적으로 변경될 영역 -->
    <!-- 추후: Ajax로 메뉴 전환 or JSP include 방식 적용 가능 -->

</div>

</div>

<%@ include file="/WEB-INF/layout/footer.jsp" %>
