<%@ include file="/WEB-INF/layout/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
                            onclick="location.href='/community/edit?communityId=${community.communityId}'">
                        수정
                    </button>

                    <form method="POST" action="/community/delete/${community.communityId}" style="display:inline;">
                        <button type="submit" class="btn btn-outline-danger"
                                onclick="return confirm('정말 삭제하시겠습니까?');">
                            삭제
                        </button>
                    </form>
                </div>
            </div>

        </div>
    </div>

    <%@ include file="/WEB-INF/layout/footer.jsp" %>
</div>