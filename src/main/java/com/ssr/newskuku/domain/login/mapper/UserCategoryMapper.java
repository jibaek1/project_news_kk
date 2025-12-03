package com.ssr.newskuku.domain.login.mapper;

import com.ssr.newskuku.domain.login.UserCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserCategoryMapper {

    /**
     * 사용자의 관심 카테고리 목록 조회
     */
    List<UserCategory> findByUserId(@Param("userId") Long userId);

    /**
     * 사용자의 관심 카테고리 ID 목록 조회
     */
    List<Long> findCategoryIdsByUserId(@Param("userId") Long userId);

    /**
     * 사용자의 관심 카테고리 이름 목록 조회
     */
    List<String> findCategoryNamesByUserId(@Param("userId") Long userId);

    /**
     * 새 관심 카테고리 추가
     */
    void insert(UserCategory userCategory);

    /**
     * 여러 관심 카테고리 일괄 추가
     */
    void insertBatch(@Param("userId") Long userId,
                     @Param("categories") List<String> categories);

    /**
     * 카테고리 이름으로 여러 관심 카테고리 일괄 추가
     */
    void insertBatchByNames(@Param("userId") Long userId,
                            @Param("categoryNames") List<String> categoryNames);

    /**
     * 특정 사용자-카테고리 관계 삭제
     */
    void delete(@Param("userCategoryId") Long userCategoryId);

    /**
     * 사용자의 특정 카테고리 관심 삭제
     */
    void deleteByUserIdAndCategoryId(@Param("userId") Long userId,
                                     @Param("categoryId") Long categoryId);

    /**
     * 사용자의 모든 관심 카테고리 삭제
     */
    void deleteAllByUserId(@Param("userId") Long userId);

    /**
     * 사용자의 관심 카테고리 전체 교체 (삭제 후 재등록)
     * 트랜잭션 처리 필요
     */
    default void replaceAll(@Param("userId") Long userId,
                            @Param("categoryIds") List<String> categories) {
        deleteAllByUserId(userId);
        if (categories != null && !categories.isEmpty()) {
            insertBatch(userId, categories);
        }
    }

    /**
     * 사용자가 특정 카테고리에 관심이 있는지 확인
     */
    boolean existsByUserIdAndCategoryId(@Param("userId") Long userId,
                                        @Param("categoryId") Long categoryId);

    /**
     * 특정 카테고리에 관심 있는 사용자 ID 목록 조회
     */
    List<Long> findUserIdsByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 특정 카테고리에 관심 있는 사용자 수 조회
     */
    int countUsersByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 카테고리별 관심 사용자 수 통계
     * 인기 카테고리 파악용
     */
    List<Map<String, Object>> getCategoryStatistics();

    /**
     * 사용자의 관심 카테고리 수 조회
     */
    int countCategoriesByUserId(@Param("userId") Long userId);

    /**
     * 여러 카테고리 중 하나라도 관심 있는 사용자 ID 목록 조회
     * IN 절 사용
     */
    List<Long> findUserIdsByAnyCategoryIds(@Param("categoryIds") List<Long> categoryIds);

    /**
     * 특정 사용자들의 공통 관심 카테고리 조회
     */
    List<Long> findCommonCategoryIds(@Param("userIds") List<Long> userIds);

    /**
     * 최근 추가된 사용자 관심 카테고리 조회
     */
    List<UserCategory> findRecentByUserId(@Param("userId") Long userId,
                                          @Param("limit") int limit);
}