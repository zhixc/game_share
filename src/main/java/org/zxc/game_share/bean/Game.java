package org.zxc.game_share.bean;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 游戏类
 *
 * @author 知行成
 * @since 2021-09-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    private Integer gId;

    /**
     * 游戏名称
     */
    private String gName;

    /**
     * 游戏封面链接
     */
    private String cover;

    /**
     * 游戏开发商
     */
    private String developer;

    /**
     * 游戏发行商
     */
    private String publisher;

    /**
     * 游戏评级星数，1-10分
     */
    private Integer gameScore;

    /**
     * 逻辑删除，1：删除，0：保留
     */
    private Integer isDeleted;

    /**
     * 游戏发行时间
     */
    private String releaseTime;

    /**
     * 创建时间
     * 添加注解，使得返回的格式为yyyy-MM-dd
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date gmtModified;

    /**
     * 外键关联游戏类型表主键
     */
    private Genres genres;

    private Integer genresId;

    /**
     * 游戏简介
     */
    private String gameReview;

}
