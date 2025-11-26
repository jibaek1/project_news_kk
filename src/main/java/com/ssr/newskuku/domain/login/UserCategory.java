package com.ssr.newskuku.domain.login;

import com.ssr.newskuku._global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias("user_category")
public class UserCategory extends BaseEntity {

    private Long userCategoryId;
    private Long userId;
    private Long categoryId;
}