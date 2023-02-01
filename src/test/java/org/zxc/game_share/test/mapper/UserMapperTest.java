package org.zxc.game_share.test.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zxc.game_share.bean.User;
import org.zxc.game_share.mapper.UserMapper;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

//    @Test
//    void updateUserByUidTest(){
//        userMapper.updateUserById(new User());
//    }
}
