package org.zxc.game_share.test.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zxc.game_share.bean.Favorite;
import org.zxc.game_share.mapper.FavoriteMapper;
import java.util.List;

@SpringBootTest
public class FavoriteMapperTest {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Test
    void testfindTotalCountByUid() {
        int i = favoriteMapper.findTotalCountByUid(1);
        System.out.println(i);
    }

    @Test
    void testfindFavoriteByGameIdAndUId() {
        Favorite favorite = favoriteMapper.findFavoriteByGameIdAndUId(25, 4);

        if (favorite == null){
            System.out.println("为空");
        }else {
            System.out.println("有结果");
        }
        System.out.println(favorite);

    }

    @Test
    void testfindAllFavorite() {
        List<Favorite> favoriteList = favoriteMapper.findAllFavorite(24, 0, 10);
        for (Favorite favorite : favoriteList) {
            System.out.println(favorite);
        }
    }
}
