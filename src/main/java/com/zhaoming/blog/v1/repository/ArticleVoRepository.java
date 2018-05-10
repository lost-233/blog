package com.zhaoming.blog.v1.repository;

import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;

import org.springframework.transaction.annotation.Transactional;

import com.zhaoming.blog.v1.model.frontend.vo.ArticleVo;

/**
 * created by Wuwenbin on 2018/1/16 at 17:00
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface ArticleVoRepository extends IPageAndSortRepository<ArticleVo, Long>, IBaseCrudRepository<ArticleVo, Long> {
}
