<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/layout/header.jsp" %>

<style>
/* 섹션 타이틀 */
.section-title {
    font-weight: 700;
    font-size: 1.5rem;
    display: block;       /* 전체 폭 차지 */
    position: relative;
    margin-bottom: 24px;  /* 타이틀 아래 여백 확보 */
    padding-bottom: 6px;
}

/* 전체 길이 구분선 */
.section-title::after {
    content: "";
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;          /* 전체 라인 */
    height: 2px;
    background-color: #000;
    opacity: 0.15;
}

/* 카드 스타일 + hover 효과 */
.card.custom-card {
    transition: 0.25s;
    border-radius: 12px;
}

.card.custom-card:hover {
    transform: translateY(-6px);
    box-shadow: 0 10px 24px rgba(0,0,0,0.12);
}

</style>

<!-- ====================== 현재 인기있는 게시물 ====================== -->
<div class="page-wrapper">
    <div class="page-content">
        <section class="py-5">
            <div class="container px-4 px-lg-5 mt-5">
                <h3 class="section-title">현재 인기있는 게시물</h3>

                <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-start">

                    <!-- Dummy card -->
                    <div class="col mb-5">
                        <div class="card custom-card h-100 shadow-sm">
                            <img class="card-img-top" src="https://dummyimage.com/450x300/ced4da/6c757d" alt="">
                            <div class="card-body p-4 text-center">
                                <h5 class="fw-bolder">게시물 제목 예시</h5>
                                <p class="text-muted small">게시물 요약 텍스트</p>
                            </div>
                            <div class="text-center pb-3">
                                <a class="btn btn-outline-dark mt-auto" href="#">자세히 보기</a>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </section>


        <!-- ====================== 내 관심사 관련 ====================== -->
        <section class="py-5 bg-light">
            <div class="container px-4 px-lg-5 mt-5">
                <h3 class="section-title">내 관심사와 관련된 게시물</h3>

                <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-start">

                    <div class="col mb-5">
                        <div class="card custom-card h-100 shadow-sm">
                            <img class="card-img-top" src="https://dummyimage.com/450x300/ced4da/6c757d" alt="">
                            <div class="card-body p-4 text-center">
                                <h5 class="fw-bolder">관심사 게시물 예시</h5>
                                <p class="text-muted small">설명 텍스트</p>
                            </div>
                            <div class="text-center pb-3">
                                <a class="btn btn-outline-primary mt-auto" href="#">보러가기</a>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </section>

    </div>

    <%@ include file="/WEB-INF/layout/footer.jsp" %>

</div>