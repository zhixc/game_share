package org.zxc.game_share.service;

import org.zxc.game_share.bean.Genres;
import org.zxc.game_share.bean.PageBean;

/**
 * 游戏分类业务层
 * @author 知行成
 * @since 2021-11-4
 */
public interface GenresService {

    /**
     * 查询所有游戏分类
     * @return
     * @param currentPage
     * @param pageSize
     * @param ggName
     */
    public PageBean<Genres> findGenres(int currentPage, int pageSize, String ggName);
}
