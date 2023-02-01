package org.zxc.game_share.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zxc.game_share.bean.User;
import java.util.List;

/**
 * 用户数据库持久层
 * @author 知行成
 * @since 2021-11-4
 */
@Mapper
public interface UserMapper {
    // 查询所有用户信息
    public List<User> findAll();

    /**
     * 根据用户名, 查询一共有多少条用户记录, 逻辑查询
     * @return
     * @param username
     * @param isDeleted
     */
    public int findTotalCount(
            @Param("username") String username,
            @Param("isDeleted") Integer isDeleted);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 分页查询，根据用户名模糊查询
     * 这里使用动态 sql 查询
     * 这里要注意：多参数传递时，要加 @Param()注解
     * 否则，mapper.xml文件里面参数不可用
     * @param start
     * @param pageSize
     * @param username
     * @param isDeleted
     * @return
     */
    public List<User> findByUsernameWithPage(
            @Param("start") int start,
            @Param("pageSize") int pageSize,
            @Param("username") String username,
            @Param("isDeleted") Integer isDeleted);

    /**
     * 添加用户
     * @param user
     * @return
     */
    public int addUser(User user);

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
     * 根据用户 uid删除一个用户
     * @param uid
     * @return
     */
    public int deleteUserByUid(
            @Param("uid") Integer uid);

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


    User findUserByPhone(String  phone);
}
