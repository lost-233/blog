package com.zhaoming.blog.v1.web.management;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.layui.LayuiTable;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.web.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhaoming.blog.v1.model.User;
import com.zhaoming.blog.v1.model.management.bo.UserQueryBo;
import com.zhaoming.blog.v1.repository.UserRepository;
import com.zhaoming.blog.v1.web.BaseController;

/**
 * created by Wuwenbin on 2018/1/23 at 18:00
 */
@Controller
@RequestMapping("/management/user")
public class UserController extends BaseController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String index() {
        return "management/users";
    }

    @GetMapping("/list")
    @ResponseBody
    public LayuiTable<User> list(Page<User> userPage, UserQueryBo userQueryBo) {
        //查找非管理员
        userQueryBo.setDefaultRoleId(2L);
        userPage = userRepository.findPagination(userPage, User.class, userQueryBo);
        return layuiTable(userPage);
    }

    @PostMapping("/edit/enable")
    @ResponseBody
    public R editEnable(Long id, Boolean enable) {
        return Controllers.builder("修改用户状态").exec(() -> userRepository.updateEnableById(enable, id) == 1);
    }
}
