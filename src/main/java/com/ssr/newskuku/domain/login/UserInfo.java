package com.ssr.newskuku.domain.login;

import com.ssr.newskuku._global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias("user_info")
public class UserInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userInfoId;
    private Long userId;
    private String profileImage;
    private LocalDate birthDate;
    private Gender gender;
    private String nickname;
    private String phoneNumber;
    private List<String> categories;
}
