package org.zxc.game_share.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zxc.game_share.config.interceptor.AdminHandlerInterceptor;
import org.zxc.game_share.config.interceptor.ArticleManagerHandlerInterceptor;
import org.zxc.game_share.config.interceptor.GameManagerHandlerInterceptor;
import org.zxc.game_share.config.interceptor.LoginHandlerInterceptor;

/**
 * SpringMVC扩展：拦截器配置
 *
 * @author 知行成
 * @since 2021-7-21
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * 我试了一下，UserService 或者 UserMapper
     * 好像不能直接注入 AdminHandlerInterceptor 中，
     * 查询用户名时会报空指针异常，所以在这里注入试试
     * 作为参数传递过去
     * <p>
     * 2021.07.29已经解决这个问题了
     */
    //@Autowired
    //UserService userService;
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/").setViewName("index");
        //registry.addViewController("/index.html").setViewName("index");
        //registry.addViewController("/main.html").setViewName("dashboard");
    }

    /**
     * 配置拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 下面的拦截器拦截顺序：
        // 前台登录拦截器 => 游戏管理员拦截器 => 游戏测评文章管理员拦截器 => 管理员拦截器

        /**
         * 前台登录拦截器
         */
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns(
                        "/avatar_front.html" // 拦截头像上传页面
                        , "/user/avatar/upload" // 拦截的请求
                        , "/favorite/**"   // 拦截收藏控制器里面的接口，必须登录才能具备收藏功能
                        , "/game_favorite.html" // 拦截查看游戏收藏页面
                        , "/personal_info_update.html" // 拦截修改个人信息页面
                        , "/findPersonInfoByUid"  // 拦截根据uid查询用户个人信息请求
                        , "/updatePersonInfo"  // 拦截根据uid修改用户个人信息请求
                        , "/gameComment/addGameComment"  // 拦截添加评论请求
                        , "/gameComment/findGameCommentByUsernameWithPage" //拦截根据用户名查询评论请求

                        ,"/article/findArticleByAIdForArticleUpdate" // （前台用）根据文章id查询文章
                        , "/article/findMyArticleByTitleWtithPage" // （前台用）拦截前台查看我的文章请求
                        , "/article/deleteArticleByAIdForFront" // （前台用） 删除文章
                        , "/article/updateArticleForFront"  //(前台用)修改文章
                        , "/article/addArticleForFront" //(前台用)拦截前台文章添加请求

                        , "/article_add_for_front.html" // (前台用)拦截前台添加文章页面
                        , "/article_explore_my.html" // (前台用)拦截前台查看我的文章页面
                        , "/article_update_for_front.html" // (前台用)拦截修改文章页面
                )
                .excludePathPatterns(
                        "/getCheckCode"); // 放行的请求

        /**
         * 游戏管理员拦截器
         */
        registry.addInterceptor(new GameManagerHandlerInterceptor())
                .addPathPatterns("/game/**"  // 拦截所有GameController所有的请求，后面根据需要放行
                        , "/gameVideo/upload" // 拦截游戏预览视频上传
                        , "/game.html"  // 拦截游戏管理页面
                        , "/game_add.html"  // 拦截添加游戏页面
                        , "/game_update.html" // 拦截修改游戏页面
                        , "/game_video_upload.html" // 拦截游戏预览视频上传页面
                        , "/game_comment.html" // 拦截游戏评论管理页面
                        , "/game_comment_update.html" // 拦截游戏评论修改页面
                        , "/gameComment/updateGameComment" // 拦截修改游戏评论请求
                        , "/gameComment/deleteGameComment" // 拦截单个删除游戏评论
                        , "/gameComment/deleteGameCommentByIds" // 拦截批量删除游戏评论
                )
                .excludePathPatterns(
                        "/game/cover/{cover}" // 放行游戏封面下载
                        , "/game/findGameByggId" // 放行根据游戏分类查询游戏
                        , "/game/findGameByGid"  // 放行根据游戏主键查询游戏
                        , "/game/findGameByName");  // 放行根据游戏名称模糊查询游戏

        /**
         * 游戏测评文章管理员拦截器
         */
        registry.addInterceptor(new ArticleManagerHandlerInterceptor())
                .addPathPatterns(
                        "/article/addArticle"  // 拦截后台添加文章请求
                        , "/article/deleteArticleByAId"  //逻辑删除文章
                        , "/article/unDeletedMultiple"  //批量解除逻辑删除
                        , "/article/unDeleted" //解除逻辑删除
                        , "/article/deleteArticleByAIds" //根据文章 id 批量删除文章, 逻辑删除
                        , "/article/updateArticle" // 修改文章
                        , "/article/findArticleByAId" // 根据 id 查询一篇文章
                        , "/article.html" // 拦截测评文章管理页面
                        , "/article_add.html" // 拦截测评文章添加页面
                        , "/article_update.html")  // 拦截测评文章修改页面
                .excludePathPatterns();

        /**
         * 管理员拦截器
         */
        registry.addInterceptor(new AdminHandlerInterceptor())
                .addPathPatterns(
                        "/user.html"   // 拦截用户管理页面
                        , "/user_add.html" // 拦截添加用户页面
                        , "/user_update.html" // 拦截修改用户页面
                        , "/user/**" // 拦截 UserController 中的所有请求
                        , "/findAllRole"  // 拦截查询所有角色信息
                )
                .excludePathPatterns(
                        "/user/avatar/upload" // 放行用户头像上传
                        , "/user/login" //放行登录接口
                        , "/user/loginByPhone" //放行手机号登录接口
                        , "/user/checkLogin" // 放行检查登录接口
                        , "/user/logout" // 放行退出接口
                        , "/user/regist" // 放行注册接口
                        , "/user/avatar/{avatar}"); // 放行用户头像请求
    }
}
