package test;

/**
 * 三角形
 * Package test
 * Created on 2016/11/7 17:17
 * version 1.0.0
 */
public class Triangle extends Share{
    private double a;
    private double b;
    private double c;
    private double p;

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.p = getP();
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

    public double getP(){
        return (a+b+c)/2;
    }

    @Override
    public double area() {
        return Math.sqrt(p*(p-a)*(p-b)*(p-c));
    }
}