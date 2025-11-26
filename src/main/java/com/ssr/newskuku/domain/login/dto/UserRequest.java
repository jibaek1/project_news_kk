package com.ssr.newskuku.domain.login.dto;

import com.ssr.newskuku.domain.login.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

public class UserRequest {

    @Getter
    @Setter
    @ToString
    public static class SocialLogin {
        private String provider;
        private String socialId;
        private String email;
        private String profileImage;
        private String name;
    }

    @Getter
    @Setter
    @ToString
    public static class SignUp {
        private String email;
        private String provider;
        private String socialId;
        private String profileImage;
        private LocalDate birthDate;
        private String gender;
        private String nickname;
        private String phoneNumber;
        private List<Long> categoryIds;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateProfile {
        private String profileImage;
        private String nickname;
        private String phoneNumber;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateCategories {
        private List<Long> categoryIds;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateStatus {
        private Status status;
    }

    /**
     * 추가 정보 입력용 DTO
     * OAuth2 로그인 후 추가 정보 수집 시 사용
     */
    @Getter
    @Setter
    @ToString
    public static class AdditionalInfo {
        private LocalDate birthDate;
        private String gender;
        private String nickname;
        private String phoneNumber;
        private List<Long> categoryIds;
    }
}