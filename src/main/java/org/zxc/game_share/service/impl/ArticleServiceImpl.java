package org.zxc.game_share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zxc.game_share.bean.Article;
import org.zxc.game_share.bean.PageBean;
import org.zxc.game_share.mapper.ArticleMapper;
import org.zxc.game_share.service.ArticleService;

import java.util.List;

/**
 * 测评文章业务实现层
 * @author 知行成
 * @since 2022-03-01
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    // 注入mapper层
    @Autowired
    ArticleMapper articleMapper;

    @Override
    public int addArticle(Article article) {
        return articleMapper.addArticle(article);
    }

    @Override
    public int deleteArticleByAId(Integer aId) {
        return articleMapper.deleteArticleByAId(aId);
    }

    @Override
    public void updateArticle(Article article) {
        articleMapper.updateArticle(article);
    }

    @Override
    public Article findArticleByAId(Integer aId, Integer isDeleted, Integer isChecked) {
        return articleMapper.findArticleByAId(aId, isDeleted, isChecked);
    }

    @Override
    public PageBean<Article> findArticleByTitleWithPage(int currentPage, int pageSize, String title, Integer isDeleted, Integer isChecked) {
        PageBean<Article> pb = new PageBean<>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        int totalCount = articleMapper.findTotalCountByTitle(title, isDeleted, isChecked);
        pb.setTotalCount(totalCount);

        int totalPage = 0;
        int result = totalCount / pageSize;
        totalPage = totalCount % pageSize == 0 ? result : result + 1;

        pb.setTotalPage(totalPage);

        int start = (currentPage - 1) * pageSize;

        List<Article> articleList = articleMapper.findArticleByTitleWithPage(start, pageSize, title, isDeleted, isChecked);

        pb.setList(articleList);

        return pb;
    }

    @Override
    public int unDeleted(Integer aid) {
        int i = articleMapper.unDeleted(aid);
        return i;
    }

    @Override
    public PageBean<Article> findMyArticleByTitleWithPage(int currentPage, int pageSize, String title, Integer uid) {
        PageBean<Article> pb = new PageBean<>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        int totalCount = articleMapper.findTotalCountByTitleAndUID(title, uid);
        pb.setTotalCount(totalCount);

        int totalPage = 0;
        int result = totalCount / pageSize;
        totalPage = totalCount % pageSize == 0 ? result : result + 1;

        pb.setTotalPage(totalPage);

        int start = (currentPage - 1) * pageSize;

        List<Article> articleList = articleMapper.findArticleByTitleAndUIDWithPage(start, pageSize, title, uid);

        pb.setList(articleList);

        return pb;
    }
}
