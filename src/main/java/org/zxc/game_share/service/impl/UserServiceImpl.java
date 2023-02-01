package org.zxc.game_share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.zxc.game_share.bean.Msg;
import org.zxc.game_share.bean.PageBean;
import org.zxc.game_share.bean.Role;
import org.zxc.game_share.bean.User;
import org.zxc.game_share.mapper.UserMapper;
import org.zxc.game_share.service.UserService;
import org.zxc.game_share.util.StrUtil;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 用户业务层 实现类
 * @author 知行成
 * @since 2021-11-4
 */
@Service
public class UserServiceImpl implements UserService {
    // 调用 dao / mapper层
    @Autowired
    private UserMapper userMapper; // 这里的爆红不用管，因为我在 UserMapper
    // 中使用的是 @Mapper 注解, 如果使用的是 @Repository注解，那么就不会爆红
    // @Mapper 和 @Repository 这两个注解具有部分相同的功能, 另外有点区别

    // 查询所有用户信息
    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    // 分页查询用户信息，带用户名模糊查询
    @Override
    public PageBean<User> findByUsernameWithPage(
            int currentPage,
            int pageSize,
            String username,
            Integer isDeleted) {
        // 1.封装pageBean对象
        // 1.1 创建 pageBean对象
        PageBean<User> pb = new PageBean<>();
        // 1.2 设置当前页码值
        pb.setCurrentPage(currentPage);
        // 1.3 设置每页多少条数据
        pb.setPageSize(pageSize);
        // 1.4 设置总记录数，这里需要查询数据库计算总的记录条数
        int totalCount = userMapper.findTotalCount(username, isDeleted);
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
        // 1.7开始调用 mapper层查询
        List<User> userList = userMapper.findByUsernameWithPage(start, pageSize, username, isDeleted);
        // 1.8 设置列表数据
        pb.setList(userList);
        return pb;
    }

//    /**
//     * 根据用户名查询用户信息
//     * @param username
//     * @return
//     */
//    @Override
//    public User findByUsername(String username) {
//        return userMapper.findByUsername(username);
//    }

    // 注册用户功能
    @Override
    public Msg regist(String username, String password, String password2) {
        // 判断用户名和密码是否为空或空字符串,
        /**
         * 判断用户名是否为 null 或 ""
         * 如果是，那么说明用户没有输入用户名
         */
//        if (username == null || username.equals("")){
//            return Msg.fail(403, "错误，没有输入用户名！");
//        }
//        /**
//         * 判断密码是否为 null 或 ""
//         * 如果是，那么说明用户没有输入密码
//         */
//        if (password == null || password.equals("")){
//            return Msg.fail(403, "错误，没有输入密码！");
//        }
//        /**
//         * 判断确认密码是否为 null 或 ""
//         * 如果是，那么说明用户没有输入密码
//         */
//        if (password2 == null || password2.equals("")){
//            return Msg.fail(403, "错误，没有输入确认密码！");
//        }
//        /**
//         * 判断密码和确认密码是否一致，若不一致，执行if里面的语句
//         */
//        if (!password.equals(password2)){
//            return Msg.fail(403, "错误，密码和确认密码不一致！");
//        }
        // 这里我发现判断规则类似，会出现大量重复代码
        // 所以要将这些代码封装成方法，提高代码复用率
        if (StrUtil.isEmpty(username)){
            // 用户名为空
            return Msg.fail(4015, "错误，用户名为空！");
        } else if (StrUtil.isEmpty(password)){
            // 密码为空
            return Msg.fail(4012, "错误，密码为空！");
        } else if (StrUtil.isEmpty(password2)){
            // 确认密码为空
            return Msg.fail(4013, "错误，确认密码为空！");
        } else if (!password.equals(password2)){
            //密码和确认密码不一致
            return Msg.fail(4014, "错误，密码和确认密码不一致！");
        }
        // 用户名空格校验
        if (username.indexOf(" ") != -1){
            return Msg.fail(4015, "错误，用户名不得包含空格");
        }
        // 用户名长度校验
        if (username.length() < 1 || username.length() > 10){
            return Msg.fail(4015, "错误，用户名不在 1 - 10 位之间");
        }
        // 密码空格校验
        if (password.indexOf(" ") != -1){
            return Msg.fail(4012, "错误，密码不能包含空格！");
        }
        // 密码长度校验
        if (password.length() < 4 || password.length() > 16){
            // 错误，密码长度不在 4-16位之间
            return Msg.fail(4012, "密码长度不在 4-16 位之间");
        }
        // 这里确认密码的长度不需要判断了，因为上面密码和确认密码比较已经判断了

        // 接下来判断用户名是否在数据库中存在了
        // 这里需要调用 mapper 根据用户名查询用户信息
        // 若存在，则不能注册
        User u = userMapper.findByUsername(username);
        if (u != null){
            return Msg.fail(4016, "错误，用户名已经存在！");
        }
        // 到了这里就基本没有问题，可以进行注册了
        // 将密码用MD5进行加密
        String hashedPassword = DigestUtils.md5DigestAsHex(password.getBytes()).toString();
        // 这里要调用 mapper的添加用户方法，来进行注册
//        User user = new User(null, username,
//                hashedPassword, null, null,
//                0, new Date(),
//                new Date(), new Role(4, null), "img/avatar/default.jpeg");
//        int i = userMapper.addUser(user);
//        if (i > 0){
//            // 说明添加成功
//            return Msg.success(200, "注册成功");
//        }
        return Msg.fail(400, "UserServiceImpl的regist()发生未知异常");
    }

