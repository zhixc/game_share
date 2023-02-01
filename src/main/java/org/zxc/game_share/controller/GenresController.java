package org.zxc.game_share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zxc.game_share.bean.Genres;
import org.zxc.game_share.bean.Msg;
import org.zxc.game_share.bean.PageBean;
import org.zxc.game_share.service.GenresService;

/**
 * 游戏分类控制层
 * @author 知行成
 * @since 2021-11-4
 */
@RestController
public class GenresController {

    @Autowired
    private GenresService genresService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 分页查询游戏分类，模糊查询
     * @param currentPage
     * @param pageSize
     * @param ggName
     * @return
     */
    @RequestMapping("/findGenres")
    public Msg findGenres(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "4") Integer pageSize,
            @RequestParam(defaultValue = "") String ggName){

        if (pageSize == 4){
            // 查询前4种游戏分类
            // 从redis中查询
            PageBean<Genres> pb41 = (PageBean<Genres>) redisTemplate.opsForValue().get("pb4");
            if (null != pb41){
                // redis中存在记录
                System.out.println("redis中已经有前4种游戏分类");
                return Msg.success().add("pb", pb41);
            }else {
                // redis缓存中没有这条记录
                // 从数据库中查询, 并写到 redis 缓存数据库中
                PageBean<Genres> pb4 = genresService.findGenres(currentPage, pageSize, ggName);
                redisTemplate.opsForValue().set("pb4", pb4);
                return Msg.success().add("pb", pb4);
            }
        }else {
            // 查询所有游戏分类
            // 从redis中查询
            PageBean<Genres> pb1 = (PageBean<Genres>) redisTemplate.opsForValue().get("pb");
            if (null != pb1){
                // redis中存在记录
                //  System.out.println("redis中已经有全部游戏分类");
                return Msg.success().add("pb", pb1);
            }else {
                // redis缓存中没有这条记录
                // 从数据库中查询, 并写到 redis 缓存数据库中
                PageBean<Genres> pb = genresService.findGenres(currentPage, pageSize, ggName);
                redisTemplate.opsForValue().set("pb", pb);
                // 返回数据给前端
                return Msg.success().add("pb", pb);
            }
        }
    }
}
