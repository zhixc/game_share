package org.zxc.game_share.bean;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 游戏收藏类
 *
 * @author 知行成
 * @since 2021-09-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {

    /**
     * 外键关联用户表主键，表示是哪个用户收藏
     */
    private User user;

    /**
     * 外键关联游戏资讯表主键，表示收藏的游戏信息
     */
    private Game game;

    /**
     * 创建时间，表示用户收藏这个游戏的时间
     */
    private Date gmtCreate;

    /**
     * 修改时间，表示用户最近一次修改收藏时间
     */
    private Date gmtModified;

}
