package com.zhaoming.blog.v1.service.impl;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.query.model.layui.LayTableQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhaoming.blog.v1.common.blog.BlogUtils;
import com.zhaoming.blog.v1.model.Cate;
import com.zhaoming.blog.v1.model.Tag;
import com.zhaoming.blog.v1.model.XParam;
import com.zhaoming.blog.v1.model.frontend.vo.ArticleVo;
import com.zhaoming.blog.v1.model.frontend.vo.IndexArticleVo;
import com.zhaoming.blog.v1.model.frontend.vo.IndexVo;
import com.zhaoming.blog.v1.repository.*;
import com.zhaoming.blog.v1.service.IndexService;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * created by Wuwenbin on 2018/1/31 at 19:04
 */
@Service
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public class IndexServiceImpl implements IndexService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleVoRepository articleVoRepository;
    @Autowired
    private ParamRepository paramRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagReferRepository tagReferRepository;
    @Autowired
    private CateRepository cateRepository;

    @Override
    public IndexVo findIndexVo() {
        List<XParam> xParams = paramRepository.findAll();
        Map<String, Object> settings = BlogUtils.settingMap(xParams);
        long blogCount = articleRepository.count();
        List<Cate> cateList = cateRepository.findAll();
        List<Map<String, Object>> randomArticles = BlogUtils.toLowerKeyListMap(articleRepository.findRandomArticles(10));
        List<Map<String, Object>> tagsTab = BlogUtils.toLowerKeyListMap(tagReferRepository.findTagsTab());
        return IndexVo.builder()
                .settings(settings)
                .blogCount(blogCount)
                .cateList(cateList)
                .randomArticles(randomArticles)
                .tagList(tagsTab).build();
    }

    @Override
    public IndexArticleVo next(Page<ArticleVo> nextPage, LayTableQuery layTableQuery) {
        Page<ArticleVo> page = articleVoRepository.findPagination(nextPage, ArticleVo.class, layTableQuery);
        Map<Long, List<Tag>> articleTags = page.getTResult().stream()
                .collect(toMap(ArticleVo::getId, article -> tagRepository.findArticleTags(article.getId(), true)));
        return IndexArticleVo.builder()
                .page(page)
                .tags(articleTags).build();
    }
}
