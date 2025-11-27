<%@ include file="/WEB-INF/layout/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<<<<<<< HEAD:src/main/webapp/WEB-INF/views/community/id.jsp
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>${community.title}</title>
    <style>
        body { font-family: Arial; background: #f5f5f5; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; }
        h1 { color: #333; }
        .tag { background: #667eea; color: white; padding: 5px 12px; border-radius: 20px; font-size: 12px; }
        .meta { font-size: 14px; color: #999; margin: 15px 0; }
        .content { font-size: 16px; line-height: 1.8; margin: 30px 0; color: #333; }
        .btn { background: #667eea; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px; }
        .btn-back { background: #999; }
    </style>
</head>
<body>
    <div class="container">
        <div style="margin-bottom: 20px;">
            <button class="btn btn-back" onclick="location.href='/community'">← 뒤로가기</button>
        </div>
=======
>>>>>>> 2c6784a19448643b0ff9609f3dad8385cb121125:src/main/webapp/WEB-INF/views/community/detail.jsp

<div class="page-wrapper">
    <div class="page-content">
        <div class="container mt-5">

            <!-- 뒤로가기 -->
            <div class="mb-4">
                <button class="btn btn-outline-secondary" onclick="history.back()">
                    <i class="bi bi-arrow-left"></i> 뒤로가기
                </button>
            </div>

            <!-- ===================== 게시글 박스 ===================== -->
            <div class="card shadow-sm p-4 mb-5">

                <!-- 태그 -->
                <span class="badge bg-primary px-3 py-2 mb-3">
                    ${community.tag}
                </span>

                <!-- 제목 -->
                <h2 class="fw-bold mb-3" style="line-height: 1.4;">
                    ${community.title}
                </h2>

                <!-- 메타 정보 -->
                <div class="text-muted small mb-4">
                    <i class="bi bi-person"></i> ${community.username}
                    &nbsp; | &nbsp;
                    <i class="bi bi-calendar3"></i> ${community.createdAt}
                    &nbsp; | &nbsp;
                    <i class="bi bi-eye"></i> ${community.viewCount}
                </div>

                <!-- 이미지 -->
                <c:if test="${not empty community.imgUrl}">
                    <img src="${community.imgUrl}"
                         class="img-fluid rounded mb-4 shadow-sm"
                         style="max-height: 400px; object-fit: cover;"
                    >
                </c:if>

                <!-- 내용 -->
                <div class="fs-5 lh-lg" style="white-space: pre-line; color:#333;">
                    ${community.content}
                </div>

                <hr class="my-4">

                <!-- 버튼 -->
                <div class="d-flex gap-2">
                    <button class="btn btn-primary"
                            onclick="alert('수정 기능은 준비 중입니다.')">
                        수정
                    </button>

                    <button class="btn btn-outline-danger"
                            onclick="alert('삭제 기능은 준비 중입니다.')">
                        삭제
                    </button>
                </div>
            </div>

<<<<<<< HEAD:src/main/webapp/WEB-INF/views/community/id.jsp
        <div>
            <button class="btn" onclick="location.href='/community/edit?communityId=${community.communityId}'">수정</button>

            <form method="POST" action="/community/delete/${community.communityId}" style="display:inline;">
                <button type="submit" class="btn btn-back" onclick="return confirm('정말 삭제하시겠습니까?');">
                    삭제
                </button>
            </form>
=======
>>>>>>> 2c6784a19448643b0ff9609f3dad8385cb121125:src/main/webapp/WEB-INF/views/community/detail.jsp
        </div>
    </div>

    <%@ include file="/WEB-INF/layout/footer.jsp" %>
</div>
