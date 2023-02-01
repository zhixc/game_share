package org.zxc.game_share.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zxc.game_share.bean.Article;

import java.util.List;

/**
 * 测评文章数据库持久层
 *
 * @author 知行成
 * @since 2022-03-1
 */
@Mapper
public interface ArticleMapper {

    /**
     * 添加文章
     *
     * @param article
     * @return
     */
    public int addArticle(Article article);

    /**
     * 根据文章id删除文章 逻辑删除
     *
     * @param aId
     * @return
     */
    public int deleteArticleByAId(Integer aId);

    /**
     * 文章修改
     *
     * @param article
     * @return
     */
    public void updateArticle(Article article);

    /**
     * 根据文章id查询一篇文章
     *
     * @param aId
     * @return
     */
    public Article findArticleByAId(@Param("aId") Integer aId,
                                    @Param("isDeleted") Integer isDeleted,
                                    @Param("isChecked") Integer isChecked);

    /**
     * 根据文章标题模糊查询文章数量
     *
     * @param title
     * @return
     */
    public int findTotalCountByTitle(
            @Param("title") String title,
            @Param("isDeleted") Integer isDeleted,
            @Param("isChecked") Integer isChecked);

    /**
     * 根据文章标题分页模糊查询文章
     *
     * @param start
     * @param pageSize
     * @param title
     * @return
     */
    public List<Article> findArticleByTitleWithPage(
            @Param("start") int start,
            @Param("pageSize") int pageSize,
            @Param("title") String title,
            @Param("isDeleted") Integer isDeleted,
            @Param("isChecked") Integer isChecked);

    /**
     * 解除逻辑删除
     *
     * @param aid
     * @return
     */
    int unDeleted(Integer aid);

    // 根据文章标题和用户 uid查询总记录数
    int findTotalCountByTitleAndUID(@Param("title") String title,
                                    @Param("uid") Integer uid);

    // 根据文章标题和用户 uid 查询文章
    List<Article> findArticleByTitleAndUIDWithPage(
            @Param("start") int start, @Param("pageSize") int pageSize,
            @Param("title") String title, @Param("uid") Integer uid);
}
