package org.zxc.game_share.test.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zxc.game_share.bean.GameComment;
import org.zxc.game_share.mapper.GameCommentMapper;

import java.util.List;

@SpringBootTest
public class GameCommentMapperTest {

    @Autowired
    GameCommentMapper gameCommentMapper;

    @Test
    void testFindGameComment(){
        List<GameComment> gameCommentList = gameCommentMapper.findGameCommentByGameId(0, 10, 44, 0);
        for (GameComment gameComment : gameCommentList){
            System.out.println(gameComment.toString());
        }
    }
}
