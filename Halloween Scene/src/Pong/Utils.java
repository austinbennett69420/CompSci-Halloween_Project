package Pong;

public class Utils {

    public static double clamp(double x, double min, double max) {
        return x < min ? min:Math.min(x, max);
    }
}
