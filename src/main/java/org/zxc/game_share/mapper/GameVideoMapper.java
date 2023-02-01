package org.zxc.game_share.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.zxc.game_share.bean.GameVideo;
import java.util.List;

/**
 * 游戏预览视频数据库持久层
 * @author 知行成
 * @since 2022-03-04
 */
@Mapper
public interface GameVideoMapper {
    /**
     * 添加一个游戏预览视频
     * @param gameVideo
     * @return
     */
    public int addGameVideo(GameVideo gameVideo);

    /**
     * 根据 gid 查询游戏预览视频
     * @param gid
     * @return
     */
    List<GameVideo> findGameVideoByGid(Integer gid);
}
