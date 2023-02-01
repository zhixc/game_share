package org.zxc.game_share.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zxc.game_share.bean.Genres;
import java.util.List;

/**
 * 游戏分类数据持久层
 * @author 知行成
 * @since 2021-11-4
 */
@Mapper
public interface GenresMapper {

    /**
     * 查询游戏分类总的记录数
     * @return
     */
    public int findTotalCount();

    /**
     * 分页查询所有游戏分类，模糊查询游戏类型名称
     * @return
     */
    public List<Genres> findByggNameWithPage(
            @Param("start") int start,
            @Param("pageSize") int pageSize,
            @Param("ggName") String ggName);
}
