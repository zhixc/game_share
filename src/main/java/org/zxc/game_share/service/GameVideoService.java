package org.zxc.game_share.service;

import org.zxc.game_share.bean.GameVideo;

import java.util.List;

/**
 * 游戏预览视频业务接口层
 * @author 知行成
 * @since 2022-03-04
 */
public interface GameVideoService {

    /**
     * 添加一个游戏预览视频
     * @param gameVideo
     * @return
     */
    int addGameVideo(GameVideo gameVideo);

    /**
     * 根据游戏gid查询游戏预览视频
     * @param gid
     * @return
     */
    List<GameVideo> findGameVideoByGid(Integer gid);
}
