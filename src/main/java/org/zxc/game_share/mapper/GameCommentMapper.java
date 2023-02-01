package org.zxc.game_share.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zxc.game_share.bean.GameComment;

import java.util.Date;
import java.util.List;

/**
 * 游戏评论数据库持久层
 *
 * @author 知行成
 * @since 2022-03-22
 */
@Mapper
public interface GameCommentMapper {

    // 添加游戏评论
    public int addGameComment(GameComment gameComment);

    // 修改游戏评论
    public int updateGameComment(GameComment gameComment);

    // 根据主键，逻辑删除游戏评论
    public int deleteGameCommentById(
            @Param("gameCommentId") Integer gameCommentId,
            @Param("isDeleted") Integer isDeleted,
            @Param("gmtModified") Date gmtModified);

    // 根据游戏id查询游戏评论，分页查询
    public List<GameComment> findGameCommentByGameId(
            @Param("start") int start,
            @Param("pageSize") Integer pageSize,
            @Param("gameId") Integer gameId,
            @Param("isDeleted") Integer isDeleted);

    // 根据游戏id查询一共有多少条评论
    public int findTotalCountByGameId(
            @Param("gameId") Integer gameId,
            @Param("isDeleted") Integer isDeleted);

    // 根据用户名查询一共有多少条评论记录
    int findTotalCountByUsername(
            @Param("username") String username,
            @Param("isDeleted") Integer isDeleted);

    // 根据用户名查询游戏评论，分页查询
    List<GameComment> findGameCommentByUsername(
            @Param("start") int start, @Param("pageSize") Integer pageSize,
            @Param("username") String username, @Param("isDeleted") Integer isDeleted);

    // 根据游戏评论主键查询
    GameComment findGameCommentByGameCommentId(
            @Param("gameCommentId") Integer gameCommentId,
            @Param("isDeleted") Integer isDeleted);
}
