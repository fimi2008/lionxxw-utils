package test;

import java.util.Scanner;

/**
 * 截取字符串
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

        byte[] b = str.getBytes();
        if (len != 0) {
            if (b[len - 1] < 0) {
                if (b[len] < 0)
                    len = len - 1;
            }
        }
        return new String(b, 0, len);
    }
}
