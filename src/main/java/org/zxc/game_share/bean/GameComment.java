package org.zxc.game_share.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 游戏评论类
 *
 * @author 知行成
 * @since 2022-03-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameComment {

    /**
     * 主键
     */
    private Integer gameCommentId;

    /**
     * 外键关联游戏表主键
     */
    private Game game;

    /**
     * 外键关联用户表主键
     */
    private User user;

    /**
     * 游戏评论内容
     */
    private String commentContent;

    /**
     * 逻辑删除, 1:删除，0:保留
     */
    private Integer isDeleted;
    /**
     * 创建时间（游戏评论发表时间）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
}
