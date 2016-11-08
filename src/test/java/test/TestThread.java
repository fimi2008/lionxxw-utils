package test;

/**
 * 交通灯多线程实现
 * Package test
 * Project lionxxw-utils
 * Company www.baofoo.com
 * Author wangjian@baofoo.com
 * Created on 2016/11/8 9:20
 * version 1.0.0
 */
public class TestThread {
    private static boolean flag = true;
    private static int count = 0;

    public static void main(String[] args) {
        Thread green = new Thread(){
            @Override
            public void run() {
                while (true){
                    if (flag){
                        count++;
                        System.out.println("绿灯闪"+count+"秒");
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (count >= 10){
                            count = 0;
                            flag = !flag;
                        }
                    }
                }
            }
        };

        Thread red = new Thread(){
            @Override
            public void run() {
                while (true){
                    if (!flag){
                        count++;
                        System.out.println("红灯闪"+count+"秒");
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (count >= 30){
                            count = 0;
                            flag = !flag;
                        }
                    }
                }
            }
        };

        green.start();
        red.start();
    }
}
