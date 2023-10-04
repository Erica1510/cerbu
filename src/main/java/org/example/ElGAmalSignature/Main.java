package org.example.ElGAmalSignature;

import java.util.ArrayList;

public class Main {
    private static String report = "report.txt";
    private static int p = 11, a = -2,b = 2;

    static int bmod(int b,int n) {
        int n0 = n,
                b0 = b,
                t0 = 0,
                t = 1,
                q = n0 / b0,
                r = n0 - q * b0,
                temp;
        while (r > 0) {
            temp = t0 - q * t;
            if (temp >= 0)
                temp = temp % n;
            else
                temp = n - (-temp) % n;
            //System.out.println("temp = " + temp);
            n0 = b0;
            b0 = r;
            t0 = t;
            t = temp;
            q = n0 / b0;
            r = n0 - q * b0;
            if (b0 != 1) ;
            else return t;
        }
        return t;
    }

    public static int minusModulo(int x,int modulo){
        while (x < 0){
            x = modulo + x;
        }
        x %= modulo;
        return x;
    }

    public static Point sumPoint(Point point1, Point point2, int i) throws Exception {
        int x1 = point1.x,
                y1 = 1,
                x2 = point2.x,
                y2 = point2.y,
                l = 0;

//        if (x2 - x1 != 0) {
//            FileWork.reportWriteln(report, "\tx" + (i + 1) + " - x" + i + " != 0");
//            FileWork.reportWrite(report, "\tl = (y2 - y1) / (x2 - x1) = " +
//                    y2 + " - " + y1 + ") / (" + x2 + " - " + x1 + ") = "
//            );
//            if ((y2 - y1) % (x2 - x1) == 0) {
//                l = (y2 - y1) / (x2 - x1);
//                FileWork.reportWrite(report, (y2 - y1) + " / " + (x2 - x1) + " = ");
//            } else {
//                l = (y2 - y1) * bmod(x2 - x1, p);
//                FileWork.reportWrite(report, (y2 - y1) + " / " + (x2 - x1) + "^-1 mod " + p + " = ");
//            }
//            FileWork.reportWrite(report, l + " mod " + p + " = ");
//        }
//        else {
//            FileWork.reportWriteln(report, "\tx" + (i + 1) + " - x" + i + " == 0");
//            FileWork.reportWrite(report,"\tl = (3 * x1^2 + a) / (2 * y1) = (3 * " +
//                                                (int)Math.pow(x1,2) + " + " + a + ") / (2 * " + y1 + ") = "
//
//            );
//            if ((3 * (int)Math.pow(x1,2) + a) % (2 * y1) == 0){
//                FileWork.reportWrite(report,(3 * (int)Math.pow(x1,2) + a) + " / " + (2 * y1) + " = ");
//                l = (3 * (int)Math.pow(x1,2) + a) / (2 * y1);
//            }
//            else {
//                FileWork.reportWrite(report,(3 * (int)Math.pow(x1,2) + a) + " * " + (2 * y1) + "^-1 mod " + p + " = " +
//                                                   (3 * (int)Math.pow(x1,2) + a) + " * " + bmod(2 * y1,p) + " = ");
//                l = (3 * (int)Math.pow(x1,2) + a) * bmod(2 * y1,p);
//            }
//            FileWork.reportWrite(report,l + " mod " + p + " = ");
//        }
//        l = minusModulo(l,p);
        l = 10;
        FileWork.reportWriteln(report,"" + l);

        int x3 = ((int)Math.pow(l,2) - x2 - x1);
        FileWork.reportWrite(report,"\tx" + (i + 2) + " = l^2 - x" + i + " - x" + (i + 1) + " = " +
                                            (int)Math.pow(l,2) + " - " + x1 + " - " + x2 + " = " +
                                            x3 + " mod " + p + " = ");
        x3 = minusModulo(x3,p);
        FileWork.reportWriteln(report,x3 + "");
        int y3 = l * (x1 - x3) - y1;
        FileWork.reportWrite(report,"\ty" + (i + 2) + " = l(x" + i + " - x" + (i + 2) +") - y1 = " + l + "(" + x1 + " - " + x3 + ") - " + y1 + " = "
                                                + y3 + " mod " + p + " = ");
        y3 = minusModulo(y3,p);
        FileWork.reportWriteln(report,y3 + "");

        return new Point(x3,y3);
    }

    public static Point sumPoint2(Point point1, Point point2){
        int x1 = point1.x,
            y1 = point1.y,
            x2 = point2.x,
            y2 = point2.y,
            l;
        return new Point();
    }

    public static int z(int x) throws Exception {
        int z = (a * (int)Math.pow(x,3) + b * x + 1) % p;
        //FileWork.reportWriteln(report,"\t z = " + z);
        while (z < 0){
            //FileWork.reportWrite(report,"\t\tz = " + p + " + " + z + " = ");
            z = p + z;
            //FileWork.reportWriteln(report,z + "");
        }
        z %= p;
        return z;
    }

    public static void main(String[] args) throws Exception {
        FileWork.reportCreate(report);
        ArrayList<Integer> A2 = new ArrayList();
        ArrayList<Integer> X = new ArrayList();
        ArrayList<Point> RESULT = new ArrayList<>();
        for (int i = 0; i < p; i++) {
          FileWork.reportWriteln(report,"a = " + i + " | => a2 mod p = " + (int)Math.pow(i,2) % p);
          A2.add((int)Math.pow(i,2) % p);
           FileWork.reportWriteln(report,"x = " + i + " => z = " + z(i));
          X.add(z(i));
        }
        for (int i = 0; i < X.size(); i++) {
            FileWork.reportWrite(report,"X = " + i + " (");
            for (int j = 0; j < A2.size(); j++) {
                if (X.get(i) == A2.get(j)){
                    FileWork.reportWrite(report,j + ",");
                    RESULT.add(new Point(i,j));
                }
            }
            FileWork.reportWriteln(report,")");
        }
        for (int i = 0; i < RESULT.size(); i++) {
            FileWork.reportWrite(report,"(" + RESULT.get(i).x + "," + RESULT.get(i).y + ")  ");
        }
        FileWork.reportWriteln(report);
        ArrayList<Point> RESULT2 = new ArrayList<>();
        RESULT2.add(RESULT.get(0));
        RESULT2.add(RESULT.get(0));
        for (int i = 1; i < RESULT.size(); i++) {
            FileWork.reportWriteln(report,"RESULT is (" + RESULT2.get(i).x + "," + RESULT2.get(i).y + ")\n");
            FileWork.reportWriteln(report,"Point" + (i) + " is (" + RESULT2.get(i - 1).x + "," + RESULT2.get(i - 1).y + ")");
            FileWork.reportWriteln(report,"Point" + (i + 1) + " is (" + RESULT2.get(i).x + "," + RESULT2.get(i).y + ")");
            RESULT2.add(sumPoint(RESULT2.get(i - 1),RESULT2.get(i),i));
        }
    }
}
