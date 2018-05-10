package com.zhaoming.blog.v1.repository;

import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;

import org.springframework.transaction.annotation.Transactional;

import com.zhaoming.blog.v1.model.Upload;

/**
 * created by Wuwenbin on 2018/1/17 at 20:11
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface UploadRepository extends IPageAndSortRepository<Upload, Long>, IBaseCrudRepository<Upload, Long> {
}
