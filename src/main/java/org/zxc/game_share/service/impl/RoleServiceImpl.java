package org.zxc.game_share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zxc.game_share.bean.Role;
import org.zxc.game_share.mapper.RoleMapper;
import org.zxc.game_share.service.RoleService;

import java.util.List;

/**
 * 角色业务实现层
 * @author 知行成
 * @since 2021-11-12
 */
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询角色表的角色信息
     * @return
     */
    @Override
    public List<Role> findAllRole() {
        return roleMapper.findAllRole();
    }

    /**
     * 根据角色 id 查询角色信息
     * @param roleId
     * @return
     */
    @Override
    public Role findRoleByRoleId(Integer roleId) {
        return roleMapper.findRoleByRoleId(roleId);
    }
}
