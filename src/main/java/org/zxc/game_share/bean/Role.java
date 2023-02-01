package org.zxc.game_share.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色实体类
 * @author 知行成
 * @since 2021-09-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private Integer rId;

    /**
     * 角色名称
     */
    private String roleName;

}
