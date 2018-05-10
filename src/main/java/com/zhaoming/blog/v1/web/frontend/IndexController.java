package com.zhaoming.blog.v1.web.frontend;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.utils.http.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.zhaoming.blog.v1.common.SessionParam;
import com.zhaoming.blog.v1.common.blog.BlogContext;
import com.zhaoming.blog.v1.model.frontend.bo.IndexArticleBo;
import com.zhaoming.blog.v1.model.frontend.vo.ArticleVo;
import com.zhaoming.blog.v1.model.frontend.vo.IndexArticleVo;
import com.zhaoming.blog.v1.service.IndexService;

import javax.servlet.http.HttpServletRequest;

import static me.wuwenbin.modules.utils.http.R.ok;

/**
 * created by Wuwenbin on 2018/1/31 at 13:48
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private BlogContext blogContext;
    @Autowired
    private IndexService indexService;

    @GetMapping(value = {"", "/index"})
    public String index(Model model) {
        model.addAttribute("indexVo", indexService.findIndexVo());
        return "frontend/index";
    }

    @PostMapping(value = {"/next", "/index/next"})
    @ResponseBody
    public R next(Page<ArticleVo> nextPage, IndexArticleBo indexArticleBo) {
        IndexArticleVo indexArticleVo = indexService.next(nextPage, indexArticleBo);
        return ok(indexArticleVo);
    }

    @GetMapping("/token/logout")
    public String logout(HttpServletRequest request, String from, @CookieValue(SessionParam.SESSION_ID_COOKIE) String uuid) {
        blogContext.removeSessionUser(uuid);
        request.getSession().invalidate();
        if (StringUtils.isEmpty(from)) {
            return "redirect:/";
        } else {
            return "redirect:" + SessionParam.MANAGEMENT_INDEX;
        }
    }
}
