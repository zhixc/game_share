package org.zxc.game_share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zxc.game_share.bean.Msg;
import org.zxc.game_share.bean.User;
import org.zxc.game_share.service.UserService;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 文件上传控制器
 *
 * @author 知行成
 * @since 2021-11-12
 */
//@RestController //暂时注释掉，不用
public class FileController {
    @Autowired
    UserService userService;

//    /**
//     * 文件上传
//     *
//     * @param uploadFile
//     * @return
//     */
//    @RequestMapping("/file/upload")
//    public Msg uploadFile(MultipartFile uploadFile, HttpServletRequest request) {
//        /**
//         * 判断文件是否为空
//         */
//        if (uploadFile.isEmpty()) {
//            return Msg.fail(403, "错误，文件不能为空！");
//        }
//
//        // 1.获得上传的文件名称（包含文件格式）
//        String fileName = uploadFile.getOriginalFilename();
//        // 重新生成文件名
//        fileName = UUID.randomUUID() + "_" + fileName;
//        // 2.获取当前项目路径与要保存的位置
//        String dirPath = System.getProperty("user.dir")
//                + "/src/main/resources/static/avatar/";
//        File filePath = new File(dirPath);
//        if (!filePath.exists()) {
//            filePath.mkdirs();
//        }
//        // 写到本地
//        try {
//            uploadFile.transferTo(new File(dirPath + fileName));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // 写到数据库表里面
//        // 先从session中取出用户信息
//        User user = (User) request.getSession().getAttribute("USER");
//        user.setAvatar(dirPath + fileName);
//        int i = userService.updateUserById(user);
//
//        // 更新session会话中头像信息
//        request.getSession().setAttribute("USER", user);
//
//        return Msg.success(200, "上传成功");
//    }

    @RequestMapping("/file/upload2")
    public Msg uploadFile2(MultipartFile uploadFile, HttpServletRequest request) {
        /**
         * 判断文件是否为空
         */
        if (uploadFile.isEmpty()) {
            return Msg.fail(403, "错误，文件不能为空！");
        }

        // 1.获得上传的文件名称（包含文件格式）
        String fileName = uploadFile.getOriginalFilename();
        // 重新生成文件名
        fileName = UUID.randomUUID() + "_" + fileName;
        // 2.获取当前项目路径与要保存的位置
        String dirPath = System.getProperty("user.dir")
                + "/files/";
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
        user.setAvatar("/file/" + fileName);
        int i = userService.updateUserById(user);

        // 更新session会话中头像信息
        request.getSession().setAttribute("USER", user);

        return Msg.success(200, "上传成功");
    }

    @GetMapping("/file/{avatar}")
    public void download(@PathVariable String avatar, HttpServletResponse response) throws IOException {
        // 获取当前项目路径
        String dirPath = System.getProperty("user.dir")
                + "/files/";
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

}
