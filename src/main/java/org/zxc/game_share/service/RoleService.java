package org.zxc.game_share.service;

import org.zxc.game_share.bean.Role;

import java.util.List;

/**
 * 角色业务接口层
 * @author 知行成
 * @since 2021-11-12
 */
public interface RoleService {
    /**
     * 查询角色表的角色信息
     * @return
     */
    public List<Role> findAllRole();

    /**
     * 根据角色 id 查询角色信息
     * @param roleId
     * @return
     */
    Role findRoleByRoleId(Integer roleId);
}
