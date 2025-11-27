<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--  상단 헤더 --%>
<%@ include file="/WEB-INF/layout/header.jsp" %>

<div class="page-wrapper">
    <div class="page-content">

        <div class="container mt-5">

            <!-- ===================== 페이지 타이틀 ===================== -->
            <div class="text-center my-4">
                <h2 class="fw-bold" style="font-size: 2rem;">공지사항</h2>
            </div>

            <!-- ===================== 공지사항 테이블 ===================== -->
            <table class="table table-hover text-center align-middle">
                <thead class="table-light">
                    <tr>
                        <th style="width: 80px;">ID</th>
                        <th>제목</th>
                        <th style="width: 150px;">작성일</th>
                        <th style="width: 80px;">조회수</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="notice" items="${notices}">
                        <tr>
                            <td>${notice.noticeId}</td>

                            <td class="text-center">
                                <a href="/notice/${notice.noticeId}"
                                   class="text-decoration-none fw-bold text-dark">
                                    ${notice.title}
                                </a>
                            </td>
                            <td>${notice.createdAt}</td>
                            <td>${notice.viewCount}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- ===================== 페이징 ===================== -->
            <nav class="mt-4">
                <ul class="pagination justify-content-center">
                    <li class="page-item disabled">
                        <a class="page-link">«</a>
                    </li>

                    <li class="page-item active">
                        <a class="page-link" href="#">1</a>
                    </li>

                    <li class="page-item">
                        <a class="page-link" href="#">2</a>
                    </li>

                    <li class="page-item">
                        <a class="page-link" href="#">3</a>
                    </li>

                    <li class="page-item">
                        <a class="page-link">»</a>
                    </li>
                </ul>
            </nav>

        </div>
    </div>

    <%-- 하단 푸터 --%>
    <%@ include file="/WEB-INF/layout/footer.jsp" %>
</div>
