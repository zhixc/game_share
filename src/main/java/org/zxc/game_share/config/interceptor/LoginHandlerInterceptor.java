package org.zxc.game_share.config.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.zxc.game_share.bean.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * @author 知行成
 * @since 2021-7-21
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {

    /**
     * 登录拦截器
     * return true放行，false不放行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User loginUser = (User) request.getSession().getAttribute("USER");
        if (loginUser == null) { // 没有登录
//            System.out.println("登录拦截器拦截成功！");
            return false; // 不放行
        }else {
//            System.out.println("用户已经登录，登录拦截器开始放行....");
            return true;   // 放行
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
