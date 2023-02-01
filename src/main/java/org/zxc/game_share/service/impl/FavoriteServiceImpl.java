package org.zxc.game_share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zxc.game_share.bean.Favorite;
import org.zxc.game_share.bean.PageBean;
import org.zxc.game_share.mapper.FavoriteMapper;
import org.zxc.game_share.service.FavoriteService;
import java.util.List;

/**
 * 游戏收藏业务实现层
 * @author 知行成
 * @since 2022-02-27
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Override
    public int addFavorite(Favorite favorite) {
        return favoriteMapper.addFavorite(favorite);
    }

    @Override
    public int deleteFavoriteByGameIdAndUId(Integer gameId, Integer uId) {
        return favoriteMapper.deleteFavoriteByGameIdAndUId(gameId, uId);
    }

    @Override
    public Favorite findFavoriteByGameIdAndUId(Integer gameId, Integer uId) {
        Favorite favorite = favoriteMapper.findFavoriteByGameIdAndUId(gameId, uId);
        return favorite;
    }

    @Override
    public PageBean findAllFavorite(Integer uId, Integer currentPage, Integer pageSize) {
        PageBean<Favorite> pb = new PageBean<>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        int totalCount = favoriteMapper.findTotalCountByUid(uId);
        pb.setTotalCount(totalCount);

        int totalPage = 0;
        int result = totalCount / pageSize;
        totalPage = totalCount % pageSize == 0 ? result : result + 1;
        pb.setTotalPage(totalPage);

        int start = (currentPage - 1) * pageSize;

        List<Favorite> favoriteList = favoriteMapper.findAllFavorite(uId, start, pageSize);
        pb.setList(favoriteList);
        return pb;
    }
}
