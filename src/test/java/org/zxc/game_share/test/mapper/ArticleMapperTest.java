package org.zxc.game_share.test.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zxc.game_share.bean.Article;
import org.zxc.game_share.bean.User;
import org.zxc.game_share.mapper.ArticleMapper;
import org.zxc.game_share.mapper.UserMapper;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class ArticleMapperTest {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    UserMapper userMapper;


    @Test
    public void addArticleTest(){
        User userByUid = userMapper.findUserByUid(1);
        Article article = new Article(4, "文章4", null, 0, 1, new Date(), new Date(), userByUid);
        int i = articleMapper.addArticle(article);
        System.out.println("受影响的行数为："+i);
    }

    @Test
    public void findArticleByAIdTest(){
        Article articleByAId = articleMapper.findArticleByAId(4, 0, 1);
        System.out.println("查询结果为："+articleByAId);
    }

    @Test
    public void updateArticleTest(){
        User userByUid = userMapper.findUserByUid(1);
        Article article = new Article(4, "文章44", null, 0, 1, new Date(), new Date(), userByUid);
        articleMapper.updateArticle(article);
        System.out.println("修改成功");


    }

    @Test
    public void findTotalCountByTitleTest(){
        int i = articleMapper.findTotalCountByTitle("文章", 0, 1);
        System.out.println(i);
    }

    @Test
    public void findArticleByTitleWithPageTest(){
        List<Article> articleList = articleMapper.findArticleByTitleWithPage(1, 2, "", 0, 1);
        for (Article article : articleList){
            System.out.println(article);
        }
    }

    @Test
    public void deleteArticleByAIdTest(){
        int i = articleMapper.deleteArticleByAId(3);
        System.out.println(i);
    }
}
