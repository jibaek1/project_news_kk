<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layout/header.jsp" %>
<style>
    body {
        font-family: 'Pretendard', 'Noto Sans KR', sans-serif;
        background-color: #f7f8fa;
        margin: 0;
        padding: 0;
    }

    .notice-container {
        width: 800px;
        margin: 50px auto;
        background: #fff;
        padding: 30px;
        border-radius: 12px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.08);
    }

    .notice-title {
        font-size: 26px;
        font-weight: bold;
        color: #222;
        margin-bottom: 10px;
    }

    .notice-info {
        font-size: 14px;
        color: #777;
        margin-bottom: 25px;
    }

    .notice-info span {
        margin-right: 15px;
    }

    .notice-content {
        font-size: 16px;
        line-height: 1.7;
        color: #333;
        white-space: pre-line; /* 줄바꿈 유지 */
        border-top: 1px solid #eee;
        padding-top: 20px;
        margin-top: 20px;
        padding-bottom: 40px;
    }

    .btn-back {
        display: inline-block;
        padding: 10px 18px;
        background: #4A67D6;
        color: white;
        border-radius: 6px;
        text-decoration: none;
        font-size: 14px;
        transition: all .2s;
    }

    .btn-back:hover {
        background: #364bb6;
    }
</style>

<div class="page-wrapper">
    <div class="page-content">
        <section class="py-5">
            <div class="notice-container">
                <div class="notice-title">${notice.title}</div>
                <div class="notice-info">
                    <span>📅 작성일: ${notice.createdAt}</span>
                    <span>✏ 수정일: ${notice.modifiedAt}</span>
                </div>
                <div class="notice-content">
                    ${notice.content}
                </div>
                <a href="/notice" class="btn-back">← 목록으로 돌아가기</a>
            </div>
        </section>
    </div>
    <%@ include file="/WEB-INF/layout/footer.jsp" %>
</div>
