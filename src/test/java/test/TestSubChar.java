package test;

import java.util.Scanner;

/**
 * 截取字符串
 * 在控制台输入一个字符串，以及需要截取的字节数，则根据要求输出对应的字符串。【注意:保证汉字不被截半个】
 * 例如:
 * 我ABCD + 5 ==> 我ABC
 * 我AB测试BCD + 8 ==> 我AB测试
 * 我AB测试BDD + 7 ==> 我AB测
 *
 * Created on 2016/11/7 17:40
 * version 1.0.0
 */
public class TestSubChar {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int index = scanner.nextInt();
        String result = subChar(str, index);
        System.out.println(result);
    }

    public static String subChar(String str, int len) {
        if (null == str || "".equals(str.trim())) {
            return "error:字符为空!";
        }
        if (len < 0) {
            return "截取起点为负!";
        }

        char[] chars = str.toCharArray();
        for (int i = 0,size = chars.length; i < size; i++){
            if (isChinese(chars[i])){
                len--;
                if (len <= 0){
                    len = i; // 保证汉字不被截半个
                }
            }
        }
        return str.substring(0, len);
    }

    /**
     * 是否为中文
     * @param c
     * @return
     */
    private static final boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
}
