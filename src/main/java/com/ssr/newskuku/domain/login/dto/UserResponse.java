package com.ssr.newskuku.domain.login.dto;

import com.ssr.newskuku.domain.login.UserAccount;
import com.ssr.newskuku.domain.login.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserResponse {

    /**
     * 사용자 기본 정보 조회 (Account만)
     */
    @Getter
    public static class AccountInfo {
        private final Long userId;
        private final String email;
        private final String provider;
        private final String status;
        private final String createdAt;

        public AccountInfo(UserAccount account) {
            this.userId = account.getUserId();
            this.email = account.getEmail();
            this.provider = account.getProvider().name();
            this.status = account.getStatus().name();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            this.createdAt = account.getCreatedAt() != null
                    ? account.getCreatedAt().format(formatter)
                    : null;
        }
    }

    /**
     * 사용자 프로필 정보 조회
     */
    @Getter
    public static class ProfileInfo {
        private final Long userId;
        private final String profileImage;
        private final LocalDate birthDate;
        private final String gender;
        private final String nickname;
        private final Integer age;
        private final String createdAt;

        public ProfileInfo(UserInfo info) {
            this.userId = info.getUserId();
            this.profileImage = info.getProfileImage();
            this.birthDate = info.getBirthDate();
            this.gender = info.getGender() != null ? info.getGender().name() : null;
            this.nickname = info.getNickname();
            this.age = calculateAge(info.getBirthDate());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            this.createdAt = info.getCreatedAt() != null
                    ? info.getCreatedAt().format(formatter)
                    : null;
        }

        private Integer calculateAge(LocalDate birthDate) {
            if (birthDate == null) return null;
            return LocalDate.now().getYear() - birthDate.getYear();
        }
    }

    /**
     * 사용자 전체 정보 조회
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FullInfo {
        // Account 정보
        private Long userId;
        private String email;
        private String provider;
        private String status;

        // Profile 정보
        private String profileImage;
        private LocalDate birthDate;
        private String gender;
        private String nickname;
        private Integer age;

        // Category 정보
        private List<Integer> categoryIds;

        private String createdAt;
        private String modifiedAt;

        public FullInfo(UserAccount account, UserInfo info, List<Integer> categoryIds) {
            // Account
            this.userId = account.getUserId();
            this.email = account.getEmail();
            this.provider = account.getProvider().name();
            this.status = account.getStatus().name();

            // Profile
            if (info != null) {
                this.profileImage = info.getProfileImage();
                this.birthDate = info.getBirthDate();
                this.gender = info.getGender() != null ? info.getGender().name() : null;
                this.nickname = info.getNickname();
                this.age = calculateAge(info.getBirthDate());
            }

            // Categories
            this.categoryIds = categoryIds;

            // Timestamp
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            this.createdAt = account.getCreatedAt() != null
                    ? account.getCreatedAt().format(formatter)
                    : null;
            this.modifiedAt = account.getModifiedAt() != null
                    ? account.getModifiedAt().format(formatter)
                    : null;
        }

        private Integer calculateAge(LocalDate birthDate) {
            if (birthDate == null) return null;
            return LocalDate.now().getYear() - birthDate.getYear();
        }
    }

    /**
     * 소셜 로그인 응답
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResult {
        private Long userId;
        private String email;
        private String nickname;
        private String profileImage;
        private boolean isNewUser;

        public static LoginResult from(UserAccount account, UserInfo info, boolean isNewUser) {
            LoginResult result = new LoginResult();
            result.userId = account.getUserId();
            result.email = account.getEmail();
            result.nickname = info != null ? info.getNickname() : null;
            result.profileImage = info != null ? info.getProfileImage() : null;
            result.isNewUser = isNewUser;
            return result;
        }
    }

    /**
     * 사용자 목록 조회 (관리자용)
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserList {
        private Long userId;
        private String email;
        private String provider;
        private String nickname;
        private String status;
        private String createdAt;

        public UserList(UserAccount account, UserInfo info) {
            this.userId = account.getUserId();
            this.email = account.getEmail();
            this.provider = account.getProvider().name();
            this.nickname = info != null ? info.getNickname() : null;
            this.status = account.getStatus().name();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            this.createdAt = account.getCreatedAt() != null
                    ? account.getCreatedAt().format(formatter)
                    : null;
        }
    }
}