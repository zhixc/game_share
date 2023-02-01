package org.zxc.game_share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zxc.game_share.bean.GameComment;
import org.zxc.game_share.bean.PageBean;
import org.zxc.game_share.mapper.GameCommentMapper;
import org.zxc.game_share.service.GameCommentService;
import java.util.Date;
import java.util.List;

/**
 * 游戏评论业务实现层
 * @author 知行成
 * @since 2022-03-22
 */
@Service
public class GameCommentServiceImpl implements GameCommentService {
    @Autowired
    GameCommentMapper gameCommentMapper;

    @Override
    public int addGameComment(GameComment gameComment) {
        return gameCommentMapper.addGameComment(gameComment);
    }

    @Override
    public int updateGameComment(GameComment gameComment) {
        return gameCommentMapper.updateGameComment(gameComment);
    }

    @Override
    public int deleteGameCommentById(Integer gameCommentId, Integer isDeleted, Date gmtModified) {
        return gameCommentMapper.deleteGameCommentById(gameCommentId, isDeleted, gmtModified);
    }

    @Override
    public PageBean findGameCommentByGameId(Integer currentPage, Integer pageSize, Integer gameId, Integer isDeleted) {
        // 创建pageBean对象
        PageBean<GameComment> pb = new PageBean<>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        int totalCount = gameCommentMapper.findTotalCountByGameId(gameId, isDeleted);
        pb.setTotalCount(totalCount);
        int totalPage = 0;
        int result = totalCount / pageSize;
        totalPage = totalCount % pageSize == 0 ? result : result + 1;
        pb.setTotalPage(totalPage);
        int start = (currentPage - 1) * pageSize;
        List<GameComment> gameCommentByGameId = gameCommentMapper.findGameCommentByGameId(start, pageSize, gameId, isDeleted);
        pb.setList(gameCommentByGameId);
        return pb;
    }

    @Override
    public PageBean findGameCommentByUsername(Integer currentPage, Integer pageSize, String username, Integer isDeleted) {
        // 创建pageBean对象
        PageBean<GameComment> pb = new PageBean<>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);

        int totalCount = gameCommentMapper.findTotalCountByUsername(username, isDeleted);
        pb.setTotalCount(totalCount);
        int totalPage = 0;
        int result = totalCount / pageSize;
        totalPage = totalCount % pageSize == 0 ? result : result + 1;
        pb.setTotalPage(totalPage);
        int start = (currentPage - 1) * pageSize;
        List<GameComment> gameCommentByGameId = gameCommentMapper.findGameCommentByUsername(start, pageSize, username, isDeleted);
        pb.setList(gameCommentByGameId);
        return pb;
    }

    @Override
    public GameComment findGameCommentByGameCommentId(Integer gameCommentId, Integer isDeleted) {
        return gameCommentMapper.findGameCommentByGameCommentId(gameCommentId, isDeleted);
    }
}
