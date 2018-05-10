package com.zhaoming.blog.v1.model.management.vo;

import com.zhaoming.blog.v1.model.Article;
import com.zhaoming.blog.v1.model.Cate;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;

/**
 * created by Wuwenbin on 2018/1/18 at 11:58
 */
@Getter
@Setter
public class ArticleVo extends Article {

    @SQLPkRefer(targetClass = Cate.class, targetColumn = "cn_name", joinColumn = "cate_id")
    private String cateName;
}
