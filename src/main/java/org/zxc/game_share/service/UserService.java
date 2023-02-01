package org.zxc.game_share.service;

import org.zxc.game_share.bean.Msg;
import org.zxc.game_share.bean.PageBean;
import org.zxc.game_share.bean.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户业务层
 * @author 知行成
 * @since 2021-11-4
 */
public interface UserService {
    // 查询所有用户信息
    public List<User> findAll();

    /**
     * 分页模糊查询用户信息，根据用户名
     * @param currentPage
     * @param pageSize
     * @param username
     * @param isDeleted
     * @return
     */
    public PageBean<User> findByUsernameWithPage(
            int currentPage,
            int pageSize,
            String username,
            Integer isDeleted);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 注册用户
     * @param username
     * @param password
     * @param password2
     * @return
     */
    public Msg regist(String username, String password, String password2);

    /**
     * 登录功能
     * @param username
     * @param password
     * @return
     */
    public Msg login(String username, String password, HttpServletRequest request);

    /**
     * 根据用户uid查询用户信息
     * @param uid
     * @return
     */
    public User findUserByUid(Integer uid);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public int updateUserById(User user);

    /**
     * 添加用户
     * @param user
     * @return
     */
    public int addUser(User user);

    /**
     * 根据用户 uid删除一个用户
     * @param uid
     * @return
     */
    public int deleteUserByUid(Integer uid);

    /**
     * 解除逻辑删除
     * @param uid
     * @return
     */
    public int unDeleted(Integer uid);

    /**
     * 根据 uid 修改用户个人信息（年龄、性别、密码）
     * @param user
     * @return
     */
    int updatePersonInfoByUid(User user);

    /**
     * 手机号登录
     * @param phone
     * @param password
     * @param request
     * @return
     */
    Msg loginByPhone(String  phone, String password, HttpServletRequest request);
}
