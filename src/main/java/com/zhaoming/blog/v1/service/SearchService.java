package com.zhaoming.blog.v1.service;

import com.zhaoming.blog.v1.model.frontend.vo.SearchPageVo;

import me.wuwenbin.modules.jpa.support.Page;

/**
 * created by Wuwenbin on 2018/2/11 at 15:16
 */
public interface SearchService {

    Page<SearchPageVo> findPagination(Page<SearchPageVo> pageVoPage, String sp);
}
