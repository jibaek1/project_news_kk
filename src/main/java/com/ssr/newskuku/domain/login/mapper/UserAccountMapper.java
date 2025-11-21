package com.ssr.newskuku.domain.login.mapper;

import com.ssr.newskuku.domain.login.Provider;
import com.ssr.newskuku.domain.login.Status;
import com.ssr.newskuku.domain.login.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserAccountMapper {

    /**
     * 소셜 로그인 제공자와 소셜 ID로 사용자 조회
     * OAuth2 로그인 시 기존 사용자 확인용
     */
    UserAccount findByProviderAndSocialId(@Param("provider") Provider provider,
                                          @Param("socialId") String socialId);

    /**
     * 사용자 ID로 조회
     */
    UserAccount findByUserId(@Param("userId") Long userId);

    /**
     * 이메일로 사용자 조회
     */
    UserAccount findByEmail(@Param("email") String email);

    /**
     * 새 사용자 등록
     * MyBatis의 useGeneratedKeys를 사용하여 자동 생성된 userId를 반환
     */
    void insert(UserAccount userAccount);

    /**
     * 사용자 정보 업데이트
     */
    void update(UserAccount userAccount);

    /**
     * 사용자 상태 업데이트 (활성화, 비활성화, 정지 등)
     */
    void updateStatus(@Param("userId") Long userId,
                      @Param("status") Status status);

    /**
     * 사용자 삭제 (물리적 삭제)
     */
    void delete(@Param("userId") Long userId);

    /**
     * 모든 사용자 조회 (관리자용)
     */
    List<UserAccount> findAll();

    /**
     * 특정 상태의 사용자 목록 조회
     */
    List<UserAccount> findByStatus(@Param("status") Status status);

    /**
     * 특정 제공자의 사용자 목록 조회
     */
    List<UserAccount> findByProvider(@Param("provider") Provider provider);

    /**
     * 사용자 총 수 조회
     */
    int countAll();

    /**
     * 제공자별 사용자 수 조회
     */
    int countByProvider(@Param("provider") Provider provider);
}