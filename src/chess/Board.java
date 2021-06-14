package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author zhou
 * @create 2021-6-14 19:56
 */
public class Board extends JPanel implements MouseListener {

    // 页边距
    public static final int MARGIN = 40;
    // 网格宽度
    public static final int GRID_SPAN = 45;
    // 行数
    public static final int ROWS = 10;
    // 列数
    public static final int COLS = 10;

    // 棋子集合
    Point[] chessList = new Point[(ROWS + 1) * (COLS + 1)];
    // 是否为黑棋
    boolean isBlack = true;
    // 游戏结束
    boolean gameOver = false;
    // 统计棋子数
    int chessCount = 0;

    // 最后一颗棋子的坐标
    int xIndex;
    int yIndex;

    // 棋盘构造方法
    public Board() {
        // 设置背景为黑色
        setBackground(Color.orange);
        // 添加鼠标监听器
        addMouseListener(this);
        // 添加鼠标运动监听器
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }
            // 鼠标移动逻辑
            @Override
            public void mouseMoved(MouseEvent e) {
                // 鼠标横坐标
                int x1 = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
                // 鼠标纵坐标
                int y1 = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
                // 健壮性判断
                if(x1 < 0 || x1 > ROWS || y1 < 0 || y1 > COLS || gameOver) {
                    // 设置光标
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                else {
                    // 设置光标
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
        });
    }

