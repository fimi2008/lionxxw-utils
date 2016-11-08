package test;

import java.io.*;

/**
 * Created on 2016/11/8 9:16
 * version 1.0.0
 */
public class TestIO {

    public static void main(String[] args) {
        String path = "D:/test/test.txt";
        createTxt(path);
        readTxt(path);
    }

    public static void readTxt(String path){
        try {
            FileReader fr = new FileReader(new File(path));
            BufferedReader br = new BufferedReader(fr);
            String str = br.readLine();
            while (null != str){
                System.out.println(str);
                str = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createTxt(String path){
        String str1 = "我叫王翔";
        String str2 = "我是参加北风网入班测试的学生";
        File file = new File(path);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write(str1);
            fw.write("\r\n");
            fw.write(str2);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fw){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("文件创建完毕,开始读取:");
    }
}

