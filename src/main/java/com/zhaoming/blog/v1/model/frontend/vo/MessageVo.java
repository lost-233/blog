package com.zhaoming.blog.v1.model.frontend.vo;

import com.zhaoming.blog.v1.model.Message;
import com.zhaoming.blog.v1.model.User;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;

/**
 * created by Wuwenbin on 2018/1/25 at 14:25
 */
@Getter
@Setter
public class MessageVo extends Message {

    @SQLPkRefer(targetClass = User.class, targetTableAlias = "tu_1", targetColumn = "nickname", joinColumn = "user_id")
    private String nickname;
    @SQLPkRefer(targetClass = User.class, targetTableAlias = "tu_2", targetColumn = "avatar", joinColumn = "user_id")
    private String avatar;
}