    // 涂色
    @Override
    public void paintComponent(Graphics g) {
        // 调用父构造方法
        super.paintComponent(g);

        // 绘制棋盘横线
        for (int i = 0; i <= ROWS; i++) {
            g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + COLS * GRID_SPAN, MARGIN + i * GRID_SPAN);
        }
        // 绘制棋盘竖线
        for (int i = 0; i <= COLS; i++) {
            g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN, MARGIN + ROWS * GRID_SPAN);
        }

        // 添加棋子
        for (int i = 0; i < chessCount; i++) {
            // 棋子横纵坐标
            int xPos = chessList[i].getX() * GRID_SPAN + MARGIN;
            int yPos = chessList[i].getY() * GRID_SPAN + MARGIN;
            // 设置棋子颜色
            g.setColor(chessList[i].getColor());
            // 添加入棋盘
            g.fillOval(xPos - Point.DIAMETER / 2, yPos - Point.DIAMETER / 2, Point.DIAMETER, Point.DIAMETER);

            // 在最后一步棋上设置红色的外边框
            if(i == chessCount - 1) {
                g.setColor(Color.RED);
                g.drawRect(xPos - Point.DIAMETER / 2, yPos - Point.DIAMETER / 2, Point.DIAMETER, Point.DIAMETER);
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    // 点击鼠标发生的事件
    @Override
    public void mousePressed(MouseEvent e) {
        // 健壮性判断
        if (gameOver) {
            return;
        }
        // 棋子颜色及其横纵坐标
        String colorName = isBlack ? "黑棋" : "白棋" ;
        xIndex = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
        yIndex = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;

        // 健壮性判断
        if (xIndex < 0 || xIndex > ROWS || yIndex < 0 || yIndex > COLS) {
            return;
        }
        // 若此处（xIndex, yIndex）已经有棋子
        if (findChess(xIndex, yIndex)) {
            return;
        }

        // 创建棋子
        Point ch = new Point(xIndex, yIndex, isBlack ? Color.black : Color.white);
        // 加入棋子列表
        chessList[chessCount++] = ch;
        // 重新上色
        repaint();

        // 判断是否胜利
        if (isWin()) {
            // 若已经胜利，则弹出提示
            String msg = String.format("恭喜，%s获得胜利！", colorName);
            JOptionPane.showMessageDialog(this, msg);
            gameOver = true;
        }
        // 黑白棋轮流落子
        isBlack = !isBlack;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // 查找该点是否已经有棋子
    private boolean findChess(int xIndex, int yIndex) {
        // 遍历
        for (Point ch : chessList) {
            // 若该点存在棋子
            if (ch != null && ch.getX() == xIndex && ch.getY() == yIndex) {
                return true;
            }
        }

        return false;
    }

    // 判断是否胜利
    private boolean isWin() {

        // 连接的棋子数，当到达 5 时，则胜利
        int continueCount = 1;

        for (int i = xIndex - 1; i >= 0; i--) {

            Color color = isBlack ? Color.BLACK : Color.WHITE;
            if(getChess(i, yIndex, color) != null) {
                continueCount++;
            }
            else {
                break;
            }
        }

        for (int i = xIndex + 1; i <= ROWS; i++) {

            Color color = isBlack ? Color.BLACK : Color.WHITE;
            if(getChess(i, yIndex, color) != null) {
                continueCount++;
            }
            else {
                break;
            }
        }

        if (continueCount >= 5) {
            return true;
        }
        else {
            continueCount = 1;
        }

        for (int y = yIndex - 1; y >= 0; y--) {

            Color color = isBlack ? Color.BLACK : Color.WHITE;
            if (getChess(xIndex, y, color) != null) {
                continueCount++;
            }
            else {
                break;
            }
        }

        for (int y = yIndex + 1; y <= ROWS; y++) {

            Color color = isBlack ? Color.BLACK : Color.WHITE;
            if(getChess(xIndex, y, color) != null) {
                continueCount++;
            }
            else {
                break;
            }
        }

        if (continueCount >= 5) {
            return true;
        }
        else {
            continueCount = 1;
        }

        for (int x = xIndex + 1, y = yIndex - 1; y >= 0 && x <= ROWS; x++, y--) {
            Color c = isBlack ? Color.black : Color.white;
            if (getChess(x, y, c) != null) {
                continueCount++;
            }
            else {
                break;
            }
        }
        for (int x = xIndex - 1, y = yIndex + 1; y <= COLS && x >= 0; x--, y++) {
            Color c = isBlack ? Color.black : Color.white;
            if (getChess(x, y, c) != null) {
                continueCount++;
            }
            else {
                break;
            }
        }

        if (continueCount >= 5) {
            return true;
        }
        else {
            continueCount = 1;
        }

        for (int x = xIndex - 1, y = yIndex - 1; y >= 0 && x >= 0; x--, y--) {

            Color c = isBlack ? Color.black : Color.white;
            if (getChess(x, y, c) != null) {
                continueCount++;
            }
            else {
                break;
            }
        }

        for (int x = xIndex + 1, y = yIndex + 1; y <= COLS && x <= ROWS; x++, y++) {

            Color c = isBlack ? Color.black : Color.white;
            if (getChess(x, y, c) != null) {
                continueCount++;
            }
            else {
                break;
            }
        }

        if (continueCount >= 5) {
            return true;
        }
        else {
            continueCount = 1;
        }

        return false;
    }

    // 获取棋子
    private Point getChess (int x, int y, Color color) {

        for (Point c : chessList) {
            if (c != null && c.getX() == x && c.getY() == y && c.getColor() == color) {
                return c;
            }
        }
        return null;
    }

    // 重新开始游戏
    public void restartGame () {
        // 将棋子集和置空
        for (int i = 0; i < chessList.length; i++) {
            chessList[i] = null;
        }
        // 调整为黑棋先下
        isBlack = true;
        // 游戏未结束
        gameOver = false;
        // 棋子数重置为 0
        chessCount = 0;
        // 重新上色
        repaint();
    }

    // 悔棋
    public void goback() {

        if (chessCount == 0) {
            return ;
        }
        // 将最后一步棋置空
        chessList[chessCount - 1] = null;
        // 棋子数量相应减少
        chessCount--;

        // 重新获取最后一颗棋子的坐标
        if (chessCount > 0) {
            xIndex = chessList[chessCount - 1].getX();
            yIndex = chessList[chessCount - 1].getY();
        }
        // 轮流落子
        isBlack = !isBlack;
        repaint();
    }

    // 投降
    public void give_in () {
        String msg = String.format("%s投降啦", isBlack ? "黑棋" : "白棋");
        JOptionPane.showConfirmDialog(this, msg);
    }
}
