package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;


public class Frame2D extends JFrame implements KeyListener, MouseListener {

    private Board board;

    private boolean XTurn = true;

    public static void main(String[] args) {
        Frame2D jFrame2D = new Frame2D();

        jFrame2D.setTitle("TicTacToe");
        jFrame2D.setMinimumSize(new Dimension(608, 640));
        jFrame2D.setMaximumSize(new Dimension(608, 640));
        jFrame2D.setResizable(false);
        jFrame2D.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame2D.board.setFrame(getFrames()[0]);

        jFrame2D.getContentPane().add(jFrame2D.board);

        jFrame2D.pack();
        jFrame2D.setVisible(true);
    }

    public Frame2D() {
        this.board = new Board();

        addMouseListener(this);
        addKeyListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();

        board.formCursor(x, y);

        System.out.println("Coordinates : (" + x + ", " + y + ")");
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if (board.formCell(XTurn) == true) {
                XTurn = !XTurn;
            }

            System.out.println("Pressed : Enter");
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}