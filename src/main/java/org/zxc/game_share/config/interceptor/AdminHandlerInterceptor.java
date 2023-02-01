package org.zxc.game_share.config.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.zxc.game_share.bean.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员拦截器
 */
public class AdminHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /**
         * 从 session 中取出已经存起来的用户信息
         */
        User loginUser = (User) request.getSession().getAttribute("USER");
        if (loginUser == null){
            // 说明用户没有登录，不放行
//            System.out.println("用户没有登录！管理员拦截器拦截成功！");
            return false;
        }
        System.out.println("角色权限为"+loginUser.getRole().getRId());
        System.out.println("角色权限名称为"+loginUser.getRole().getRoleName());
        if (loginUser.getRole().getRId() == 1) {
            // 进入这里，说明用户是管理员
//            System.out.println("管理员用户登录，给管理员放行");
            return true;
        }
//        System.out.println("管理员拦截器已经成功拦截");

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
