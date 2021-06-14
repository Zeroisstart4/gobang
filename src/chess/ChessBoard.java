package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author zhou
 * @create 2021-6-14 21:29
 */
public class ChessBoard extends JFrame {

    // 棋盘
    Board board;
    // 菜单栏
    private JMenuBar upBar;
    // 菜单面板
    private JPanel downBar;
    // 菜单
    private JMenu systembutton;
    // 重新开始按钮
    private JButton restartbutton;
    // 悔棋按钮
    private JButton backbutton;
    // 退出按钮
    private JButton exitbutton;
    // 重新开始菜单项
    private JMenuItem restart;
    // 悔棋菜单项
    private JMenuItem back;
    // 退出菜单项
    private JMenuItem exit;

    // 投降按钮
    private JButton give_in;

    // 构造方法
    public ChessBoard () {

        // 创建棋盘
        board = new Board();
        // 设置标题名称
        setTitle("单机五子棋");
        // 创建菜单栏
        upBar = new JMenuBar();
        // 创建系统菜单
        systembutton = new JMenu("系统");
        // 创建重新开始菜单项
        restart = new JMenuItem("重新开始");
        // 创建悔棋菜单项
        back = new JMenuItem("悔棋");
        // 创建退出菜单项
        exit = new JMenuItem("退出");

        // 设置属性
        setJMenuBar(upBar);
        // 将菜单项添加入系统菜单
        systembutton.add(restart);
        systembutton.add(back);
        systembutton.add(exit);

        // 创建监听器
        MyItemListener lis = new MyItemListener();
        exit.addActionListener(lis);
        back.addActionListener(lis);
        restart.addActionListener(lis);

        upBar.add(systembutton);

        downBar = new JPanel();
        restartbutton = new JButton("重新开始");
        backbutton = new JButton("悔棋");
        exitbutton = new JButton("退出");
        give_in = new JButton("投降");

        exitbutton.addActionListener(lis);
        backbutton.addActionListener(lis);
        restartbutton.addActionListener(lis);
        give_in.addActionListener(lis);
        downBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        downBar.add(restartbutton);
        downBar.add(backbutton);
        downBar.add(exitbutton);
        downBar.add(give_in);

        add(downBar, BorderLayout.SOUTH);
        add (board);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 650);
        //pack();
    }
    private class MyItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            Object obj = e.getSource();
            if (obj == restart || obj == restartbutton)
            {
                System.out.println("重新开始...");
                board.restartGame();
            }
            else if (obj == exit || obj == exitbutton)
            {
                System.exit(0);
            }
            else if (obj == back || obj == backbutton)
            {
                System.out.println("悔棋...");
                board.goback();
            }
            else if (obj == give_in)
            {
                System.out.println("投降");
                board.give_in();
                board.restartGame();
            }
        }
    }

    public static void main(String[] args) {

        ChessBoard board = new ChessBoard();
        board.setVisible(true);
    }
}
