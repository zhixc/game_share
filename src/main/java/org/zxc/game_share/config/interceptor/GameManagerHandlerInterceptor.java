package org.zxc.game_share.config.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.zxc.game_share.bean.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 游戏管理员拦截器
 */
public class GameManagerHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /**
         * 从 session 中取出已经存起来的用户信息
         */
        User loginUser = (User) request.getSession().getAttribute("USER");
        if (loginUser == null){
            // 说明用户没有登录，不放行
//            System.out.println("用户没有登录！游戏管理员拦截器拦截成功！");
            return false;
        }
//        System.out.println("角色权限为"+loginUser.getRole().getRId());
//        System.out.println("角色权限名称为"+loginUser.getRole().getRoleName());
        if (loginUser.getRole().getRId() == 1) {
            // 进入这里，说明用户是系统管理员, 放行
            return true;
        }else if (loginUser.getRole().getRId() == 2){
            // 进入这里，说明用户是游戏管理员，放行
//            System.out.println("游戏管理员登录，放行");
            return true;
        }
        // 到达这里，必须拦截
//        System.out.println("游戏管理员拦截器已经成功拦截");

        return false;
    }
}
