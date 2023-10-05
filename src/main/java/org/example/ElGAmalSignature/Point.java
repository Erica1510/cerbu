package org.example.ElGAmalSignature;

public class Point {
    public int x;
    public int y;
    public Point() {}
    public Point(int x,int y){
        this.x = x;
        this.y = y;
    }
    public boolean equal(Point another_point){
        if (this.x == another_point.x && this.y == another_point.y) return true;
        else return false;
    }
    public boolean isOpposite(Point another_point){
        if (this.x == another_point.x && -this.y == another_point.y) return true;
        else return false;
    }
    public Point sum(Point another_point){
        int x3,y3;
        if (isOpposite(another_point)){
            return new Point(0,0);
        }
        int l = (another_point.y - this.y) / (another_point.x - this.x);
        x3 = (int)Math.pow(l,2) - another_point.x - this.x;
        return new Point();
    }
}
