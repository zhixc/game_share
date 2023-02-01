package org.zxc.game_share.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zxc.game_share.bean.Favorite;
import java.util.List;


/**
 * 游戏收藏数据库持久层
 * @author 知行成
 * @since 2022-02-27
 */
@Mapper
public interface FavoriteMapper {
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
    public int deleteFavoriteByGameIdAndUId(
            @Param("gameId") Integer gameId,
            @Param("uId") Integer uId);

    /**
     * 根据游戏id和用户id查询收藏记录
     * @param gameId
     * @param uId
     * @return
     */
    public Favorite findFavoriteByGameIdAndUId(
            @Param("gameId") Integer gameId,
            @Param("uId") Integer uId);

    /**
     * 查询当前用户的所有游戏收藏记录, 分页查询
     * @param uId
     * @param start
     * @param pageSize
     * @return
     */
    public List<Favorite> findAllFavorite(
            @Param("uId") Integer uId,
            @Param("start") int start,
            @Param("pageSize") int pageSize);

    /**
     * 根据用户 id 查询收藏的游戏共有多少条记录
     * @param uId
     * @return
     */
    public int findTotalCountByUid(Integer uId);
}
