<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--  상단 헤더/네브 --%>
<%@ include file="/WEB-INF/layout/header.jsp" %>

<!-- Section-->
<section class="py-5">
    <div class="container px-4 px-lg-5 mt-5">

        <h2 class="mb-4">뉴스 상세보기</h2>

        <div class="row gx-4 gx-lg-5 justify-content-center">
            <table border="1" cellspacing="0" cellpadding="8">
                <tbody>
                <tr>
                    <th>ID</th>
                    <td>${news.newsId}</td>
                </tr>

                <tr>
                    <th>제목</th>
                    <td>${news.title}</td>
                </tr>

                <!-- AI 요약 표시 추가 -->
                <tr>
                    <th>AI 요약</th>
                    <td style="white-space: pre-line;">
                        <c:choose>
                            <c:when test="${not empty news.summary}">
                                ${news.summary}
                            </c:when>
                            <c:otherwise>
                                <span style="color: #999;">요약이 아직 생성되지 않았습니다.</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>

                <tr>
                    <th>내용</th>
                    <td style="white-space: pre-line;">${news.content}</td>
                </tr>

                <tr>
                    <th>원문 링크</th>
                    <td>
                        <a href="${news.url}" target="_blank">기사 원문 보기</a>
                    </td>
                </tr>

                <tr>
                    <th>생성일</th>
                    <td>${news.createdAt}</td>
                </tr>

                <tr>
                    <th>수정일</th>
                    <td>${news.modifiedAt}</td>
                </tr>
                </tbody>
            </table>
        </div>

        <!-- AI 요약 버튼 -->
        <div class="mt-2 mb-4">
            <!-- 요약이 없을 때만 버튼 표시 -->
            <c:if test="${empty news.summary}">
                <form action="/news/${news.newsId}/summarize" method="post" style="display:inline;">
                    <button type="submit" class="btn btn-primary">AI 요약 생성</button>
                </form>
            </c:if>

            <!-- 요약이 있을 때는 재생성 버튼 표시 -->
            <c:if test="${not empty news.summary}">
                <form action="/news/${news.newsId}/summarize" method="post" style="display:inline;">
                    <button type="submit" class="btn btn-warning">AI 요약 재생성</button>
                </form>
            </c:if>
        </div>

        <!-- 뒤로가기 버튼 -->
        <div class="mt-4">
            <a href="/news" class="btn btn-secondary">← 목록으로</a>
        </div>

    </div>
</section>

<%--  하단 푸터 --%>
<%@ include file="/WEB-INF/layout/footer.jsp" %>