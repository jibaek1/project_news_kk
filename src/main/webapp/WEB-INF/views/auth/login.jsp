<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인 - NewSkuku</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login-container {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            padding: 60px 50px;
            width: 100%;
            max-width: 450px;
        }

        .login-header {
            text-align: center;
            margin-bottom: 40px;
        }

        .login-header h1 {
            color: #333;
            font-size: 32px;
            margin-bottom: 10px;
        }

        .login-header p {
            color: #666;
            font-size: 14px;
        }

        .error-message {
            background: #fee;
            border: 1px solid #fcc;
            color: #c33;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 25px;
            font-size: 14px;
        }

        .oauth-buttons {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .oauth-button {
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 15px 20px;
            border: none;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
        }

        .oauth-button:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        }

        .oauth-button img {
            width: 24px;
            height: 24px;
            margin-right: 12px;
        }

        .google-login {
            background: white;
            border: 2px solid #e0e0e0;
            color: #555;
        }

        .google-login:hover {
            border-color: #4285f4;
            background: #f8f9fa;
        }

        .naver-login {
            background: #03c75a;
            color: white;
        }

        .naver-login:hover {
            background: #02b350;
        }

        .divider {
            text-align: center;
            margin: 30px 0;
            position: relative;
        }

        .divider::before {
            content: '';
            position: absolute;
            top: 50%;
            left: 0;
            right: 0;
            height: 1px;
            background: #e0e0e0;
        }

        .divider span {
            background: white;
            padding: 0 15px;
            color: #999;
            font-size: 14px;
            position: relative;
        }

        .footer-links {
            text-align: center;
            margin-top: 30px;
        }

        .footer-links a {
            color: #667eea;
            text-decoration: none;
            font-size: 14px;
            margin: 0 10px;
        }

        .footer-links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-header">
            <h1>NewSkuku</h1>
            <p>소셜 계정으로 간편하게 로그인하세요</p>
        </div>

        <c:if test="${param.error}">
            <div class="error-message">
                ⚠️ 로그인에 실패했습니다.
                <c:if test="${not empty param.message}">
                    <br>${param.message}
                </c:if>
            </div>
        </c:if>

        <div class="oauth-buttons">
            <!-- Google 로그인 -->
            <a href="${pageContext.request.contextPath}/oauth2/authorization/google" class="oauth-button google-login">
                <svg width="24" height="24" viewBox="0 0 24 24">
                    <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
                    <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
                    <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
                    <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
                </svg>
                Google로 계속하기
            </a>

            <!-- Naver 로그인 -->
            <a href="${pageContext.request.contextPath}/oauth2/authorization/naver" class="oauth-button naver-login">
                <svg width="24" height="24" viewBox="0 0 24 24">
                    <path fill="#FFFFFF" d="M16.273 12.845L7.376 0H0v24h7.726V11.156L16.624 24H24V0h-7.727v12.845z"/>
                </svg>
                Naver로 계속하기
            </a>
        </div>

        <div class="divider">
            <span>또는</span>
        </div>

        <div class="footer-links">
            <a href="${pageContext.request.contextPath}/">홈으로 돌아가기</a>
        </div>
    </div>
</body>
</html>