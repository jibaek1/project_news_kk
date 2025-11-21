package com.ssr.newskuku.domain.login.dto;

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

        private List<Integer> categoryIds;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateProfile {
        private String profileImage;
        private LocalDate birthDate;
        private String gender;
        private String nickname;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateCategories {
        private List<Integer> categoryIds;
    }


    @Getter
    @Setter
    @ToString
    public static class UpdateNickname {
        private String nickname;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateStatus {
        private String status;
    }
}
