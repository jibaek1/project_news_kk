package com.ssr.newskuku.domain.login.mapper;

import com.ssr.newskuku.domain.login.Gender;
import com.ssr.newskuku.domain.login.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserInfoMapper {

    /**
     * 사용자 ID로 사용자 정보 조회
     */
    UserInfo findByUserId(@Param("userId") Long userId);

    /**
     * 새 사용자 정보 등록
     */
    void insert(UserInfo userInfo);

    /**
     * 사용자 정보 업데이트
     */
    void update(UserInfo userInfo);

    /**
     * 프로필 이미지 URL 업데이트
     */
    void updateProfileImage(@Param("userId") Long userId,
                            @Param("profileImageUrl") String profileImageUrl);

    /**
     * 닉네임 업데이트
     */
    void updateNickname(@Param("userId") Long userId,
                        @Param("nickname") String nickname);

    /**
     * 성별 업데이트
     */
    void updateGender(@Param("userId") Long userId,
                      @Param("gender") Gender gender);

    /**
     * 생년월일 업데이트
     */
    void updateBirthDate(@Param("userId") Long userId,
                         @Param("birthDate") LocalDate birthDate);

    /**
     * 추가 정보 일괄 업데이트 (생년월일, 성별)
     */
    void updateAdditionalInfo(@Param("userId") Long userId,
                              @Param("gender") Gender gender,
                              @Param("birthDate") LocalDate birthDate);

    /**
     * 사용자 정보 삭제
     */
    void delete(@Param("userId") Long userId);

    /**
     * 닉네임 중복 확인
     */
    boolean existsByNickname(@Param("nickname") String nickname);

    /**
     * 닉네임으로 사용자 정보 조회
     */
    UserInfo findByNickname(@Param("nickname") String nickname);

    /**
     * 특정 성별의 사용자 목록 조회
     */
    List<UserInfo> findByGender(@Param("gender") Gender gender);

    /**
     * 특정 연령대의 사용자 목록 조회
     * birthDate를 기준으로 현재 나이를 계산하여 조회
     */
    List<UserInfo> findByAgeRange(@Param("minAge") Integer minAge,
                                  @Param("maxAge") Integer maxAge);

    /**
     * 모든 사용자 정보 조회 (관리자용)
     */
    List<UserInfo> findAll();

    /**
     * 프로필 완성도 체크 (필수 정보 입력 여부)
     * 생년월일, 성별 정보가 모두 있으면 true
     */
    boolean isProfileComplete(@Param("userId") Long userId);

    /**
     * 연령대별 사용자 수 통계
     * birthDate를 기준으로 현재 나이를 계산하여 통계
     */
    List<Map<String, Object>> countByAgeGroup();

    /**
     * 성별 사용자 수 통계
     */
    List<Map<String, Object>> countByGender();


    List<String> findCategoryNamesByUserId(@Param("userId") Long userId);
}