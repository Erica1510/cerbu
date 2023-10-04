package org.example.ElGAmalSignature;

import static org.example.ElGAmalSignature.Main.bmod;

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
    public Point sum(Point another_point, int a, int p) {
        if (this.isOpposite(another_point)) {
            return new Point(0, 0); // или return null, если хотите обозначить бесконечно удаленную точку
        }

        int l;
        if (this.equal(another_point)) {
            l = (3 * x * x + a) * bmod(2 * y, p) % p;
        } else {
            l = (another_point.y - this.y) * bmod(another_point.x - this.x, p) % p;
        }

        int x3 = (l * l - this.x - another_point.x + p) % p;
        int y3 = (l * (this.x - x3) - this.y + p) % p;

        return new Point(x3, y3);
    }

}
