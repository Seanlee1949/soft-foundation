package com.example.boot.common;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static com.example.boot.constant.CommonConstant.USER_NAME;

/**
 * @author lishuai
 * @since 2023/3/14
 */
public class CookiendSessionInterceptor implements AsyncHandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CookiendSessionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.debug("进入拦截器");
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                LOGGER.debug("cookie===for遍历" + cookie.getName());
                if (Objects.equals(cookie.getName(), "userName")) {
                    ThreadContext.put(USER_NAME, cookie.getValue());
                    return true;
                }
            }
        }
        LOGGER.debug("没有cookie-----cookie时间可能到期，");
//        response.sendRedirect("index.html");
        //返回false，不执行原来controller的方法
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println();
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println();
    }

}