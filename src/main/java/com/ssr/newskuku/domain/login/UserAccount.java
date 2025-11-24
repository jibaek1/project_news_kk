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
@Alias("user_account")
public class UserAccount extends BaseEntity {

    private Long userId;
    private String email;
    private Provider provider;
    private String socialId;
    private Status status;

}
