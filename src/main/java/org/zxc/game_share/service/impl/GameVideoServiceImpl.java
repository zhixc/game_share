package org.zxc.game_share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zxc.game_share.bean.GameVideo;
import org.zxc.game_share.mapper.GameVideoMapper;
import org.zxc.game_share.service.GameVideoService;

import java.util.List;

/**
 * 游戏预览视频业务实现层
 * @author 知行成
 * @since 2022-03-04
 */
@Service
public class GameVideoServiceImpl implements GameVideoService {

    @Autowired
    GameVideoMapper gameVideoMapper;

    /**
     * 添加一个游戏预览视频
     * @param gameVideo
     * @return
     */
    @Override
    public int addGameVideo(GameVideo gameVideo) {
        int i = gameVideoMapper.addGameVideo(gameVideo);
        return i;
    }

    /**
     * 根据游戏gid查询游戏预览视频
     * @param gid
     * @return
     */
    @Override
    public List<GameVideo> findGameVideoByGid(Integer gid) {
        return gameVideoMapper.findGameVideoByGid(gid);
    }
}
