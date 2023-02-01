package org.zxc.game_share.bean;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 游戏类型表
 *
 * Serializable序列化，因为需要存到redis缓存
 *
 * @author 知行成
 * @since 2021-09-11
 */
@Data
public class Genres implements Serializable {

    private Integer ggId;

    /**
     * 游戏类型名称
     */
    private String ggName;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
}
