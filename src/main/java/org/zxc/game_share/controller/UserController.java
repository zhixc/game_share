package org.zxc.game_share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zxc.game_share.bean.Msg;
import org.zxc.game_share.bean.PageBean;
import org.zxc.game_share.bean.Role;
import org.zxc.game_share.bean.User;
import org.zxc.game_share.service.RoleService;
import org.zxc.game_share.service.UserService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户控制层
 *
 * @author 知行成
 * @since 2021-11-4
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 添加用户或者修改用户时，表单头像上传
     *
     * @param uploadFile
     * @param request
     * @return
     */
    @RequestMapping("/avatar/add")
    public Msg addAvatar(MultipartFile uploadFile, HttpServletRequest request) {
        // 判断文件是否为空
        if (uploadFile.isEmpty()) {
            return Msg.fail(403, "错误，文件不能为空！");
        }

        // 1.获得上传的文件名称（包含文件格式）
        String fileName = uploadFile.getOriginalFilename();
        // 重新生成文件名
        fileName = UUID.randomUUID() + "_" + fileName;
        // 2.获取当前项目路径与要保存的位置
        String dirPath = System.getProperty("user.dir")
                + "/files/avatar/";
        File filePath = new File(dirPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        // 写到本地
        try {
            uploadFile.transferTo(new File(dirPath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String avatar = "/user/avatar/" + fileName;

        return Msg.success(200, "上传成功").add("avatar", avatar);
    }


    /**
     * 用户头像上传
     *
     * @param uploadFile
     * @param request
     * @return
     */
    @RequestMapping("/avatar/upload")
    public Msg uploadAvatar(MultipartFile uploadFile, HttpServletRequest request) {
        // 判断文件是否为空
        if (uploadFile == null) {
            return Msg.fail(403, "错误，文件不能为空！");
        }
        if (uploadFile.isEmpty()) {
            return Msg.fail(403, "错误，文件不能为空！");
        }

        // 1.获得上传的文件名称（包含文件格式）
        String fileName = uploadFile.getOriginalFilename();

        // 打印文件类型
        System.out.println("文件类型="+uploadFile.getContentType());

        int i = fileName.lastIndexOf("."); // 获取最后一个.的索引
        String suffixName = fileName.substring(i);//从最后一个.开始截取后面的字符串
        System.out.println("后缀名是：" + suffixName); //截取出来的就是文件后缀名了
        System.out.println("文件大小=" + uploadFile.getSize()); //取得的是字节

        // 头像格式必须为 png/jpg/jpeg
        if (!".png".equals(suffixName) && !".jpg".equals(suffixName) && !".jpeg".equals(suffixName)) {
            // 头像格式不为 png/jpg/jpeg
            System.out.println("头像格式不为 png/jpg/jpeg");
            return Msg.fail(403, "头像格式必须为： png/jpg/jpeg");
        }

        // 图像大小不得超过500KB，转成KB
        long pictureSize = uploadFile.getSize() / 1024;
        System.out.println("头像大小"+pictureSize+"KB");
        if (pictureSize > 500) {
            System.out.println("头像大小超过500KB了");
            return Msg.fail(403, "头像大小超过500KB了");
        }


        // 重新生成文件名
        fileName = UUID.randomUUID() + "_" + fileName;
        // 2.获取当前项目路径与要保存的位置
        String dirPath = System.getProperty("user.dir")
                + "/files/avatar/";
        File filePath = new File(dirPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        // 写到本地
        try {
            uploadFile.transferTo(new File(dirPath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 写到数据库表里面
        // 先从session中取出用户信息
        User user = (User) request.getSession().getAttribute("USER");
        user.setAvatar("/user/avatar/" + fileName);
        userService.updateUserById(user);

        // 更新session会话中头像信息
        request.getSession().setAttribute("USER", user);

        String avatar = "/user/avatar/" + fileName;

        return Msg.success(200, "上传成功").add("avatar", avatar);
    }

    /**
     * 头像下载/头像获取
     *
     * @param avatar
     * @param response
     * @throws IOException
     */
    @GetMapping("/avatar/{avatar}")
    public void download(@PathVariable String avatar, HttpServletResponse response) throws IOException {
        // 获取当前项目路径
        String dirPath = System.getProperty("user.dir")
                + "/files/avatar/";
        // 根据当前项目路径和文件名，拼接成为文件绝对路径，读取作为file对象
        File file = new File(dirPath + avatar);
        // 将file对象作为文件输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        // 创建相应输出流对象
        ServletOutputStream outputStream = response.getOutputStream();

        // 设置相应头
        response.addHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(avatar, "UTF-8"));

        // 文件读取输出，每次读取 1024
        int len = 0;
        byte[] data = new byte[1024];
        while ((len = fileInputStream.read(data)) != -1) {
            outputStream.write(data, 0, len);
        }

        // 关闭输出流
        outputStream.close();
        // 关闭输入流
        fileInputStream.close();
    }

    /**
     * 批量删除用户信息, 逻辑删除
     *
     * @param
     * @return
     */
    @RequestMapping("/deleteUserByUids")
    public Msg deleteUserByUids(HttpServletRequest request) {
        String[] uid = request.getParameterValues("uid[]");
        if (uid.length <= 0) {
            return Msg.fail(400, "错误，没有选中要删除的行！");
        } else {
            // 将字符串数组转化为数组
            int arr[] = new int[uid.length];
            for (int i = 0; i < uid.length; i++) {
                arr[i] = Integer.parseInt(uid[i]);
            }
            // 使用for循环，逐条删除
            for (int i : arr) {
                userService.deleteUserByUid(i);
            }
        }
        return Msg.success();
    }

    /**
     * 批量解除逻辑删除
     *
     * @param
     * @return
     */
    @RequestMapping("/unDeletedMultiple")
    public Msg unDeletedMultiple(HttpServletRequest request) {
        String[] strArr = request.getParameterValues("arr[]");
//        for (String i : strArr){
//            System.out.println(i);
//        }
        if (null == strArr) {
            return Msg.fail(400, "错误，没有选中要删除的行, 空指针异常");
        }
        if (strArr.length <= 0) {
            // 没有要删除的
            return Msg.fail(400, "错误，没有选中要删除的行");
        }
        // 批量解除删除，根据批量删除的改进, 这种需要判断 null的情况, 所以在上面加入判断 strArr == null
        // 可以不用转为int型数组，for循环遍历时可以直接调用业务层
        for (int i = 0; i < strArr.length; i++) {
            userService.unDeleted(Integer.parseInt(strArr[i]));
        }

        return Msg.success(200, "批量解除逻辑删除成功，用户已经重新开启！");
    }

    /**
     * 解除逻辑删除
     *
     * @param uid
     * @return
     */
    @RequestMapping("/unDeleted")
    public Msg unDeleted(Integer uid) {
//        System.out.println(uid);
        int i = userService.unDeleted(uid);
        return Msg.success(200, "解除逻辑删除成功，用户已经重新开启！");
    }

    /**
     * 根据用户 uid删除一个用户
     *
     * @param uid
     * @return
     */
    @RequestMapping("/deleteUserByUid")
    public Msg deleteUserByUid(Integer uid) {
//        System.out.println(uid); //获取成功
        // 调用 service删除
        int i = userService.deleteUserByUid(uid);
        if (i > 0) {
            return Msg.success(200, "删除成功！");
        }
        return Msg.fail(400, "删除失败！");
    }

    /**
     * 添加用户
     *
     * @param user
     * @param roleId
     * @return
     */
    @RequestMapping("/addUser")
    public Msg addUser(User user, Integer roleId) {
//        System.out.println(user);
//        System.out.println(roleId);
        String password = user.getPassword();
        if (password.indexOf(" ") != -1){
            return Msg.fail(403, "错误，密码不能包含空格！");
        }
        if (password.length() < 4 || password.length() > 16){
            // 错误，密码长度不在 4-16位之间
            return Msg.fail(403, "错误，密码长度不在 4-16位之间");
        }
        // 用户名空格校验
        if (user.getUsername().indexOf(" ") != -1){
            return Msg.fail(403, "错误，用户名不能包含空格！");
        }
        if (user.getUsername().length() < 1 || user.getUsername().length() > 10){
            // 错误，用户名长度不在 1-10位之间
            return Msg.fail(403, "错误，用户名长度不在 1-10位之间");
        }
        // 1.根据用户名查询用户信息，如果用户已经存在了，那么就不能添加
        User u = userService.findByUsername(user.getUsername());
        if (u != null) {
            // 用户已经存在
            return Msg.fail(400, "用户名已经存在");
        }
        // 校验 roleId
        if (null == roleId){
            return Msg.fail(403, "错误，roleId为null");
        }
        Role role = roleService.findRoleByRoleId(roleId);
        if (null == role){
            // roleI不合法
            return Msg.fail(403, "错误，roleId不合法");
        }

        // 年龄校验
        if (user.getAge() != null){
            // 判断年龄是否符合正常人的寿命
            if (user.getAge() < 0 || user.getAge() > 199){
                return Msg.fail(403, "错误，年龄不在 0 - 199 之间！");
            }
        }

        user.setGmtCreate(new Date());// 创建时间
        user.setGmtModified(new Date()); // 修改时间

        user.setRole(role);
        int i = userService.addUser(user);
        return Msg.success();
    }

    /**
     * 修改用户信息（用户名不得更改，这是唯一的字段）
     *
     * @param user
     * @return
     */
    @RequestMapping("/updateUserByUid")
    public Msg updateUser(User user, Integer roleId) {
//        System.out.println(user);
//        System.out.println(roleId);
        // 判断roleId是否为null
        if (roleId == null){
            // 错误， roleId 不能为 null
            return Msg.fail(403, "错误，roleId 为 null");
        }
        // 判断 user 是否为 null
        if (null == user){
            return Msg.fail(403,  "错误， user 为null");
        }
        // 判断 roleId在角色表中是否存在, 根据 roleId查询角色表
        Role role = roleService.findRoleByRoleId(roleId);
        if (null == role){
            // 角色表中不存在roleId
            return Msg.fail(403, "错误，roleId不合法！");
        }

        // 年龄校验
        if (user.getAge() != null){
            // 判断年龄是否符合正常人的寿命
            if (user.getAge() < 0 || user.getAge() > 199){
                return Msg.fail(403, "错误，年龄不在 0 - 199 之间！");
            }
        }
//        // 用户名空格校验
//        if (user.getUsername().indexOf(" ") != -1){
//            return Msg.fail(403, "错误，用户名不能包含空格！");
//        }
//        // 用户名长度校验
//        if (user.getUsername().length() < 1 || user.getUsername().length() > 10){
//            return Msg.fail(403, "错误，用户名不在 1 - 10 位之间");
//        }
//        // 用户名是否在数据库中存在了

        String password = user.getPassword();

        user.setRole(role);
        user.setGmtModified(new Date());
        // 先判断密码是否修改过，前端提交过来的MD5和数据库中的进行比较
        User userByUid = userService.findUserByUid(user.getUId());
        if (!user.getPassword().equals(userByUid.getPassword())) {
            // 进入这里说明密码修改了
            // 密码空格校验
            if (password.indexOf(" ") != -1){
                return Msg.fail(403, "错误，密码不能包含空格！");
            }
            // 密码长度校验 4-16位
            if (password.length() < 4 || password.length() > 16){
                // 错误，密码长度不在 4-16位之间
                return Msg.fail(403, "密码长度不在 4-16位之间");
            }
            // 将密码用MD5进行加密
            String hashedPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).toString();
            user.setPassword(hashedPassword);
        }
        // 判断用户头像是否为 null
        // 如果是，给其写入一个默认头像
//        System.out.println("要修改的用户头像：" + user.getAvatar());
        if (null == user.getAvatar() || "".equals(user.getAvatar())) {
            // 用户头像为空
            // 设置默认头像
            user.setAvatar("img/avatar/default.jpeg");
        }
        int i = userService.updateUserById(user);
        return Msg.success();
    }

    /**
     * 根据用户uid查询用户信息, 顺便查询角色表的角色信息
     *
     * @param uid
     * @return
     */
    @RequestMapping("/findUserByUid")
    public Msg findUserByUid(@RequestParam Integer uid) {
        if (uid == null) {
            return Msg.fail(403, "错误，用户id为空");
        }
        User user = userService.findUserByUid(uid);
        List<Role> roleList = roleService.findAllRole();
        if (user != null) {
            return Msg.success().add("user", user).add("roleList", roleList);
        } else {
            return Msg.fail(400, "根据用户uid查询失败!");
        }
    }

    /**
     * 查询所有用户，仅用于测试，不投入实际使用
     *
     * @return
     */
//    @RequestMapping("/findAll")
//    public List<User> findAll() {
//        return userService.findAll();
//    }

    /**
     * 分页模糊查询用户信息
     *
     * @param currentPage 当前页码，默认为 1
     * @param pageSize    每页显示多少条数据 默认 10 条
     * @param username    用户名 默认为空字符串
     * @param isDeleted   逻辑删除属性，1表示数据库字段已经被删除，0表示保留
     * @return
     */
    @RequestMapping("/findPage")
    public Msg findWithPage(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "0") Integer isDeleted) {
        PageBean<User> pb = userService.findByUsernameWithPage(currentPage, pageSize, username, isDeleted);
        return Msg.success().add("pb", pb);
    }

    /**
     * 用户注册功能
     *
     * @param username
     * @param password
     * @param password2
     * @param checkCode
     * @param request
     * @return
     */
    @RequestMapping("/regist")
    public Msg regist(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String password2,
            @RequestParam String checkCode, HttpServletRequest request) {
        // 1.从session中取出验证码信息
        String checkcode_server = String.valueOf(request.getSession().getAttribute("CHECKCODE_SERVER"));
        if (!checkcode_server.equals(checkCode)) {
            // session 会话中的验证码与用户提交过来的不一致
            // 移除session会话中的验证码信息
            request.getSession().removeAttribute("CHECKCODE_SERVER");
            return Msg.fail(4011, "验证码错误");
        }
        // 移除session会话中的验证码信息
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        // 2.调用 service 层来解决
        Msg msg = userService.regist(username, password, password2);

        return msg;
    }

    /**
     * 用户登录功能
     * @param username
     * @param password
     * @param checkCode
     * @return
     */
    @RequestMapping("/login")
    public Msg login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String checkCode, HttpServletRequest request) {
        // 1.从session中取出验证码信息, 判断
        String checkcode_server = String.valueOf(request.getSession().getAttribute("CHECKCODE_SERVER"));
        if (!checkcode_server.equals(checkCode)) {
            // session 会话中的验证码与用户提交过来的不一致
            // 移除session会话中的验证码信息
            request.getSession().removeAttribute("CHECKCODE_SERVER");
            return Msg.fail(4011, "验证码错误");
        }
        // 移除session会话中的验证码信息
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        // 2.调用业务层来处理
        Msg msg = userService.login(username, password, request);

        return msg;
    }

    /**
     * 用户登录功能（手机号）
     * @param phone
     * @param password
     * @param checkCode
     * @return
     */
    @RequestMapping("/loginByPhone")
    public Msg loginByPhone(
            @RequestParam String  phone,
            @RequestParam String password,
            @RequestParam String checkCode, HttpServletRequest request) {
        // 1.从session中取出验证码信息, 判断
        String checkcode_server = String.valueOf(request.getSession().getAttribute("CHECKCODE_SERVER"));
        if (!checkcode_server.equals(checkCode)) {
            // session 会话中的验证码与用户提交过来的不一致
            // 移除session会话中的验证码信息
            request.getSession().removeAttribute("CHECKCODE_SERVER");
            return Msg.fail(4011, "验证码错误");
        }
        // 移除session会话中的验证码信息
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        // 2.调用业务层来处理
        Msg msg = userService.loginByPhone(phone, password, request);

        return msg;
    }

    /**
     * 检查用户登录信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/checkLogin")
    public Msg checkUserLogin(HttpServletRequest request) {
        // 从 session中取出用户信息
        User user = (User) request.getSession().getAttribute("USER");
        if (user == null) {
            return Msg.success(403, "用户未登录！");
        } else {
            // 将用户的部分信息提交给前端，如用户名，头像
            User u = new User();
            u.setUId(user.getUId());
            u.setUsername(user.getUsername());
            u.setAvatar(user.getAvatar());
            u.setAge(user.getAge());
            u.setRole(user.getRole());
            return Msg.success(200, "用户已经登录！").add("user", u);
        }

    }

    /**
     * 退出功能
     *
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public Msg logout(HttpServletRequest request) {
        // 先从 session 中查看用户信息是否存在
        // 不存在，则直接返回通知用户，操作不合法
        Object user = request.getSession().getAttribute("USER");
        if (user == null) {
            return Msg.fail(403, "错误，您尚未登录！请先登录！");
        }
        // 存在的话，清除 session 中的用户信息
        request.getSession().removeAttribute("USER");
        return Msg.success(200, "退出成功！");
    }

}
