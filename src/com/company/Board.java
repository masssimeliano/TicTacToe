package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

public class Board extends JComponent {

    private Frame frame = new Frame();

    private final int widthField = 600;
    private final int heightField = 600;

    private final int widthCell = widthField / 3;
    private final int heightCell = heightField / 3;

    private int x = 1;
    private int y = 1;

    private int fullCellsCount = 0;

    private int[][] cells = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.setColor(Color.BLACK);

        vivField();

        endGameCheck(graphics);

        drawField(graphics);
        drawCursor(graphics);
        drawCells(graphics);
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    private void drawField(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setColor(Color.BLACK);

        graphics2D.drawRect(0, 0, widthField, heightField);
        graphics2D.drawRect(0, heightCell, widthField, heightCell);
        graphics2D.drawRect(widthCell, 0, widthCell, heightField);
    }

    private void drawCursor(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setColor(Color.ORANGE);

        graphics2D.drawRect(x * widthCell, y * widthCell, widthCell, heightCell);
    }

    public void formCursor(int x, int y) {
        for (int i = 0; i <= 2; i++)
            for (int j = 0; j <= 2; j++) {
                if ((x >= i * widthCell + 8) && (x <= (i + 1) * widthCell + 8) && (y >= j * heightCell + 30) && (y <= (j + 1) * heightCell + 30)) {
                    if (cells[i][j] == 0) {
                        this.x = i;
                        this.y = j;
                    }
                }
            }

        repaint();
    }

    private void drawCells(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        for (int i = 0; i <= 2; i++)
            for (int j = 0; j <= 2; j++) {
                if (cells[i][j] == 1) {
                    graphics2D.setPaint(Color.RED);

                    graphics2D.drawLine(i * widthCell, j * heightCell, (i + 1) * widthCell, (j + 1) * heightCell);
                    graphics2D.drawLine((i + 1) * widthCell, j * heightCell, i * widthCell, (j + 1) * heightCell);
                }

                if (cells[i][j] == 2) {
                    graphics2D.setPaint(Color.BLUE);

                    graphics2D.drawOval(i * widthCell, j * heightCell, widthCell, heightCell);
                }
            }
    }

    private void drawWin(Graphics graphics, int x1, int y1, int x2, int y2, int x3, int y3) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setPaint(Color.ORANGE);

        graphics2D.fillRect(x1 * widthCell, y1 * widthCell, widthCell, heightCell);
        graphics2D.fillRect(x2 * widthCell, y2 * widthCell, widthCell, heightCell);
        graphics2D.fillRect(x3 * widthCell, y3 * widthCell, widthCell, heightCell);
    }

    public boolean formCell(boolean XTurn) {
        boolean successfullyFormed = true;

        if (cells[x][y] == 0) {
            if (XTurn == true)
                cells[x][y] = 1;
            else
                cells[x][y] = 2;

            fullCellsCount = fullCellsCount + 1;

            repaint();
        }
        else
            successfullyFormed = false;

        return successfullyFormed;
    }

    private String checkWin(Graphics graphics) {

        for (int i = 0; i <= 2; i++)
        {
            for (int j = 1; j <= 2; j++) {
                if ((cells[i][0] == j) && (cells[i][1] == j) && (cells[i][2] == j)) {
                    drawWin(graphics, i, 0, i, 1, i, 2);

                    if (j == 1)
                        return "X";
                    else
                        return "O";
                }

                if ((cells[0][i] == j) && (cells[1][i] == j) && (cells[2][i] == j)) {
                    drawWin(graphics, 0, i, 1, i, 2, i);

                    if (j == 1)
                        return "X";
                    else
                        return "O";
                }
            }
        }

        for (int j = 1; j <= 2; j++) {
            if ((cells[0][0] == j) && (cells[1][1] == j) && (cells[2][2] == j)) {
                drawWin(graphics, 0, 0, 1, 1, 2, 2);

                if (j == 1)
                    return "X";
                else
                    return "O";
            }
            if ((cells[0][2] == j) && (cells[1][1] == j) && (cells[2][0] == j)) {
                drawWin(graphics, 0, 2, 1, 1, 2, 0);

                if (j == 1)
                    return "X";
                else
                    return "O";
            }
        }

        return "";
    }

    private void vivField() {
        for (int i = 0; i <= 2; i++)
            System.out.println(cells[0][i] + " " + cells[1][i] + " " + cells[2][i]);
    }

    private void endGameCheck(Graphics graphics) {
        if (fullCellsCount >= 5) {
            String result = checkWin(graphics);

            if (result.equals("X") == true) {
                System.out.println("Check : X Won!");

                drawCells(graphics);

                frame.disable();

                System.out.println("Frame is disabled");
                // frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
            else
                if (result.equals("O") == true) {
                    System.out.println("Check : O Won!");

                    drawCells(graphics);

                    frame.disable();

                    System.out.println("Frame is disabled");
                    // frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }
                else
                    System.out.println("Check : No One Won!");
        }
    }

}
