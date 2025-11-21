package com.ssr.newskuku.domain.bookmark;

import com.ssr.newskuku._global.entity.BaseEntity;
import com.ssr.newskuku.domain.news.News;
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
public class BookMark extends BaseEntity {

    private Long bookmarkId;
    private Long userInfoId;
    private News news;
    boolean status;

}
