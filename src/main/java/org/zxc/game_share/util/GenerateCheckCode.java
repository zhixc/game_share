package org.zxc.game_share.util;

/**
 * 验证码字符串生成工具类
 * 随机生成计算加减法
 * @author 知行成
 * @since 2021-10-27
 */
public class GenerateCheckCode {
    //    private static String strNum = "0123456789";
    private static String strPlus = "+-";
    public static int result;

    /**
     * 构造方法私有化
     */
    private GenerateCheckCode() {
    }

    /**
     * 生成验证码方法
     * @return
     */
    public static String getCheckCode() {
        // 随机生成3个数
        int num = (int) (Math.random() * 10);
        int num2 = (int) (Math.random() * 10);
        int num3 = (int) (Math.random() * 2);

        /*if (num3 == 1){
            //减法
            int result = num - num2;
        }else {
            int result = num + num2;
        }*/
        // 使用三元运算符改进if-else结构
        result = num3 == 1 ? num - num2 : num + num2;

        char c = strPlus.charAt(num3);
        StringBuilder sb = new StringBuilder();
        sb.append(num).append(c).append(num2).append("=?");
        return sb.toString();
    }
}
