package org.zxc.game_share.bean;

import lombok.Data;

/**
 * 游戏预览视频实体类
 * @author 知行成
 * @since 2022-03-04
 */
@Data
public class GameVideo {
    
    private Integer vId;

    private Long size;

    private String url;

    private Game game;
}