    // 登录功能
    @Override
    public Msg login(String username, String password, HttpServletRequest request) {
        // 1.先判断用户名和密码是否为空
        if (StrUtil.isEmpty(username)){
            // 用户名为空
            return Msg.fail(4015, "错误，用户名为空！");
        } else if (StrUtil.isEmpty(password)){
            // 密码为空
            return Msg.fail(4012, "错误，密码为空！");
        }
        if (password.length() < 4 || password.length() > 16){
            // 错误，密码长度不在 4-16位之间
            return Msg.fail(4012, "密码长度不在 4-16 位之间");
        }
        // 2.接下来查询用户名在数据库中是否存在
        User u = userMapper.findByUsername(username);
        if (u == null){
            // 不存在
            return Msg.fail(4017, "用户名不存在！");
        }
        // 判断用户是否被删除了
        if (u.getIsDeleted() == 1){
            return Msg.fail(403, "错误，用户不存在！");
        }
        // 3.用户名存在，那就比较密码是否正确
        // 将密码用MD5进行加密，然后再比较
        String hashedPassword = DigestUtils.md5DigestAsHex(password.getBytes()).toString();
        if (!hashedPassword.equals(u.getPassword())){
            // 参数密码与数据库查出来的用户对象密码不一致
            return Msg.fail(4018, "密码错误!");
        }
        // 4.到了这里说明，用户名和密码都已经通过检测
        // 即登录成功，将用户信息存到 session 中
//        System.out.println(u);
        request.getSession().setAttribute("USER", u);

        return Msg.success(200, "登录成功！").add("roleId", u.getRole().getRId());
    }

    /**
     * 根据用户uid查询用户信息
     * @param uid
     * @return
     */
    @Override
    public User findUserByUid(Integer uid) {
        User user = userMapper.findUserByUid(uid);
        return user;
    }

    /**
     * 根据用户 uid 修改用户信息
     * @param user
     * @return
     */
    @Override
    public int updateUserById(User user) {
        return userMapper.updateUserById(user);
    }

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public int addUser(User user) {
        // 将密码用MD5进行加密
        String hashedPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).toString();
        user.setPassword(hashedPassword);
        // 判断用户头像是否存在
        if (null == user.getAvatar() || "".equals(user.getAvatar())){
            // 用户头像为空
            // 设置默认头像
            user.setAvatar("img/avatar/default.jpeg");
        }
        return userMapper.addUser(user);
    }

    /**
     * 根据用户 uid 删除一个用户
     * @param uid
     * @return
     */
    @Override
    public int deleteUserByUid(Integer uid) {
        // 在执行逻辑删除前，先根据 uid查询用户的 is_deleted字段值是否已经为1
        // 如果为1，说明该用户从逻辑上已经被删除了
        // 那么就不需要调用mapper层进行逻辑删除了
        User u = userMapper.findUserByUid(uid);
        if (u.getIsDeleted() == 1){
            // 说明用户已经被删除了（逻辑上）
            return 0; // 删除的记录数为0
        }else {
            return userMapper.deleteUserByUid(uid);
        }
    }

    /**
     * 解除逻辑删除
     * @param uid
     * @return
     */
    @Override
    public int unDeleted(Integer uid) {
        return userMapper.unDeleted(uid);
    }

    /**
     * 根据 uid 修改用户个人信息（年龄、性别、密码）
     * @param user
     * @return
     */
    @Override
    public int updatePersonInfoByUid(User user) {
        return userMapper.updatePersonInfoByUid(user);
    }

    @Override
    public Msg loginByPhone(String  phone, String password, HttpServletRequest request) {
        User user = userMapper.findUserByPhone(phone);
        System.out.println("程序进入这里～～～～～");
        System.out.println("user = " + user);
        // 判断用户是否存在
        if (user == null){
            return Msg.fail(400, "用户不存在！");
        }
        // 否则说明用户存在
        // 传递进来的password需要进行MD5加密后再比较
        String hashedPassword = DigestUtils.md5DigestAsHex(password.getBytes()).toString();
        if (!hashedPassword.equals(user.getPassword())){
            // 参数密码与数据库查出来的用户对象密码不一致
            return Msg.fail(4018, "密码错误!");
        }
        // 到这里说明手机号和密码都成功验证
        // 将用户信息写到session会话里面
        request.getSession().setAttribute("USER", user);
        return Msg.success();
    }
}
