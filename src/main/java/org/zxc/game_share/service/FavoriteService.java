package org.zxc.game_share.service;

import org.zxc.game_share.bean.Favorite;
import org.zxc.game_share.bean.PageBean;

import java.util.List;

/**
 * 游戏收藏业务层
 * @author 知行成
 * @since 2022-02-27
 */
public interface FavoriteService {
    /**
     * 添加一条收藏记录
     * @param favorite
     * @return
     */
    public int addFavorite(Favorite favorite);

    /**
     * 移除收藏的游戏记录
     * @param gameId
     * @param uId
     * @return
     */
    public int deleteFavoriteByGameIdAndUId(Integer gameId, Integer uId);

    /**
     * 根据游戏id和用户id查询收藏记录
     * @param gameId
     * @param uId
     * @return
     */
    public Favorite findFavoriteByGameIdAndUId(Integer gameId, Integer uId);

    /**
     * 查询当前用户的所有游戏收藏记录, 分页查询
     * @param uId
     * @param currentPage
     * @param pageSize
     * @return
     */
    public PageBean findAllFavorite(Integer uId, Integer currentPage, Integer pageSize);
}
