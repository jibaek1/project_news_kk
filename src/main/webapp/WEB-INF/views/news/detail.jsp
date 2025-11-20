<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>


<%--  상단 헤더/네브 --%>
<%@ include file="/WEB-INF/layout/header.jsp" %>

<!-- Section-->
<section class="py-5">
    <div class="container px-4 px-lg-5 mt-5">
        <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
            <table border="1" cellspacing="0" cellpadding="8">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>제목</th>
                    <th>내용</th>
                    <th>조회수</th>
                    <th>노출 여부</th>
                    <th>생성일</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="notice" items="${notices}">
                    <tr>
                        <td>${notice.noticeId}</td>
                        <td>${notice.title}</td>
                        <td>${notice.content}</td>
                        <td>${notice.viewCount}</td>
                        <td>${notice.visible}</td>
                        <td>${notice.createdAt}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</section>

<%--  하단 푸터 --%>
<%@ include file="/WEB-INF/layout/footer.jsp" %>
