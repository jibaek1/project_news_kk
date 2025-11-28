<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인</title>

    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #f7f7f7;
        }

        .login-wrapper {
            min-height: 100vh;
        }

        .login-card {
            width: 380px;
            border-radius: 16px;
        }

        .social-btn {
            transition: transform 0.2s;
            text-decoration: none;
        }

        .social-btn:hover {
            transform: translateY(-2px);
        }

        /* 구글 로그인 버튼 */
        .google-btn {
            background-color: #ffffff;
            border: 1px solid #dadce0;
            color: #3c4043;
            padding: 12px 16px;
            border-radius: 4px;
            font-size: 14px;
            font-weight: 500;
        }

        .google-btn:hover {
            background-color: #f8f9fa;
            border-color: #d2d2d2;
        }

        .google-btn img {
            width: 18px;
            height: 18px;
            margin-right: 24px;
        }

        /* 네이버 로그인 버튼 */
        .naver-btn {
            background-color: #03C75A;
            border: none;
            color: #ffffff;
            padding: 0;
            border-radius: 4px;
            font-size: 14px;
            font-weight: 700;
            height: 50px;
            margin-right: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .naver-btn:hover {
            background-color: #02b350;
        }

        /* 네이버 N 로고 (CSS로 구현) */
        .naver-logo {
            width: 20px;
            height: 20px;
            background-color: white;
            border-radius: 2px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            margin-right: 8px;
            font-weight: 900;
            font-size: 16px;
            color: #03C75A;
            font-family: Arial, sans-serif;
        }
    </style>
</head>
<body>

<div class="login-wrapper d-flex justify-content-center align-items-center">

    <div class="login-card card p-4 shadow">

        <!-- 로고 영역 -->
        <div class="text-center mb-4">
            <img src="/img/newsKuKu_Logo.png" alt="NewsKuKu Logo" width="60">
            <h4 class="mt-3 fw-bold">NewsKuKu</h4>
        </div>

        <!-- 안내문 -->
        <p class="text-center text-muted mb-4" style="font-size: 0.9rem;">
            소셜 계정으로 간편하게 로그인하세요
        </p>

        <!-- ======================= 구글 로그인 ======================= -->
        <a href="${pageContext.request.contextPath}/oauth2/authorization/google"
           class="btn google-btn social-btn mb-3 d-flex align-items-center justify-content-center w-100">
            <img src="https://www.gstatic.com/firebasejs/ui/2.0.0/images/auth/google.svg" alt="Google Logo">
            <span>Google 계정으로 로그인</span>
        </a>

        <!-- ======================= 네이버 로그인 ======================= -->
        <a href="${pageContext.request.contextPath}/oauth2/authorization/naver"
           class="btn naver-btn social-btn w-100">
            <span class="naver-logo">N</span>
            <span>Naver 계정으로 로그인</span>
        </a>

    </div>

</div>

</body>
</html>