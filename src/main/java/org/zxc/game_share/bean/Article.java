package org.zxc.game_share.bean;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 游戏测评文章类
 *
 * @author 知行成
 * @since 2021-09-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private Integer aId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 逻辑删除，1：删除，0：保留
     */
    private Integer isDeleted;

    /**
     * 文章审核状态，1：审核通过，0：未通过，2：审核中
     */
    private Integer isChecked;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date gmtModified;

    /**
     * 外键关联用户表主键，表明文章是谁发布的
     */
    private User user;

}
