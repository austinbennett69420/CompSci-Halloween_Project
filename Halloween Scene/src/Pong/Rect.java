package Pong;

import java.util.ArrayList;

public class Rect {

    public double x;
    public double y;
    public double w;
    public double h;

    public Rect(double X, double Y, double width, double height) {
        x = X;
        y = Y;
        w = width;
        h = height;
    }

    public Rect addPos(vec2 v) {
        return new Rect(this.x + v.x,
        this.y + v.y,
        w,
        h);
    }

    public Rect getCopy() {
        return new Rect(x, y, w, h);
    }

    public static boolean collideRect(Rect a, Rect b) {
        if (a.x + a.w < b.x || b.x + b.w < a.x) {
            return false;
        }
        if (a.y + a.h < b.y || b.y + b.h < a.y) {
            return false;
        }
        return true;
    }



    public static ArrayList<Rect> collideRects(Rect a, Rect... rects) {
        //returns a list of all rects colliding with A
        ArrayList<Rect> ret = new ArrayList<>();
        for (Rect r: rects) {
            if (collideRect(a, r)) {
                ret.add(r);
            }
        }

        return ret;
    }
    public static boolean isCollideRects(Rect a, Rect... rects) {
        //returns a list of all rects colliding with A
        ArrayList<Rect> ret = new ArrayList<>();
        for (Rect r: rects) {
            if (collideRect(a, r)) {
                return true;
            }
        }

        return false;
    }

    public String toString() {
        return String.format("{%.2f, %.2f, %.2f, %.2f}", x, y, w, h);
    }


}
