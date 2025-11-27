package com.ssr.newskuku.domain.inquiry;

import com.ssr.newskuku._global.entity.BaseEntity;

import java.sql.Timestamp;

public class Inquiry extends BaseEntity {

    private Long id;

    private Long userId;


    private String title;


    private String content;


    private String answerContent;

    private Timestamp createdAt;

    private Long answerId;

    private Timestamp updatedAt;
}
