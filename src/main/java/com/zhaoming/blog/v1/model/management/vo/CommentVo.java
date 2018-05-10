package com.zhaoming.blog.v1.model.management.vo;

import com.zhaoming.blog.v1.model.Article;
import com.zhaoming.blog.v1.model.Comment;
import com.zhaoming.blog.v1.model.User;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;

/**
 * created by Wuwenbin on 2018/1/25 at 14:25
 */
@Getter
@Setter
public class CommentVo extends Comment {

    @SQLPkRefer(targetClass = User.class, targetColumn = "nickname", joinColumn = "user_id")
    private String nickname;
    @SQLPkRefer(targetClass = Article.class, targetColumn = "title", joinColumn = "article_id")
    private String articleTitle;
}
