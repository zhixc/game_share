package org.zxc.game_share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zxc.game_share.bean.Msg;
import org.zxc.game_share.bean.Role;
import org.zxc.game_share.service.RoleService;
import java.util.List;

/**
 * 角色控制层
 * @author 知行成
 * @since 2021-11-12
 */
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/findAllRole")
    public Msg findAllRole(){
        List<Role> roleList = roleService.findAllRole();
        return Msg.success().add("roleList", roleList);
    }
}
