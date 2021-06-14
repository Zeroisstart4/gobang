package chess;

import java.awt.*;

/**
 * @author zhou
 * @create 2021-6-14 20:01
 */
// 棋子坐标点
public class Point {
    // 横坐标
    private int x;
    // 纵坐标
    private int y;
    // 棋子颜色
    private Color color;
    // 棋子直径
    public static final int DIAMETER = 30;

    public Point(int x, int y, Color color) {
        super();
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
}
