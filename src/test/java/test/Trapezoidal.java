package test;

/**
 * 梯形
 * Package test
 * Created on 2016/11/7 17:24
 * version 1.0.0
 */
public class Trapezoidal extends Share {
    private double a;
    private double b;
    private double c;

    public Trapezoidal(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    @Override
    public double area() {
        return (a+b)*c/2;
    }
}
