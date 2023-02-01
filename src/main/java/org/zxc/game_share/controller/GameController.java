package org.zxc.game_share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zxc.game_share.bean.Game;
import org.zxc.game_share.bean.Msg;
import org.zxc.game_share.bean.PageBean;
import org.zxc.game_share.service.GameService;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

/**
 * 游戏控制层
 *
 * @author 知行成
 * @since 2021-11-4
 */
@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    /**
     * 游戏封面上传
     *
     * @param uploadFile
     * @param request
     * @return
     */
    @RequestMapping("/cover/upload")
    public Msg uploadGameCover(MultipartFile uploadFile, HttpServletRequest request) {
        // 判断文件是否为空
        if (uploadFile == null){
            return Msg.fail(403, "错误，文件不能为空！");
        }
        if (uploadFile.isEmpty()) {
            return Msg.fail(403, "错误，文件不能为空！");
        }

        // 1.获得上传的文件名称（包含文件格式）
        String fileName = uploadFile.getOriginalFilename();

        // 文件类型和大小校验开始
        int i = fileName.lastIndexOf("."); // 获取最后一个.的索引
        String suffixName = fileName.substring(i);//从最后一个.开始截取后面的字符串
        System.out.println("后缀名是：" + suffixName); //截取出来的就是文件后缀名了
        System.out.println("文件大小=" + uploadFile.getSize()); //取得的是字节

        // 格式必须为 png/jpg/jpeg
        if (!".png".equals(suffixName) && !".jpg".equals(suffixName) && !".jpeg".equals(suffixName)) {
            // 格式不为 png/jpg/jpeg
            System.out.println("图片格式不为 png/jpg/jpeg");
            return Msg.fail(403, "图片格式必须为：png/jpg/jpeg");
        }

        // 图像大小不得超过500KB，转成KB
        long pictureSize = uploadFile.getSize() / 1024;
//        System.out.println("图片大小"+pictureSize+"KB");
        if (pictureSize > 1024) {
            return Msg.fail(403, "图片大小超过1024KB(即1MB)了");
        }
        // 文件类型和大小校验结束

        // 重新生成文件名
        fileName = UUID.randomUUID() + "_" + fileName;
        // 2.获取当前项目路径与要保存的位置
        String dirPath = System.getProperty("user.dir")
                + "/files/game_cover/";
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

//        // 写到数据库表里面
//
//        user.setAvatar("/game/cover/" + fileName);
//        int i = gameService.updateById(user);

        String cover = "/game/cover/" + fileName;

        return Msg.success(200, "上传成功").add("cover", cover);
    }

    /**
     * 封面下载/获取
     *
     * @param cover
     * @param response
     * @throws IOException
     */
    @GetMapping("/cover/{cover}")
    public void download(@PathVariable String cover, HttpServletResponse response) throws IOException {
        // 获取当前项目路径
        String dirPath = System.getProperty("user.dir")
                + "/files/game_cover/";
        // 根据当前项目路径和文件名，拼接成为文件绝对路径，读取作为file对象
        File file = new File(dirPath + cover);
        // 将file对象作为文件输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        // 创建相应输出流对象
        ServletOutputStream outputStream = response.getOutputStream();

        // 设置相应头
        response.addHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(cover, "UTF-8"));

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
     * 根据游戏分类主键ggId查询游戏，顺便作分页查询
     *
     * @param currentPage 当前页，默认 1
     * @param pageSize    每页显示多少条数据，默认10条
     * @param ggId        游戏类别主键, 默认为 null，类型必须为 int的包装类 Integer
     * @return
     */
    @RequestMapping("/findGameByggId")
    public Msg findGameByggId(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") Integer ggId) {
        // 调用 service
        PageBean pb = gameService.findGameByggId(currentPage, pageSize, ggId);
        return Msg.success().add("pb", pb);
    }

    /**
     * 根据游戏名称，模糊查询游戏数据，并作分页
     *
     * @param currentPage
     * @param pageSize
     * @param gameName
     * @return
     */
    @RequestMapping("/findGameByName")
    public Msg findGameByName(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String gameName,
            @RequestParam(defaultValue = "0") Integer isDeleted) {
        // 调用service 查询
        PageBean pb = gameService.findGameByName(currentPage, pageSize, gameName, isDeleted);
        return Msg.success().add("pb", pb);
    }

    /**
     * 根据游戏主键gid查询游戏
     * 如果 isDeleted 为 null,那么说明是前台调用
     * 如果 isDeleted 为 0 或 1 , 说明是后台管理调用
     * @param gid
     * @return
     */
    @RequestMapping("/findGameByGid")
    public Msg findGameByGid(@RequestParam Integer gid, Integer isDeleted) {
        if (null == gid){
            // gid 为 null
            return Msg.fail(403, "错误， gid为null");
        }
        Game game = null;
        if (null == isDeleted){
            // 前台调用，给一个默认值 0，表示查询的游戏逻辑上存在
            isDeleted = 0;
        }else if (0 != isDeleted && 1 != isDeleted){
            // isDeleted参数不正确
            return Msg.fail(403, "错误， isDeleted参数不正确");
        }
        // 后台调用，并且 isDeleted参数正确，为 0 或 1
        game = gameService.findGameByGid(gid, isDeleted);

        return Msg.success().add("game", game);
    }


    /**
     * 添加游戏
     *
     * @param
     * @return
     */
    @RequestMapping("/addGame")
    public Msg addGame(Game game) {
//        System.out.println(game);
        game.setGmtCreate(new Date());
        game.setGmtModified(new Date());
        int i = gameService.addGame(game);

        /*
         field 'releaseTime': rejected value [2022-02-26];
         codes [typeMismatch.game.releaseTime,typeMismatch.releaseTime,
         typeMismatch.java.util.Date,typeMismatch];
         arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes
         [game.releaseTime,releaseTime]; arguments [];
         default message [releaseTime]];
         default message [Failed to convert property value of
         type 'java.lang.String' to required type 'java.util.Date'
         for property 'releaseTime'; nested exception is
         org.springframework.core.convert.ConversionFailedException:
         Failed to convert from type [java.lang.String]
         to type [@com.fasterxml.jackson.annotation.JsonFormat
         java.util.Date] for value '2022-02-26';
         nested exception is java.lang.IllegalArgumentException]]

         */

        return Msg.success();
    }

    /**
     * 修改游戏
     *
     * @param game
     * @return
     */
    @RequestMapping("/updateGame")
    public Msg updateGame(Game game) {
        game.setGmtModified(new Date()); // 修改时间
        int i = gameService.updateGame(game);
        return Msg.success(200, "修改成功");
    }

    /**
     * 单个逻辑删除游戏
     *
     * @param gid
     * @return
     */
    @RequestMapping("/deleteGameByGid")
    public Msg deleteGameByGid(Integer gid) {
        // 调用 service删除
        int i = gameService.deleteGameByGid(gid);
        if (i > 0) {
            return Msg.success(200, "删除成功！");
        }
        return Msg.fail(400, "删除失败！");
    }

    /**
     * 批量解除逻辑删除
     * @param
     * @return
     */
    @RequestMapping("/unDeletedMultiple")
    public Msg unDeletedMultiple(HttpServletRequest request) {
        String[] strArr = request.getParameterValues("arr[]");
        if (null == strArr){
            return Msg.fail(400, "错误，没有选中要解除删除的行, 空指针异常");
        }
        if (strArr.length <= 0){
            // 没有要删除的
            return Msg.fail(400, "错误，没有选中要解除删除的行");
        }
        // 可以不用转为int型数组，for循环遍历时可以直接调用业务层
        for (int i = 0; i < strArr.length; i++){
            gameService.unDeleted(Integer.parseInt(strArr[i]));
        }
        return Msg.success();
    }

    /**
     * 解除逻辑删除
     * @param gid
     * @return
     */
    @RequestMapping("/unDeleted")
    public Msg unDeleted(Integer gid) {
        int i = gameService.unDeleted(gid);
        return Msg.success();
    }

    /**
     * 批量逻辑删除游戏
     * @param request
     * @return
     */
    @RequestMapping("/deleteGameByGids")
    public Msg deleteGameByGids(HttpServletRequest request) {
        String[] gid = request.getParameterValues("gid[]");
        if (gid.length <= 0) {
            return Msg.fail(400, "错误，没有选中要删除的行！");
        } else {
            // 将字符串数组转化为数组
            int[] arr = new int[gid.length];
            for (int i = 0; i < gid.length; i++) {
                arr[i] = Integer.parseInt(gid[i]);
            }
            // 使用for循环, 逐条删除
            for (int i : arr) {
                gameService.deleteGameByGid(i);
            }
        }
        return Msg.success();
    }
}
