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

        .social-btn img {
            width: 22px;
            margin-right: 10px;
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
        <!-- <a href="/oauth2/authorization/google" -->
        <a href="/regist"
           class="btn btn-light d-flex align-items-center border social-btn mb-3"
           style="padding: 10px; border-radius: 12px;">
            <img src="https://www.gstatic.com/firebasejs/ui/2.0.0/images/auth/google.svg">
            <span class="fw-semibold text-dark">구글 계정으로 로그인</span>
        </a>

        <!-- ======================= 카카오 로그인 ======================= -->
        <!-- <a href="/oauth2/authorization/kakao" -->
        <a href="/regist"
           class="btn d-flex align-items-center social-btn"
           style="background-color:#FEE500; padding: 10px; border-radius: 12px;">
            <img src="https://developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_small.png">
            <span class="fw-semibold" style="color:#3c1e1e;">카카오 계정으로 로그인</span>
        </a>

    </div>

</div>

</body>
</html>
