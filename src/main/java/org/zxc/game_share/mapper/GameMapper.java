package org.zxc.game_share.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zxc.game_share.bean.Game;
import java.util.List;

/**
 * 游戏数据持久层
 * @author 知行成
 * @since 2021-11-4
 */
@Mapper
public interface GameMapper {
    /**
     * 根据游戏分类主键ggId查询游戏, 分页查询
     *
     * @param start
     * @param pageSize
     * @param ggId
     * @return
     */
    public List<Game> findGameByggId(@Param("start") int start,
                                     @Param("pageSize") Integer pageSize,
                                     @Param("ggId") Integer ggId);

    /**
     * 根据游戏分类主键 ggId 查询游戏记录条数
     * @return
     * @param ggId
     */
    public int findTotalCountByggId(Integer ggId);

    /**
     * 根据游戏名称，模糊查询游戏一共有多少条数据
     * @param gameName
     * @param isDeleted
     * @return
     */
    public int findTotalCountByName(@Param("gameName") String gameName,
                                    @Param("isDeleted") Integer isDeleted);

    /**
     * 根据游戏名称模糊查询，分页查询
     * @param start
     * @param pageSize
     * @param gameName
     * @param isDeleted
     * @return
     */
    public List<Game> findGameByName(
            @Param("start") int start,
            @Param("pageSize") Integer pageSize,
            @Param("gameName") String gameName,
            @Param("isDeleted") Integer isDeleted);

    /**
     * 根据游戏主键gid查询游戏
     * @param gid
     * @return
     */
    public Game findGameByGid(@Param("gid") Integer gid,
                              @Param("isDeleted") Integer isDeleted);

    /**
     * 添加游戏
     * @param game
     * @return
     */
    public int addGame(Game game);

    /**
     * 修改游戏
     * @return
     */
    public int updateGame(Game game);

    /**
     * 删除游戏(逻辑删除)
     * @param gid
     * @return
     */
    public int deleteGameByGid(Integer gid);

    /**
     * 解除逻辑删除
     * @param gid
     * @return
     */
    public int unDeleted(Integer gid);
}
