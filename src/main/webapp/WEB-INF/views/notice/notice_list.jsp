<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

                    <!-- 이전 버튼 -->
                    <c:choose>
                        <c:when test="${hasPrevious}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${currentPage - 1}">«</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled">
                                <a class="page-link">«</a>
                            </li>
                        </c:otherwise>
                    </c:choose>

                    <!-- 페이지 번호 -->
                    <c:forEach begin="0" end="${totalPages - 1}" var="i">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}">
                                ${i + 1}
                            </a>
                        </li>
                    </c:forEach>

                    <!-- 다음 버튼 -->
                    <c:choose>
                        <c:when test="${hasNext}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${currentPage + 1}">»</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled">
                                <a class="page-link">»</a>
                            </li>
                        </c:otherwise>
                    </c:choose>

                </ul>
            </nav>

        </div>
    </div>

    <%-- 하단 푸터 --%>
    <%@ include file="/WEB-INF/layout/footer.jsp" %>

</div>
