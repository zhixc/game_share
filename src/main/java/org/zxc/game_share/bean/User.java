package org.zxc.game_share.bean;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类
 *
 * @author 知行成
 * @since 2021-09-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer uId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别，女: 0  男: 1  未知: 2
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 逻辑删除，1：删除，0：保留
     */
    private Integer isDeleted;

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
     * 外键关联角色权限表的主键
     */
    private Role role;

    /**
     * 用户头像链接
     */
    private String avatar;

//    /**
//     * 收藏的游戏列表
//     */
//    private List<Game> gameList;

    // 手机号
    private String  phone;
}
