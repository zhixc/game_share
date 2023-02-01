package org.zxc.game_share.service;

import org.zxc.game_share.bean.GameComment;
import org.zxc.game_share.bean.PageBean;

import java.util.Date;

/**
 * 游戏评论业务层
 * @author 知行成
 * @since 2022-03-22
 */
public interface GameCommentService {

    // 添加游戏评论
    public int addGameComment(GameComment gameComment);

    // 修改游戏评论
    public int updateGameComment(GameComment gameComment);

    // 根据主键，逻辑删除游戏评论
    public int deleteGameCommentById(Integer gameCommentId,
                                                  Integer isDeleted, Date gmtModified);

    // 根据游戏id查询游戏评论，分页查询
    public PageBean findGameCommentByGameId(Integer currentPage, Integer pageSize,
                                            Integer gameId, Integer isDeleted);
    // 根据用户名，分页查询游戏评论
    PageBean findGameCommentByUsername(Integer currentPage, Integer pageSize, String username, Integer isDeleted);

    // 根据游戏评论主键查询
    GameComment findGameCommentByGameCommentId(Integer gameCommentId, Integer isDeleted);
}
