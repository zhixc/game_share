package org.zxc.game_share.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.zxc.game_share.bean.Role;

import java.util.List;
/**
 * 角色数据库持久层
 * @author 知行成
 * @since 2021-11-12
 */
@Mapper
public interface RoleMapper {
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
