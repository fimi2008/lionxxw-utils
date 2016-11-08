package test;

/**
 * 测试形状面积
 * Package test
 * Project lionxxw-utils
 * Company www.baofoo.com
 * Author wangjian@baofoo.com
 * Created on 2016/11/7 17:26
 * version 1.0.0
 */
public class TestShare {
    public static void main(String[] args) {
        Share[] arrs = {new Trapezoidal(2,3,4), new Triangle(3,4,5), new Round(2)};
        for (Share arr : arrs){
            System.out.println(arr.area());
        }
    }
}
