package com.zhaoming.blog.v1.service;

import com.zhaoming.blog.v1.model.frontend.vo.ArticleVo;
import com.zhaoming.blog.v1.model.frontend.vo.IndexArticleVo;
import com.zhaoming.blog.v1.model.frontend.vo.IndexVo;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.query.model.layui.LayTableQuery;

/**
 * created by Wuwenbin on 2018/1/31 at 19:03
 */
public interface IndexService {

    IndexVo findIndexVo();

    IndexArticleVo next(Page<ArticleVo> nextPage, LayTableQuery layTableQuery);
}
