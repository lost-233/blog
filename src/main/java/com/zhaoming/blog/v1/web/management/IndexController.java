package com.zhaoming.blog.v1.web.management;

import me.wuwenbin.modules.utils.http.CookieUtils;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.http.WebUtils;
import me.wuwenbin.modules.valdiation.template.RTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.zhaoming.blog.v1.common.SessionParam;
import com.zhaoming.blog.v1.common.blog.BlogContext;
import com.zhaoming.blog.v1.common.blog.BlogUtils;
import com.zhaoming.blog.v1.model.Article;
import com.zhaoming.blog.v1.model.Note;
import com.zhaoming.blog.v1.model.User;
import com.zhaoming.blog.v1.repository.ArticleRepository;
import com.zhaoming.blog.v1.repository.CateRepository;
import com.zhaoming.blog.v1.repository.CommentRepository;
import com.zhaoming.blog.v1.repository.NoteRepository;
import com.zhaoming.blog.v1.web.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static me.wuwenbin.modules.utils.http.R.error;
import static me.wuwenbin.modules.utils.web.Controllers.builder;

/**
 * created by Wuwenbin on 2018/1/14 at 14:38
 */
@Controller("managementIndexController")
@RequestMapping("/management")
public class IndexController extends BaseController {

    @Autowired
    private BlogContext blogContext;
    @Autowired
    private RTemplate rTemplate;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CateRepository cateRepository;

    @GetMapping("/index")
    public String index(Model model, @CookieValue(SessionParam.SESSION_ID_COOKIE) String uuid) {
        User user = blogContext.getSessionUser(uuid);
        model.addAttribute("avatar", user.getAvatar());
        model.addAttribute("nickname", user.getNickname());
        return "management/index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("articleCnt", articleRepository.count());
        model.addAttribute("noteCnt", noteRepository.count());
        model.addAttribute("commentCnt", commentRepository.count());
        model.addAttribute("latestArticle", articleRepository.findLatestArticle());
        model.addAttribute("latestNote", noteRepository.findLatestNote());
        model.addAttribute("latestComment", BlogUtils.toLowerKeyMap(commentRepository.findLatestComment()));
        model.addAttribute("cateList", cateRepository.findAll());
        return "management/home";
    }

    @PostMapping("/simple/post/article")
    @ResponseBody
    public R simplePostArticle(@Valid Article article, BindingResult result) {
        if (article.getContent().length() > 300) {
            return error("草稿字数不宜过多！");
        }
        article.setId(System.currentTimeMillis() / 1000);
        article.setSummary(article.getContent());
        return validResponse(result, rTemplate, article,
                simpleArticle -> builder("发布文章草稿").exec(() -> articleRepository.saveSimpleArticle(simpleArticle) == 1));
    }

    @PostMapping("/simple/post/note")
    @ResponseBody
    public R simplePostNote(@Valid Note note, BindingResult result) {
        if (note.getContent().length() > 300) {
            return error("字数不宜过多！");
        }
        note.setClearContent(WebUtils.filterHtml(note.getContent()));
        return validResponse(result, rTemplate, note,
                simpleNote -> builder("发布随笔").exec(() -> noteRepository.saveSimpleNote(simpleNote) == 1));
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, String from, @CookieValue(SessionParam.SESSION_ID_COOKIE) String uuid) {
        blogContext.removeSessionUser(uuid);
        request.getSession().invalidate();
        CookieUtils.deleteCookie(request, response, SessionParam.REMEMBER_COOKIE_NAME);
        if (StringUtils.isEmpty(from)) {
            return "redirect:/";
        } else {
            return "redirect:" + SessionParam.MANAGEMENT_INDEX;
        }
    }
}
