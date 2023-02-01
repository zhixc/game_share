package org.zxc.game_share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zxc.game_share.bean.Article;
import org.zxc.game_share.bean.Msg;
import org.zxc.game_share.bean.PageBean;
import org.zxc.game_share.bean.User;
import org.zxc.game_share.service.ArticleService;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 测评文章控制层
 *
 * @author 知行成
 * @since 2022-03-1
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    // 所有用户皆可使用（不用拦截）
    // 根据 id 查询一篇文章(这是给前台文章详情页面使用的接口)
    @GetMapping("/findArticleByAIdForArticleDetail")
    public Msg findArticleByAIdForArticleDetail(Integer aid) {
        if (aid == null) {
            return Msg.fail(403, "错误！文章aid为null");
        }
        Article articleByAId = articleService.findArticleByAId(aid, 0, 1);
        if (articleByAId == null){
            return Msg.fail(403, "错误，文章不存在！");
        }
        return Msg.success().add("article", articleByAId);
    }

    // 前台普通用户可使用
    // 根据id查询文章，这是给前台普通用户修改页面使用的
    @GetMapping("/findArticleByAIdForArticleUpdate")
    public Msg findArticleByAIdForArticleUpdate(Integer aid) {
        if (aid == null) {
            return Msg.fail(403, "错误！文章aid为null");
        }
        Article articleByAId = articleService.findArticleByAId(aid, 0, null);
        if (articleByAId == null){
            return Msg.fail(403, "错误，文章不存在！");
        }
        return Msg.success().add("article", articleByAId);
    }

    // 前台普通用户可使用查询我的文章，根据标题查询
    // 这是给前台普通用户使用的接口
    @GetMapping("/findMyArticleByTitleWtithPage")
    public Msg findMyArticleByTitleWtithPage(@RequestParam(defaultValue = "1") Integer currentPage,
                                             @RequestParam(defaultValue = "5") Integer pageSize,
                                             @RequestParam(defaultValue = "") String title, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("USER");
        if (user == null){
            return Msg.fail(403, "错误，用户未登录！");
        }
        Integer uid = user.getUId();
        PageBean<Article> pb = articleService.findMyArticleByTitleWithPage(currentPage, pageSize, title, uid);
        return Msg.success().add("pb", pb);
    }

    // 前台普通用户可使用的删除文章
    @PostMapping("/deleteArticleByAIdForFront")
    public Msg deleteArticleByAIdForFront(@RequestParam Integer aid, HttpServletRequest request){
        // 删除之前检查是否为当前用户操作
        User user = (User) request.getSession().getAttribute("USER");
        Article articleByAId = articleService.findArticleByAId(aid, 0, null);
        Integer uId = articleByAId.getUser().getUId();
        if (user.getUId() != uId){
            return Msg.fail(403, "错误，没有权限删除");
        }
        int i = articleService.deleteArticleByAId(aid);
        if (i > 0){
            return Msg.success(200, "删除成功");
        }else {
            return Msg.fail(403, "错误，删除失败！");
        }
    }

    // 前台普通用户可使用的修改文章
    @PostMapping("/updateArticleForFront")
    public Msg updateArticleForFront(Integer aid, String title, String content, HttpServletRequest request){
        // 根据用户id，查询文章
        // 如果查询结果为空，说明这篇文章不存在，
        // 如果存在，就比较 uid是否与session中的用户uid一致
        // 如果不一致，那么没有权限修改，直接返回错误信息
        Article articleByAId = articleService.findArticleByAId(aid, 0, null);
        User user = (User) request.getSession().getAttribute("USER");
        Integer uId = articleByAId.getUser().getUId();
        if (user.getUId() != uId){
            // 不相等
            return Msg.fail(403, "错误，没有权限修改文章！");
        }
        Article article = new Article();
        article.setAId(aid); // 文章id
        article.setTitle(title); // 文章标题
        article.setContent(content); // 文章内容
        article.setGmtModified(new Date()); // 修改时间
        article.setUser(user); // 作者信息
        article.setIsDeleted(0); // 0：保留
        article.setIsChecked(2); // 2：审核中

        // 调用service层将信息写入
        articleService.updateArticle(article);
        return Msg.success(200, "文章修改成功！");
    }

    // 添加文章（前台普通用户可用），
    // 要在测评文章管理员拦截器那里放行，在登录拦截器那里拦截
    @PostMapping("/addArticleForFront")
    public Msg addArticleForFront(String title, String content, HttpServletRequest request) {
        // 文章标题长度校验
        if (title.length() <= 0){
            return Msg.fail(403, "文章标题不能为空！");
        }
        // 文章内容长度校验
        if (content.length() <= 0) {
            return Msg.fail(403, "错误，文章内容不能为空");
        }
        Article article = new Article();
        article.setTitle(title); // 设置标题
        article.setContent(content); // 设置内容
        article.setIsChecked(2); //审核中
        article.setIsDeleted(0); //保留
        article.setGmtCreate(new Date()); // 创建时间
        article.setGmtModified(new Date()); // 修改时间

        // 从session会话中取出用户信息
        User user = (User) request.getSession().getAttribute("USER");
        if (user == null) {
            return Msg.fail(403, "错误，用户还没有登录");
        }
        // 设置作者信息
        article.setUser(user);
        // 调用业务层
        int i = articleService.addArticle(article);
        return i > 0 ? Msg.success(200, "添加文章成功！") : Msg.success();

    }

    // 添加文章（后台专用）
    @PostMapping("/addArticle")
    public Msg addArticle(Article article, HttpServletRequest request) {
        article.setGmtCreate(new Date()); // 创建时间
        article.setGmtModified(new Date());// 修改时间
        if (article.getIsChecked() == null) {
            article.setIsChecked(2); // 审核中
        }
        if (article.getIsDeleted() == null) {
            article.setIsDeleted(0); // 默认保留
        }
        // 从session会话中取出用户id，将其写入
        User user = (User) request.getSession().getAttribute("USER");
        article.setUser(user);
        int i = articleService.addArticle(article);
        return Msg.success(200, "添加成功");
    }

    // 根据文章 id 删除一篇文章, 逻辑删除
    @PostMapping("/deleteArticleByAId")
    public Msg deleteArticleByAId(Integer aid) {
        if (null == aid) {
            return Msg.fail(400, "错误，没有 aid");
        }
        int i = articleService.deleteArticleByAId(aid);
        return Msg.success(200, "删除成功");
    }

    // 批量解除逻辑删除
    @RequestMapping("/unDeletedMultiple")
    public Msg unDeletedMultiple(HttpServletRequest request) {
        String[] strArr = request.getParameterValues("arr[]");
        if (null == strArr) {
            return Msg.fail(400, "错误，没有选中要解除删除的行, 空指针异常");
        }
        if (strArr.length <= 0) {
            // 没有要删除的
            return Msg.fail(400, "错误，没有选中要解除删除的行");
        }
        // 可以不用转为int型数组，for循环遍历时可以直接调用业务层
        for (int i = 0; i < strArr.length; i++) {
            articleService.unDeleted(Integer.parseInt(strArr[i]));
        }
        return Msg.success();
    }

    // 解除逻辑删除
    @RequestMapping("/unDeleted")
    public Msg unDeleted(Integer aid) {
        int i = articleService.unDeleted(aid);
        return Msg.success();
    }

    // 根据文章 id 批量删除文章, 逻辑删除
    @PostMapping("/deleteArticleByAIds")
    public Msg deleteArticleByAIds(HttpServletRequest request) {
        String[] aid = request.getParameterValues("aid[]");
        if (aid.length <= 0) {
            return Msg.fail(400, "错误，没有选中要删除的行！");
        } else {
            // 将字符串数组转化为数组
            int[] arr = new int[aid.length];
            for (int i = 0; i < aid.length; i++) {
                arr[i] = Integer.parseInt(aid[i]);
            }
            // 使用for循环逐条删除
            for (int i : arr) {
                articleService.deleteArticleByAId(i);
            }
        }
        return Msg.success(200, "删除成功");
    }

    // 修改文章
    @PostMapping("/updateArticle")
    public Msg updateArticle(Article article) {
        System.out.println(article);
        if (article.getIsDeleted() == null) {
            article.setIsDeleted(0); // 保留
        }
        if (article.getIsChecked() == null) {
            article.setIsChecked(2); //审核中
        } else if (article.getIsChecked() != 0 && article.getIsChecked() != 1 && article.getIsChecked() != 2) {
            return Msg.fail(403, "错误，审核状态错误");
        }

        article.setGmtModified(new Date()); // 修改时间
        articleService.updateArticle(article);
        return Msg.success(200, "修改成功");
    }

    // 根据 id 查询一篇文章
    @GetMapping("/findArticleByAId")
    public Msg findArticleByAId(Integer aid, Integer isDeleted, Integer isChecked) {
        if (aid == null) {
            return Msg.fail(403, "错误！文章aid为null");
        } else if (isDeleted == null) {
            return Msg.fail(403, "错误，逻辑删除为null");
        } else if (isDeleted != 1 && isDeleted != 0) {
            return Msg.fail(403, "错误，逻辑删除不正确");
        } else if (null == isChecked) {
            return Msg.fail(403, "错误，审核状态为null");
        } else if (0 != isChecked && 1 != isChecked && 2 != isChecked) {
            return Msg.fail(403, "错误，审核状态不正确");
        }
        Article articleByAId = articleService.findArticleByAId(aid, isDeleted, isChecked);
        if (articleByAId == null){
            return Msg.fail(403, "错误，文章不存在！");
        }
        return Msg.success().add("article", articleByAId);
    }

    // 根据文章标题模糊查询，并作分页
    @GetMapping("/findArticleByTitleWithPage")
    public Msg findArticleByTitleWithPage(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "0") Integer isDeleted,
            @RequestParam(defaultValue = "1") Integer isChecked) {
//        System.out.println("文章标题"+title);
        PageBean<Article> pb = articleService.findArticleByTitleWithPage(currentPage, pageSize, title, isDeleted, isChecked);
        return Msg.success().add("pb", pb);
    }
}
