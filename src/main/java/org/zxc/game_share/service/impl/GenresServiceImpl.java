package org.zxc.game_share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.zxc.game_share.bean.Genres;
import org.zxc.game_share.bean.PageBean;
import org.zxc.game_share.mapper.GenresMapper;
import org.zxc.game_share.service.GenresService;

import java.util.List;
import java.util.Set;

/**
 * 游戏分类业务实现层
 * @author 知行成
 * @since 2021-11-4
 */
@Service
public class GenresServiceImpl implements GenresService {

    @Autowired
    private GenresMapper genresMapper;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 查询所有游戏分类
     * @return
     * @param currentPage
     * @param pageSize
     * @param ggName
     */
    @Override
    public PageBean<Genres> findGenres(int currentPage, int pageSize, String ggName) {

        // 1.封装pageBean对象
        // 1.1 创建 pageBean对象
        PageBean<Genres> pb = new PageBean<>();
        // 1.2 设置当前页码值
        pb.setCurrentPage(currentPage);
        // 1.3 设置每页多少条数据
        pb.setPageSize(pageSize);
        // 1.4 设置总记录数，这里需要查询数据库计算总的记录条数
        int totalCount = genresMapper.findTotalCount();
        pb.setTotalCount(totalCount);
        // 1.5 设置总的页码数，这里要计算一下
        int totalPage = 0;
        int result = totalCount / pageSize;
        totalPage = totalCount % pageSize == 0 ? result : result + 1;
        pb.setTotalPage(totalPage);
        // 1.6根据当前页码 currentPage 和每页显示多少条记录 pageSize
        // 计算并查询用户表，从而得到用户数据，再设置到pageBean对象里面
        // start 是开始查询的位置
        int start = (currentPage - 1) * pageSize; // 这是计算总结出来的公式
        // 1.7开始调用 mapper层查询之前，先判断一下
        List<Genres> genresList = genresMapper.findByggNameWithPage(start, pageSize, ggName);
        // 1.8 设置列表数据
        pb.setList(genresList);
        return pb;
    }

}
