package com.zhaoming.blog.v1.web.frontend;

import lombok.extern.slf4j.Slf4j;
import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.utils.http.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhaoming.blog.v1.common.blog.BlogUtils;
import com.zhaoming.blog.v1.model.Note;
import com.zhaoming.blog.v1.model.XParam;
import com.zhaoming.blog.v1.model.frontend.bo.NoteQueryBo;
import com.zhaoming.blog.v1.repository.NoteRepository;
import com.zhaoming.blog.v1.repository.ParamRepository;

import java.util.List;
import java.util.Map;

/**
 * created by Wuwenbin on 2018/2/9 at 14:14
 */
@Slf4j
@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private ParamRepository paramRepository;

    @GetMapping
    public String index(Model model) {
        List<XParam> xParams = paramRepository.findAll();
        Map<String, Object> settings = BlogUtils.settingMap(xParams);
        model.addAttribute("settings", settings);
        model.addAttribute("noteCount", noteRepository.count());
        return "frontend/note";
    }

    @PostMapping("/next")
    @ResponseBody
    public R next(Page<Note> notePage, NoteQueryBo noteQueryBo) {
        notePage = noteRepository.findPagination(notePage, Note.class, noteQueryBo);
        return R.ok(notePage);
    }
}
