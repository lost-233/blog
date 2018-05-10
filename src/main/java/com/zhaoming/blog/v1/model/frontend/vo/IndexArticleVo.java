package com.zhaoming.blog.v1.model.frontend.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wuwenbin.modules.jpa.support.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.zhaoming.blog.v1.model.Tag;

/**
 * created by Wuwenbin on 2018/2/4 at 12:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexArticleVo implements Serializable {
    private Page<ArticleVo> page;
    private Map<Long, List<Tag>> tags;
}
