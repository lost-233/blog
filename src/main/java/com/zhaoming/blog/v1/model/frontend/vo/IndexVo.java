package com.zhaoming.blog.v1.model.frontend.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.zhaoming.blog.v1.model.Cate;

/**
 * created by Wuwenbin on 2018/1/30 at 13:21
 */
@Getter
@Setter
@Builder
public class IndexVo implements Serializable {

    private Map<String, Object> settings;
    private long blogCount;
    private List<Cate> cateList;
    private List<Map<String, Object>> randomArticles;
    private List<Map<String, Object>> tagList;

}
