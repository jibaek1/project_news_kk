package com.ssr.newskuku.domain.userbookmark;

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
@Alias("BookMark")
public class UserBookMark extends BaseEntity {

    private Long bookmarkId;
    private Long userId;
    private Long newsId;

}
