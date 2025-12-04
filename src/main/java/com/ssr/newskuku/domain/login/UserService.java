package com.ssr.newskuku.domain.login;
import com.ssr.newskuku.domain.login.dto.UserRequest;
import com.ssr.newskuku.domain.login.dto.UserResponse;
import com.ssr.newskuku.domain.login.mapper.UserAccountMapper;
import com.ssr.newskuku.domain.login.mapper.UserCategoryMapper;
import com.ssr.newskuku.domain.login.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserAccountMapper userAccountMapper;
    private final UserInfoMapper userInfoMapper;
    private final UserCategoryMapper userCategoryMapper;

    /**
     * 사용자 전체 정보 조회 (Account + Info + Categories)
     */
    public UserResponse.FullInfo getUserFullInfo(Long userId) {
        UserAccount account = userAccountMapper.findByUserId(userId);
        if (account == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        UserInfo info = userInfoMapper.findByUserId(userId);

        List<Long> categoryIds = userCategoryMapper.findCategoryIdsByUserId(userId);

        return new UserResponse.FullInfo(account, info, categoryIds);
    }

    /**
     * 사용자 프로필 정보 조회
     */
    public UserResponse.ProfileInfo getUserProfile(Long userId) {
        UserInfo info = userInfoMapper.findByUserId(userId);
        if (info == null) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }

        List<Long> categoryIds = userCategoryMapper.findCategoryIdsByUserId(userId);
        return new UserResponse.ProfileInfo(info, categoryIds);
    }

    /**
     * 사용자 계정 정보 조회
     */
    public UserResponse.AccountInfo getUserAccount(Long userId) {
        UserAccount account = userAccountMapper.findByUserId(userId);
        if (account == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        return new UserResponse.AccountInfo(account);
    }

    /**
     * 프로필 정보 업데이트 (닉네임, 프로필 이미지, 모바일)
     */
    @Transactional
    public void updateProfile(Long userId, UserRequest.UpdateProfile request) {
        UserInfo userInfo = userInfoMapper.findByUserId(userId);
        if (userInfo == null) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }

        // 닉네임 중복 체크
        if (request.getNickname() != null && !request.getNickname().equals(userInfo.getNickname())) {
            if (userInfoMapper.existsByNickname(request.getNickname())) {
                throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
            }
        }

        // 업데이트할 정보 설정
        if (request.getProfileImage() != null) {
            userInfo.setProfileImage(request.getProfileImage());
        }
        if (request.getNickname() != null) {
            userInfo.setNickname(request.getNickname());
        }
        if (request.getPhoneNumber() != null) {
            userInfo.setPhoneNumber(request.getPhoneNumber());
        }

        userInfoMapper.update(userInfo);
        log.info("사용자 프로필 업데이트 완료: userId={}", userId);
    }

    /**
     * 추가 정보 업데이트 (생년월일, 성별)
     * OAuth2 로그인 후 추가 정보 수집 시 사용
     */
    @Transactional
    public void updateAdditionalInfo(Long userId, UserRequest.AdditionalInfo request) {
        UserInfo userInfo = userInfoMapper.findByUserId(userId);
        if (userInfo == null) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }

        // 닉네임 중복 체크
        if (request.getNickname() != null && !request.getNickname().equals(userInfo.getNickname())) {
            if (userInfoMapper.existsByNickname(request.getNickname())) {
                throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
            }
        }

        // 성별 변환
        Gender gender = null;
        if (request.getGender() != null) {
            gender = Gender.valueOf(request.getGender().toUpperCase());
        }

        if (request.getBirthDate() != null) {
            userInfo.setBirthDate(request.getBirthDate());
        }
        if (gender != null) {
            userInfo.setGender(gender);
        }
        if (request.getNickname() != null) {
            userInfo.setNickname(request.getNickname());
        }
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isEmpty()) {
            userInfo.setPhoneNumber(request.getPhoneNumber());
        }

        userInfoMapper.update(userInfo);

        // 카테고리 업데이트
        if (request.getCategories() != null && !request.getCategories().isEmpty()) {
            updateUserCategories(userId, request.getCategories());
        }

        log.info("사용자 추가 정보 업데이트 완료: userId={}", userId);
    }

    /**
     * 사용자 관심 카테고리 업데이트
     */
    @Transactional
    public void updateUserCategories(Long userId, List<String> categories) {
        // 기존 카테고리 삭제
        userCategoryMapper.deleteAllByUserId(userId);

        // 새 카테고리 등록
        if (categories != null && !categories.isEmpty()) {
            userCategoryMapper.insertBatch(userId, categories);
        }

        log.info("사용자 관심 카테고리 업데이트 완료: userId={}, count={}", userId, categories.size());
    }

    /**
     * 사용자 상태 변경 (활성화, 비활성화)
     */
    @Transactional
    public void updateUserStatus(Long userId, Status status) {
        UserAccount account = userAccountMapper.findByUserId(userId);
        if (account == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        userAccountMapper.updateStatus(userId, status);
        log.info("사용자 상태 변경 완료: userId={}, status={}", userId, status);
    }

    /**
     * 사용자 삭제 (계정, 정보, 카테고리 모두 삭제)
     */
    @Transactional
    public void deleteUser(Long userId) {
        // 카테고리 삭제
        userCategoryMapper.deleteAllByUserId(userId);

        // 사용자 정보 삭제
        userInfoMapper.delete(userId);

        // 계정 삭제
        userAccountMapper.delete(userId);

        log.info("사용자 삭제 완료: userId={}", userId);
    }

    /**
     * 닉네임 중복 확인
     */
    public boolean isNicknameAvailable(String nickname) {
        return !userInfoMapper.existsByNickname(nickname);
    }

    /**
     * 사용자 목록 조회 (관리자용)
     */
    public List<UserResponse.UserList> getAllUsers() {
        List<UserAccount> accounts = userAccountMapper.findAll();

        return accounts.stream()
                .map(account -> {
                    UserInfo info = userInfoMapper.findByUserId(account.getUserId());
                    return new UserResponse.UserList(account, info);
                })
                .toList();
    }

    /**
     * 특정 상태의 사용자 목록 조회
     */
    public List<UserResponse.UserList> getUsersByStatus(Status status) {
        List<UserAccount> accounts = userAccountMapper.findByStatus(status);

        return accounts.stream()
                .map(account -> {
                    UserInfo info = userInfoMapper.findByUserId(account.getUserId());
                    return new UserResponse.UserList(account, info);
                })
                .toList();
    }

    /**
     * 프로필 완성도 확인
     */
    public boolean isProfileComplete(Long userId) {
        return userInfoMapper.isProfileComplete(userId);
    }

    /**
     * 사용자 통계 조회
     */
    public int getTotalUserCount() {
        return userAccountMapper.countAll();
    }

    /**
     * 제공자별 사용자 수 조회
     */
    public int getUserCountByProvider(Provider provider) {
        return userAccountMapper.countByProvider(provider);
    }

    public UserInfo getUserInfo(Long userId) {
        UserInfo info = userInfoMapper.findByUserId(userId);
        if (info == null) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }

        List<String> categories = userInfoMapper.findCategoryNamesByUserId(userId);
        info.setCategories(categories);

        return info;
    }
}