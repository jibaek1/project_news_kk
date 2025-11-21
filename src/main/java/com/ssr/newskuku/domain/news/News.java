package com.ssr.newskuku.domain.news;


import com.ssr.newskuku._global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("News")
public class News extends BaseEntity {

    private Long newsId;
    private String title;
    private String content;
    private String url;
    private int categoryId;
    private String thumbnail;

}
