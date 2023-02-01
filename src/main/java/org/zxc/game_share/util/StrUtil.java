package org.zxc.game_share.util;

/**
 * 字符串工具类
 * 写了许多重复代码后，要改进
 * 所以封装这个类
 * @author 知行成zxc
 * @since 2021-11-2
 */
public class StrUtil {
    private StrUtil(){}

    /**
     * 判断字符串是否为 null 或 ""
     * 如果是的话，返回 true，否则返回 false
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if (str == null || str.equals("")){
            return true;
        }else {
            return false;
        }
    }
}
