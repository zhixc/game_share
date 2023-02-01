package org.zxc.game_share.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 * 这个类参考自黑马程序员 javaWeb 视频教程
 *
 * Serializable序列化，因为需要存到redis缓存
 *
 * @author 黑马程序员
 * @since 2018
 */
public class PageBean<T> implements Serializable{

    private int totalCount; // 总记录数
    private int totalPage; // 总页码
    private int currentPage; // 当前页码
    private int pageSize; // 每页显示条数

    private List<T> list; // 每页显示的数据集合

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
