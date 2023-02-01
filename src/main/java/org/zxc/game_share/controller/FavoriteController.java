package org.zxc.game_share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zxc.game_share.bean.*;
import org.zxc.game_share.service.FavoriteService;
import org.zxc.game_share.service.GameService;
import org.zxc.game_share.service.UserService;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 游戏收藏控制层
 * @author 知行成
 * @since 2022-02-27
 */
@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;

    @Autowired
    UserService userService;

    @Autowired
    GameService gameService;

    /**
     * 查询游戏是否已经收藏
     * @param gid
     * @param request
     * @return
     */
    @PostMapping("/isFavorite")
    public Msg isFavorite(Integer gid, HttpServletRequest request){
        // 先判断 gameId是否为空
        if (null == gid){
            return Msg.fail(403, "错误，gameId为null");
        }
        // 从session会话中取出用户信息
        User user = (User) request.getSession().getAttribute("USER");
        // 如果用户未登录，那么直接返回
        if (null == user){
            return Msg.fail(403, "错误，用户未登录");
        }
        // 调用 mapper层，查询
        Favorite favorite = favoriteService.findFavoriteByGameIdAndUId(gid, user.getUId());
        if (null != favorite){
            return Msg.success(200, "游戏已经收藏过了");
        }else {
            return Msg.fail(403, "游戏未收藏");
        }
    }

    /**
     * 添加游戏收藏
     * @param gid
     * @param request
     * @return
     */
    @PostMapping("/addFavorite")
    public Msg addFavorite(Integer gid, HttpServletRequest request){
        // 先判断 gameId是否为空
        if (null == gid){
            return Msg.fail(403, "错误，gameId为null");
        }
        // 从session会话中取出用户信息
        User user = (User) request.getSession().getAttribute("USER");
        // 如果用户未登录，那么直接返回
        if (null == user){
            return Msg.fail(403, "错误，用户未登录"); // 这行代码并没有效果，因为登录拦截器会拦截没有登录的用户
        }
        System.out.println("走到这里了");
        // 调用 mapper层，查询
        Favorite favorite = favoriteService.findFavoriteByGameIdAndUId(gid, user.getUId());
        if (null != favorite){
            return Msg.success(200, "游戏已经收藏过了");
        }else {
            // 游戏还未收藏
            User user1 = userService.findUserByUid(user.getUId());
            Game game = gameService.findGameByGid(gid, 0);
            // 调用mapper层收藏
            Favorite favorite1 = new Favorite(user, game, new Date(), new Date());
            favoriteService.addFavorite(favorite1);
            return Msg.success(200, "游戏收藏成功");
        }
    }

    /**
     * 查询用户收藏的游戏列表数据，分页查询
     * @param currentPage
     * @param request
     * @return
     */
    @GetMapping("/favoriteListWithPage")
    public Msg favoriteListWithPage(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "5") Integer pageSize,
            HttpServletRequest request){
        // 调用业务层
        User user = (User) request.getSession().getAttribute("USER");
        if (null == user){
            return Msg.fail(403, "错误，未登录！");
        }
        PageBean pb = favoriteService.findAllFavorite(user.getUId(), currentPage, pageSize);

        return Msg.success(200, "查询游戏收藏列表成功").add("pb", pb);
    }

    // 移除收藏
    @GetMapping("/removeFavorite")
    public Msg removeFavorite(@RequestParam("gid") Integer gid, HttpServletRequest request){
        if (null == gid){
            return Msg.fail(403, "错误，没有选中要移除的游戏");
        }
        // 从session会话中取出用户信息
        User user = (User) request.getSession().getAttribute("USER");
        if (null == user){
            return Msg.fail(403, "错误，用户没有登录");
        }
        int i = favoriteService.deleteFavoriteByGameIdAndUId(gid, user.getUId());
        return Msg.success();
    }
}
