package com.zhaoming.blog.v1.common.blog;

import me.wuwenbin.modules.utils.http.CookieUtils;
import me.wuwenbin.modules.utils.http.WebUtils;

import org.springframework.stereotype.Component;

import com.zhaoming.blog.v1.common.SessionParam;
import com.zhaoming.blog.v1.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * created by Wuwenbin on 2018/2/7 at 22:22
 */
@Component
public class BlogContext extends ConcurrentHashMap<String, BlogSession> {

    public void setSessionUser(HttpServletRequest request, HttpServletResponse response, User sessionUser) {
        BlogSession session = BlogSession.builder()
                .sessionUser(sessionUser)
                .expired(false)
                .host(WebUtils.getRemoteAddr(request))
                .build();
        Cookie cookie = CookieUtils.getCookie(request, SessionParam.SESSION_ID_COOKIE);
        if (cookie != null) {
            session.setId(cookie.getValue());
        }
        CookieUtils.setCookie(response, SessionParam.SESSION_ID_COOKIE, session.getId(), -1);
        put(session.getId(), session);
    }

    public User getSessionUser(String uuid) {
        Optional<BlogSession> user = Optional.ofNullable(getOrDefault(uuid, null));
        if (user.isPresent()) {
            return user.get().getSessionUser();
        }
        return null;
    }

    public void removeSessionUser(String uuid) {
        remove(uuid);
    }

    public void clearAll() {
        clear();
    }

}
