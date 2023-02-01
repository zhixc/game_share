package org.zxc.game_share.test.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zxc.game_share.bean.Game;
import org.zxc.game_share.mapper.GameMapper;
import org.zxc.game_share.service.GameService;

@SpringBootTest
public class GameMapperTest {

    @Autowired
    GameMapper gameMapper;

    @Autowired
    GameService gameService;

    @Test
    void findGameByGidTest(){
//        Integer gid = 1;
//        Integer isDeleted = 0;
//        Game gameByGid = gameMapper.findGameByGid(gid, isDeleted);
//        System.out.println(gameByGid);
        Integer gid = 1;
        Integer isDeleted = 0;
        Game gameByGid = gameService.findGameByGid(gid, isDeleted);
        System.out.println(gameByGid);
    }
}
