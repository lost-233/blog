package com.zhaoming.blog.v1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zhaoming.blog.v1.common.blog.BlogContext;
import com.zhaoming.blog.v1.config.interceptor.AdminInterceptor;
import com.zhaoming.blog.v1.config.interceptor.SessionInterceptor;
import com.zhaoming.blog.v1.config.interceptor.ValidateInterceptor;

/**
 * created by Wuwenbin on 2018/1/17 at 19:37
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    private final Environment environment;
    @Autowired
    private BlogContext blogContext;

    @Autowired
    public WebMvcConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * 配置静态资源路径以及上传文件的路径
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/upload/**").addResourceLocations(environment.getProperty("spring.resources.static-locations"));
    }

    /**
     * 配置后台以及前台的一些拦截
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ValidateInterceptor(blogContext)).addPathPatterns("/**");
        registry.addInterceptor(new SessionInterceptor(blogContext)).addPathPatterns("/management/**", "/token/**");
        registry.addInterceptor(new AdminInterceptor(blogContext)).addPathPatterns("/management/**");
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error?errorCode=404"));
            container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error?errorCode=500"));
            container.addErrorPages(new ErrorPage(Throwable.class, "/error?errorCode=500"));
        };
    }


}