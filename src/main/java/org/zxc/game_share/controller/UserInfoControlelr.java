package org.zxc.game_share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zxc.game_share.bean.Msg;
import org.zxc.game_share.bean.User;
import org.zxc.game_share.service.UserService;
import java.util.Date;

/**
 * 用户个人信息控制层
 * @author 知行成
 * @since 2022-3-22
 */
@RestController
public class UserInfoControlelr {

    @Autowired
    UserService userService;

    // 根据uid查询用户个人信息，
    // 查询部分信息，uid、用户名、年龄、密码、性别
    @GetMapping("/findPersonInfoByUid")
    public Msg findPersonInfoByUid(@RequestParam Integer uid){
        // 先判断 uid是否为空
        if (uid == null || uid.equals("")){
            return Msg.fail(403, "错误，uid 为空!");
        }
        User userByUid = userService.findUserByUid(uid);
        User user = new User();
        user.setUId(userByUid.getUId());
        user.setUsername(userByUid.getUsername());
        user.setPassword(userByUid.getPassword());
        user.setAge(userByUid.getAge());
        user.setSex(userByUid.getSex());
        return Msg.success().add("user", user);
    }

    // 修改用户个人信息，给前台使用的接口
    @PostMapping("/updatePersonInfo")
    public Msg updatePersonInfo(User user){
//        System.out.println(user);
        // 判断是否为空
        if (user == null){
            return Msg.fail(403, "错误，user对象为null");
        }
        // 判断uid是否为空
        if (user.getUId() == null){
            return Msg.fail(403, "错误，uid为null");
        }
        if (user.getAge() != null){
            // 判断年龄是否符合正常人的寿命
            if (user.getAge() < 0 || user.getAge() > 199){
                return Msg.fail(403, "错误，年龄不在 0 - 199 之间！");
            }
        }
        // 判断密码是否修改过
        User userByUid = userService.findUserByUid(user.getUId());
        if (!userByUid.getPassword().equals(user.getPassword())){
            // 先进行密码长度校验
            if (user.getPassword().length() < 4 || user.getPassword().length() > 16){
                return Msg.fail(403, "错误，密码长度不在 4 - 16位之间！");
            }
            // 密码已经修改过
            // 将新密码进行MD5加密
            String hashedPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).toString();
            user.setPassword(hashedPassword);
        }
        user.setGmtModified(new Date());
        // 到了这里基本校验完了
        // 调用业务层来修改用户的个人信息，修改密码，年龄，性别
        int i = userService.updatePersonInfoByUid(user);
        return Msg.success(200, "修改成功！");
    }
}
