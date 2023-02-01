package org.zxc.game_share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zxc.game_share.bean.Game;
import org.zxc.game_share.bean.GameVideo;
import org.zxc.game_share.bean.Msg;
import org.zxc.game_share.service.GameService;
import org.zxc.game_share.service.GameVideoService;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

/**
 * 游戏预览视频上传控制器
 *
 * @author 知行成
 * @since 2022-03-04
 */
@RestController
public class GameVideoController {
    @Autowired
    private GameVideoService gameVideoService;

    @Autowired
    private GameService gameService;

    /**
     * 游戏预览视频上传
     *
     * @param uploadFile
     * @param gid
     * @param isDeleted
     * @return
     */
    @RequestMapping("/gameVideo/upload")
    public Msg uploadFile2(MultipartFile uploadFile, @RequestParam Integer gid, @RequestParam Integer isDeleted) {
//        System.out.println(uploadFile);
//        System.out.println(gid);
//        System.out.println(isDeleted);

        //判断文件是否为空
        if (uploadFile == null) {
            return Msg.fail(403, "错误，文件不能为空！");
        }
        if (uploadFile.isEmpty()) {
            return Msg.fail(403, "错误，文件不能为空！");
        }

        // 1.获得上传的文件名称（包含文件格式）
        String fileName = uploadFile.getOriginalFilename();

        // 进行文件格式、文件大小校验
        int i = fileName.lastIndexOf("."); // 获取最后一个.的索引
        String suffixName = fileName.substring(i);//从最后一个.开始截取后面的字符串
        System.out.println("后缀名是：" + suffixName); //截取出来的就是文件后缀名了
        System.out.println("文件大小=" + uploadFile.getSize()); //取得的是字节

        // 视频格式必须为 mp4
        if (!".mp4".equals(suffixName)) {
            // 视频格式不为 mp4
            return Msg.fail(403, "视频格式必须为：mp4");
        }

        // 视频大小不得超过100MB，转成MB
        long videoSize = uploadFile.getSize() / 1024 / 1024;
        System.out.println("视频大小" + videoSize + "MB");
        if (videoSize > 100) {
            System.out.println("视频大小超过100MB");
            return Msg.fail(403, "视频大小超过100MB了!");
        }

        // 校验结束
        // 重新生成文件名
        fileName = UUID.randomUUID() + "_" + fileName;
        // 2.获取当前项目路径与要保存的位置
        String dirPath = System.getProperty("user.dir")
                + "/files/video/";
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

        // 生成url，用于文件下载输出流
        String url = "/gameVideo/" + fileName;
        // 写到数据库表里面
        GameVideo gameVideo = new GameVideo();
        System.out.println("gid=" + gid);
        System.out.println("isDeleted=" + isDeleted);
        Game gameByGid = gameService.findGameByGid(gid, isDeleted);
        System.out.println(gameByGid);
        gameVideo.setGame(gameByGid);
        gameVideo.setUrl(url);
        gameVideo.setSize(uploadFile.getSize() / 1024 / 1024); // 转成Mb
        int i2 = gameVideoService.addGameVideo(gameVideo);

        return Msg.success(200, "上传成功").add("url", url);
    }

    /**
     * 游戏预览视频下载
     *
     * @param filename
     * @param response
     * @throws IOException
     */
    @GetMapping("/gameVideo/{filename}")
    public void download(@PathVariable String filename, HttpServletResponse response) throws IOException {
        // 获取当前项目路径
        String dirPath = System.getProperty("user.dir")
                + "/files/video/";
        // 根据当前项目路径和文件名，拼接成为文件绝对路径，读取作为file对象
        File file = new File(dirPath + filename);
        // 将file对象作为文件输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        // 创建相应输出流对象
        ServletOutputStream outputStream = response.getOutputStream();

        // 设置相应头
        response.addHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));

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
     * 根据游戏主键 gid 查询游戏预览视频, 从游戏预览视频表里面查
     *
     * @param gid
     * @return
     */
    @RequestMapping("/gameVideo/findGameVideoByGid")
    public Msg findGameVideoByGid(@RequestParam Integer gid) {
        if (null == gid) {
            return Msg.fail(403, "错误，gid为null");
        }
        // 调用业务层查询
        List<GameVideo> gameVideoList = gameVideoService.findGameVideoByGid(gid);
        // 暂时只做一个
        return Msg.success().add("gameVideoList", gameVideoList);
    }
}
