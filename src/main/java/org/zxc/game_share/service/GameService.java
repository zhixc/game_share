package org.zxc.game_share.service;

import org.zxc.game_share.bean.Game;
import org.zxc.game_share.bean.PageBean;
/**
 * 游戏业务接口层
 * @author 知行成
 * @since 2021-11-4
 */
public interface GameService {
    /**
     * 根据游戏分类主键ggId查询游戏
     *
     * @param currentPage
     * @param pageSize
     * @param ggId
     * @return
     */
    public PageBean findGameByggId(Integer currentPage, Integer pageSize, Integer ggId);

    /**
     * 根据游戏名称，模糊查询游戏数据，并作分页
     * @param currentPage
     * @param pageSize
     * @param gameName
     * @param isDeleted
     * @return
     */
    public PageBean findGameByName(Integer currentPage, Integer pageSize, String gameName, Integer isDeleted);

    /**
     * 根据游戏主键gid查询游戏
     * @param gid
     * @return
     */
    public Game findGameByGid(Integer gid, Integer isDeleted);

    /**
     * 添加游戏
     * @param game
     * @return
     */
    public int addGame(Game game);

    /**
     * 修改游戏
     * @param game
     * @return
     */
    public int updateGame(Game game);

    /**
     * 根据 id 删除游戏
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
