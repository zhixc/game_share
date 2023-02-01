package org.zxc.game_share.service;

import org.zxc.game_share.bean.Article;
import org.zxc.game_share.bean.PageBean;

/**
 * 测评文章业务层
 * @author 知行成
 * @since 2022-03-01
 */
public interface ArticleService {

    /**
     * 添加文章
     * @param article
     * @return
     */
    public int addArticle(Article article);

    /**
     * 根据文章id删除文章
     * @param aId
     * @return
     */
    public int deleteArticleByAId(Integer aId);

    /**
     * 文章修改
     * @param article
     * @return
     */
    public void updateArticle(Article article);

    /**
     * 根据文章id查询一篇文章
     * @param aId
     * @return
     */
    public Article findArticleByAId(Integer aId, Integer isDeleted, Integer isChecked);


    /**
     * 根据文章标题分页模糊查询文章
     * @param currentPage
     * @param pageSize
     * @param title
     * @return
     */
    public PageBean<Article> findArticleByTitleWithPage(int currentPage, int pageSize, String title, Integer isDeleted, Integer isChecked);

    /**
     * 解除逻辑删除
     * @param aid
     * @return
     */
    int unDeleted(Integer aid);

    /**
     * 查询我的文章，根据标题查询（前台普通用户使用）
     * @param currentPage
     * @param pageSize
     * @param title
     * @param uid
     * @return
     */
    PageBean<Article> findMyArticleByTitleWithPage(int currentPage, int pageSize, String title, Integer uid);
}
