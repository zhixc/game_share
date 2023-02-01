package org.zxc.game_share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zxc.game_share.bean.*;
import org.zxc.game_share.service.GameCommentService;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 游戏评论控制层
 *
 * @author 知行成
 * @since 2022-03-22
 */
@RestController
@RequestMapping("/gameComment")
public class GameCommentController {

    @Autowired
    GameCommentService gameCommentService;

    // 前台使用添加游戏评论
    @PostMapping("/addGameComment")
    public Msg addGameComment(GameComment gameComment, Integer gid, HttpServletRequest request) {
//        System.out.println(gameComment);
//        System.out.println(gid);
        if (gameComment == null) {
            return Msg.fail(403, "错误，gameComment为null");
        }
        if (gid == null) {
            return Msg.fail(403, "错误，gid为null");
        }
        // 判断isDeleted是否为空，如果为空，说明是前台添加评论
        // 如果不为空，说明是非法行为，因为我不打算做后台的添加评论行为
        if (gameComment.getIsDeleted() != null) {
            return Msg.fail(403, "错误，非法行为！");
        }
        // 判断评论内容是否为空
        if (gameComment.getCommentContent().length() <= 0) {
            return Msg.fail(403, "错误，你好歹说句话吧！");
        }

        // 判断评论长度是否大于200
        if (gameComment.getCommentContent().length() > 200){
            return Msg.fail(403, "错误，评论长度不能超过200字！");
        }

        gameComment.setGmtCreate(new Date());
        gameComment.setGmtModified(new Date());
        gameComment.setIsDeleted(0);

        // 这里最好还是用根据 gid 查询游戏，因为new出来的游戏，
        // 如果gid有误，那么写到数据库表中是会失败的
        Game game = new Game(gid, null, null, null,
                null, null, null, null,
                null, null, null, null, null);
        gameComment.setGame(game);
        // 从session会话中取出用户信息
        User user = (User) request.getSession().getAttribute("USER");
        if (null == user) {
            return Msg.fail(403, "错误，用户为null，用户未登录");
        }
        gameComment.setUser(user);
        int i = gameCommentService.addGameComment(gameComment);
        if (i > 0) {
            return Msg.success();
        }
        return Msg.fail(403, "添加失败");
    }

    // 后台修改游戏评论
    @PostMapping("/updateGameComment")
    public Msg updateGameComment(GameComment gameComment, Integer gid, Integer uid) {
        System.out.println("gameComment="+ gameComment);
        System.out.println("gid="+gid);
        System.out.println("uid="+uid);

        gameComment.setGmtModified(new Date());
        // 调用业务层来写入
        int i = gameCommentService.updateGameComment(gameComment);
        if (i > 0){
            return Msg.success();
        }
        return Msg.fail(403, "错误，修改失败！");
    }

    // 后台单个删除或解除删除游戏评论
    @PostMapping("/deleteGameComment")
    public Msg deleteGameComment(Integer gameCommentId, Integer isDeleted) {
        if (gameCommentId == null) {
            return Msg.fail(403, "错误，gameCommentId为空");
        }
        // 调用业务层，解除逻辑删除方法
        int i = gameCommentService.deleteGameCommentById(gameCommentId, isDeleted, new Date());
        if (i > 0) {
            return Msg.success();
        }
        return Msg.fail(403, "错误。游戏评论控制层deleteGameComment()方法报错");
    }

    // 批量删除或逻辑删除游戏评论
    @PostMapping("/deleteGameCommentByIds")
    public Msg deleteGameCommentByIds(Integer isDeleted, HttpServletRequest request) {
        if (isDeleted == null){
            return Msg.fail(403, "错误，isDeleted为空");
        }
        String[] gameCommentId = request.getParameterValues("gameCommentId[]");
        if (null == gameCommentId) {
            return Msg.fail(403, "错误，没有选中要删除的行，空指针异常");
        }
        // 判断数组长度
        if (gameCommentId.length <= 0) {
            return Msg.fail(403, "错误，没有选中要删除的行！");
        }
        // 将字符串数组元素转为Integer类型，然后再调用删除的方法
        for (int i = 0; i < gameCommentId.length; i++) {
//            System.out.println(Integer.parseInt(gameCommentId[i]));
            gameCommentService.deleteGameCommentById(Integer.parseInt(gameCommentId[i]), isDeleted, new Date());
        }
        return Msg.success();
    }

    /**
     * 根据游戏 gid 查询游戏评论
     *
     * @param currentPage 当前页
     * @param pageSize    每页显示多少条记录数
     * @param gid         查询的是哪个游戏的评论
     * @param isDeleted   逻辑删除字段
     * @return
     */
    @GetMapping("/findGameCommentByGameIdWithPage")
    public Msg findGameCommentByGameIdWithPage(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam Integer gid,
            @RequestParam(defaultValue = "0") Integer isDeleted) {
        if (null == gid) {
            return Msg.fail(403, "错误， gameId 为空");
        }
        PageBean pb = gameCommentService.findGameCommentByGameId(currentPage, pageSize, gid, isDeleted);
        return Msg.success().add("pb", pb);
    }

    /**
     * 根据用户名 查询游戏评论
     *
     * @param currentPage 当前页
     * @param pageSize    每页显示多少条记录数
     * @param username    查询的是哪个用户的评论
     * @param isDeleted   逻辑删除字段
     * @return
     */
    @GetMapping("/findGameCommentByUsernameWithPage")
    public Msg findGameCommentByUsernameWithPage(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "0") Integer isDeleted) {

//        System.out.println("username=" + username);
        PageBean pb = gameCommentService.findGameCommentByUsername(currentPage, pageSize, username, isDeleted);
        return Msg.success().add("pb", pb);
    }

    // 根据id查询游戏评论
    @GetMapping("/findGameCommentById")
    public Msg findGameCommentById(Integer gameCommentId, Integer isDeleted){
        // 校验参数
        if (gameCommentId == null){
            return Msg.fail(403, "错误，gameCommentId为空！");
        }
        // 根据参数，调用业务层查询
        GameComment gameComment = gameCommentService.findGameCommentByGameCommentId(gameCommentId, isDeleted);
        return Msg.success().add("gameComment", gameComment);
    }
}
