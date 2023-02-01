package org.zxc.game_share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zxc.game_share.bean.Game;
import org.zxc.game_share.bean.PageBean;
import org.zxc.game_share.mapper.GameMapper;
import org.zxc.game_share.service.GameService;

import java.util.List;

/**
 * 游戏业务实现层
 * @author 知行成
 * @since 2021-11-4
 */
@Service
public class GameServiceImpl implements GameService{
    @Autowired
    private GameMapper gameMapper;
    /**
     * 根据游戏分类主键ggId查询游戏
     *
     * @param currentPage
     * @param pageSize
     * @param ggId
     * @return
     */
    @Override
    public PageBean findGameByggId(Integer currentPage, Integer pageSize, Integer ggId) {

        // 1.封装pageBean对象
        // 1.1 创建 pageBean对象
        PageBean<Game> pb = new PageBean<>();
        // 1.2 设置当前页码值
        pb.setCurrentPage(currentPage);
        // 1.3 设置每页多少条数据
        pb.setPageSize(pageSize);
        // 1.4 设置总记录数，这里需要查询数据库计算总的记录条数
        int totalCount = gameMapper.findTotalCountByggId(ggId);
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
        // 调用 mapper层
        List<Game> gameList = gameMapper.findGameByggId(start, pageSize, ggId);

        // 1.7设置数据列表
        pb.setList(gameList);

        return pb;
    }

    /**
     * 根据游戏名称，模糊查询游戏数据，并作分页
     * @param currentPage
     * @param pageSize
     * @param gameName
     * @param isDeleted
     * @return
     */
    @Override
    public PageBean findGameByName(Integer currentPage,
                                   Integer pageSize,
                                   String gameName, Integer isDeleted) {
        // 1.封装pageBean对象
        // 1.1 创建 pageBean对象
        PageBean<Game> pb = new PageBean<>();
        // 1.2 设置当前页码值
        pb.setCurrentPage(currentPage);
        // 1.3 设置每页多少条数据
        pb.setPageSize(pageSize);
        // 1.4 设置总记录数，这里需要查询数据库计算总的记录条数
        int totalCount = gameMapper.findTotalCountByName(gameName, isDeleted);
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
        // 调用 mapper层
        List<Game> gameList = gameMapper.findGameByName(start, pageSize, gameName, isDeleted);
        pb.setList(gameList);
        return pb;
    }

    /**
     * 根据游戏主键gid查询游戏
     * @param gid
     * @return
     */
    @Override
    public Game findGameByGid(Integer gid, Integer isDeleted) {
        Game game = gameMapper.findGameByGid(gid, isDeleted);
        return game;
    }

    /**
     * 添加游戏
     * @param game
     * @return
     */
    @Override
    public int addGame(Game game) {
        int i = gameMapper.addGame(game);
        return i;
    }

    /**
     * 修改游戏
     * @param game
     * @return
     */
    @Override
    public int updateGame(Game game) {
        int i = gameMapper.updateGame(game);
        return i;
    }

    /**
     * 根据 id 删除游戏
     * @param gid
     * @return
     */
    @Override
    public int deleteGameByGid(Integer gid) {
        int i = gameMapper.deleteGameByGid(gid);
        return i;
    }

    /**
     * 解除逻辑删除
     * @param gid
     * @return
     */
    @Override
    public int unDeleted(Integer gid) {
        int i = gameMapper.unDeleted(gid);
        return i;
    }
}
