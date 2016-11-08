package test;

/**
 * 圆
 * Package test
 * Created on 2016/11/7 17:25
 * version 1.0.0
 */
public class Round extends Share {
    private double r;

    public Round(double r) {
        this.r = r;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    @Override
    public double area() {
        return Math.PI*r*r;
    }
}
