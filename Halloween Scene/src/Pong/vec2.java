package Pong;

import java.awt.*;

public class vec2 {
    public double x;
    public double y;

    public vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public vec2(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public double get_distance() {
        return Math.sqrt((x*x) + (y*y));
    }

    public Point toPoint() {
        return new Point((int) x, (int) y);
    }

    public String toString() {
        return String.format("{%.2f, %.2f}", x, y);
    }

    public vec2 add(vec2 other) {
        return new vec2(this.x + other.x, this.y + other.y);
    }

    public vec2 mul(vec2 other) {
        return new vec2(this.x * other.x, this.y * other.y);
    }

    public vec2 mul(int other) {
        return new vec2(this.x * other, this.y * other);
    }





}
