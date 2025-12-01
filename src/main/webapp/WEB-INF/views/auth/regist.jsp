<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원정보 입력</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #f7f7f7;
        }

        .form-wrapper {
            min-height: 100vh;
        }

        .form-card {
            width: 480px;
            border-radius: 16px;
        }

        .profile-thumb {
            width: 90px;
            height: 90px;
            border-radius: 50%;
            object-fit: cover;
            background-color: #e9ecef;
        }

        .category-tag {
            border: 1px solid #ced4da;
            border-radius: 20px;
            padding: 6px 14px;
            cursor: pointer;
            transition: 0.15s;
            background: #fff;
            margin: 4px;
        }

        .category-tag.active {
            background-color: #0d6efd;
            color: #fff;
            border-color: #0d6efd;
        }
    </style>
</head>
<body>

<div class="form-wrapper d-flex justify-content-center align-items-center">

    <div class="form-card card p-4 shadow">

        <!-- 타이틀 -->
        <h3 class="text-center fw-bold mb-4">추가 정보 입력</h3>

        <p class="text-center text-muted mb-4" style="font-size: 0.9rem;">
            서비스 이용을 위해 아래 정보를 입력해주세요.
        </p>

        <form action="/regist" method="post" enctype="multipart/form-data">
            <!-- userId hidden input 추가 -->
            <input type="hidden" name="userId" value="${userId}">

            <!-- 프로필 사진 -->
            <div class="text-center mb-4">
                <img id="profilePreview" src="https://cdn-icons-png.flaticon.com/512/847/847969.png"
                     class="profile-thumb mb-2" alt="Profile Image">

                <div>
                    <label class="btn btn-secondary btn-sm">
                        사진 선택
                        <input type="file" name="profileImage" accept="image/*" hidden
                               onchange="previewImage(event)">
                    </label>
                </div>
            </div>

            <!-- 닉네임 -->
            <div class="mb-3">
                <label class="form-label fw-semibold">닉네임 <span class="text-danger">*</span></label>
                <input type="text" name="nickname" class="form-control"
                       placeholder="닉네임을 입력하세요"
                       value="${nickname != null ? nickname : ''}" required>
            </div>

            <!-- 생년월일 -->
            <div class="mb-3">
                <label class="form-label fw-semibold">생년월일 <span class="text-danger">*</span></label>
                <input type="date" name="birthDate" class="form-control"
                       value="${birthDate != null ? birthDate : ''}" required>
            </div>

            <!-- 성별 -->
            <div class="mb-3">
                <label class="form-label fw-semibold">성별 <span class="text-danger">*</span></label>
                <div class="d-flex gap-3 mt-1">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="gender"
                               value="MALE" id="genderMale"
                               ${gender == 'MALE' ? 'checked' : ''} required>
                        <label class="form-check-label" for="genderMale">남성</label>
                    </div>

                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="gender"
                               value="FEMALE" id="genderFemale"
                               ${gender == 'FEMALE' ? 'checked' : ''} required>
                        <label class="form-check-label" for="genderFemale">여성</label>
                    </div>
                </div>
            </div>

            <!-- 휴대전화번호 -->
            <div class="mb-3">
                <label class="form-label fw-semibold">휴대전화번호</label>
                <input type="text" name="phoneNumber" class="form-control" placeholder="010-1234-5678">
            </div>

            <!-- 관심 카테고리 -->
            <div class="mb-3">
                <label class="form-label fw-semibold d-block mb-2">
                    관심 카테고리 <span class="text-danger">*</span>
                </label>
                <p class="text-muted small">최소 1개 이상 선택해주세요</p>

                <!-- 실제 categoryIds로 전송 -->
                <div class="d-flex flex-wrap">
                    <span class="category-tag" data-category-id="1">정치</span>
                    <span class="category-tag" data-category-id="2">경제</span>
                    <span class="category-tag" data-category-id="3">마켓+</span>
                    <span class="category-tag" data-category-id="4">산업</span>
                    <span class="category-tag" data-category-id="5">사회</span>
                    <span class="category-tag" data-category-id="6">전국</span>
                    <span class="category-tag" data-category-id="7">세계</span>
                    <span class="category-tag" data-category-id="8">문화</span>
                    <span class="category-tag" data-category-id="9">건강</span>
                    <span class="category-tag" data-category-id="10">연예</span>
                    <span class="category-tag" data-category-id="11">스포츠</span>
                </div>

                <!-- 선택된 카테고리 ID를 저장할 hidden inputs -->
                <div id="categoryInputs"></div>
            </div>

            <!-- 완료 버튼 -->
            <button type="submit" class="btn btn-primary w-100 mt-3"
                    style="padding: 10px; border-radius: 10px;">
                정보 저장하고 시작하기
            </button>

        </form>

    </div>
</div>


<!-- 이미지 미리보기 스크립트 -->
<script>
    function previewImage(event) {
        const reader = new FileReader();
        reader.onload = function(){
            document.getElementById('profilePreview').src = reader.result;
        };
        reader.readAsDataURL(event.target.files[0]);
    }

    // 카테고리 선택 토글 및 hidden input 생성
    const categoryInputsDiv = document.getElementById('categoryInputs');

    document.addEventListener("click", function(e) {
        if (e.target.classList.contains("category-tag")) {
            e.target.classList.toggle("active");
            updateCategoryInputs();
        }
    });

    function updateCategoryInputs() {
        // 기존 hidden inputs 제거
        categoryInputsDiv.innerHTML = '';

        // 선택된 카테고리들의 hidden input 생성
        document.querySelectorAll('.category-tag.active').forEach(tag => {
            const categoryId = tag.getAttribute('data-category-id');
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'categoryIds';
            input.value = categoryId;
            categoryInputsDiv.appendChild(input);
        });
    }

    // 폼 제출 시 카테고리 선택 검증
    document.querySelector('form').addEventListener('submit', function(e) {
        const selectedCategories = document.querySelectorAll('.category-tag.active');
        if (selectedCategories.length === 0) {
            e.preventDefault();
            alert('관심 카테고리를 최소 1개 이상 선택해주세요.');
        }
    });
</script>

</body>
</html>