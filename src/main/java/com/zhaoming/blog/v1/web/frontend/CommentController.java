package com.zhaoming.blog.v1.web.frontend;

import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.http.WebUtils;
import me.wuwenbin.modules.utils.lang.LangUtils;
import me.wuwenbin.modules.utils.web.Injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhaoming.blog.v1.common.blog.BlogUtils;
import com.zhaoming.blog.v1.model.Comment;
import com.zhaoming.blog.v1.model.Keyword;
import com.zhaoming.blog.v1.repository.ArticleRepository;
import com.zhaoming.blog.v1.repository.CommentRepository;
import com.zhaoming.blog.v1.repository.KeywordRepository;
import com.zhaoming.blog.v1.repository.ParamRepository;
import com.zhaoming.blog.v1.web.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static me.wuwenbin.modules.utils.http.R.error;
import static me.wuwenbin.modules.utils.web.Controllers.builder;

/**
 * created by Wuwenbin on 2018/2/8 at 18:54
 */
@Controller
@RequestMapping("/token/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private KeywordRepository keywordRepository;
    @Autowired
    private ParamRepository paramRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @PostMapping("/sub")
    @ResponseBody
    public R sub(@Valid Comment comment, BindingResult bindingResult, HttpServletRequest request) {
        if (!paramRepository.findValueByName("all_comment_open").equals("1") || !articleRepository.findOne(comment.getArticleId()).getCommented()) {
            return error("未开放评论功能，请勿非法操作！");
        }
        if (!bindingResult.hasErrors()) {
            comment.setIpAddr(WebUtils.getRemoteAddr(request));
            comment.setIpCnAddr(BlogUtils.getIpCnInfo(BlogUtils.getIpInfo(comment.getIpAddr())));
            comment.setUserAgent(request.getHeader("user-agent"));
            comment.setComment(Injection.stripSqlXSS(comment.getComment()));
            List<Keyword> keywords = keywordRepository.findAll();
            keywords.forEach(kw -> comment.setComment(comment.getComment().replace(kw.getWords(), LangUtils.string.repeat("*", kw.getWords().length()))));
            return builder("发表评论成功", "发表评论失败", "发表评论失败").exec(() -> commentRepository.save(comment) != null);
        } else {
            return error("提交的评论内容不合法");
        }
    }
}
