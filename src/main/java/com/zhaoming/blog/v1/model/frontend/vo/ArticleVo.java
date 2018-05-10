package com.zhaoming.blog.v1.model.frontend.vo;

import lombok.*;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;
import me.wuwenbin.modules.sql.annotation.SQLTable;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.zhaoming.blog.v1.model.Cate;
import com.zhaoming.blog.v1.model.User;

/**
 * created by Wuwenbin on 2018/2/3 at 18:09
 */
@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLTable("t_article")
public class ArticleVo implements Serializable {

    private Long id;
    private String title;
    private String cover;
    private Long cateId;
    @SQLPkRefer(targetClass = User.class, targetColumn = "nickname", joinColumn = "author_id")
    private String authorName;
    @SQLPkRefer(targetClass = Cate.class, targetColumn = "cn_name", joinColumn = "cate_id")
    private String cateName;
    private String summary;
    private Integer views;
    private LocalDateTime post;
    private Boolean top;
}
