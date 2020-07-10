package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Board extends JComponent {

    private final int widthField = 600;
    private final int heightField = 600;

    private final int widthCell = widthField / 3;
    private final int heightCell = heightField / 3;

    private int x = 1;
    private int y = 1;

    private int[][] cells = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

    private int fullCellsCount = 0;

    private boolean flagWin = false;

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.setColor(Color.BLACK);

        vivField();

        drawHintTable(graphics);

        endGameCheck(graphics);

        drawField(graphics);
        drawIcon(graphics);
        drawCursor(graphics);
        drawCells(graphics);
    }

    public boolean isFlagWin() {
        return flagWin;
    }

    public void setFreeCells() {
        cells = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

        fullCellsCount = 0;

        x = 1;
        y = 1;

        flagWin = false;
    }

    private void drawField(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setColor(Color.BLACK);

        for (int i = 0; i <= 2; i++) {
            graphics2D.drawRect(0 + i, 0 + i, widthField - 2 * i, heightField - 2 * i);
            graphics2D.drawRect(0 + i, heightCell + i, widthField - 2 * i, heightCell - 2 * i);
            graphics2D.drawRect(widthCell + i, 0 + i, widthCell - 2 * i, heightField - 2 * i);
        }
    }

    private void drawCursor(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setColor(Color.ORANGE);

        graphics2D.drawRect(x * widthCell, y * widthCell, widthCell, heightCell);
        graphics2D.drawRect(x * widthCell + 1, y * widthCell + 1, widthCell - 2, heightCell - 2);
        graphics2D.drawRect(x * widthCell + 2, y * widthCell + 2, widthCell - 4, heightCell - 4);
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

                    for (int l = -1; l<= 1; l++) {
                        graphics2D.drawLine(i * widthCell + l, j * heightCell, (i + 1) * widthCell + l, (j + 1) * heightCell);
                        graphics2D.drawLine((i + 1) * widthCell + l, j * heightCell, i * widthCell + l, (j + 1) * heightCell);
                    }
                }

                if (cells[i][j] == 2) {
                    graphics2D.setPaint(Color.BLUE);

                    for (int l = 0; l <= 2; l++)
                        graphics2D.drawOval(i * widthCell + l, j * heightCell + l, widthCell - 2 * l, heightCell - 2 * l);
                }
            }
    }

    private void drawIcon(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        Image image = null;

        try {
            image = ImageIO.read(new File("C:\\Users\\Admin\\IdeaProjects\\TicTacToeGame\\src\\resources\\icon.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        graphics2D.drawImage(image, 450, 613, 125, 125, null);
    }

    private void drawWin(Graphics graphics, int x1, int y1, int x2, int y2, int x3, int y3) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setPaint(Color.ORANGE);

        graphics2D.fillRect(x1 * widthCell, y1 * widthCell, widthCell, heightCell);
        graphics2D.fillRect(x2 * widthCell, y2 * widthCell, widthCell, heightCell);
        graphics2D.fillRect(x3 * widthCell, y3 * widthCell, widthCell, heightCell);
    }

    private void drawHintTable(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setPaint(Color.GRAY);
        graphics.fillRect(0, 600, 600, 200);

        graphics2D.setPaint(Color.BLACK);
        graphics2D.setFont(new Font("Arial", Font.BOLD, 20));

        graphics2D.drawString("Instruction : ", 0, 620);
        graphics2D.drawString(" - Use Mouse to choose cell", 0, 650);
        graphics2D.drawString(" - Use Enter to place an X/O", 0, 680);
        graphics2D.drawString(" - Use F5 to restart game", 0, 710);

        graphics2D.setFont(new Font("Arial", Font.BOLD + Font.CENTER_BASELINE, 20));

        graphics2D.drawString("P.S. You may play with your friend! :)", 0, 740);
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
        System.out.println("Field :");

        for (int i = 0; i <= 2; i++)
            System.out.println(cells[0][i] + " " + cells[1][i] + " " + cells[2][i]);
    }

    private void endGameCheck(Graphics graphics) {
        if (fullCellsCount >= 5) {
            String result = checkWin(graphics);

            flagWin = true;

            if (result.equals("X") == true) {
                System.out.println("Check : X Won!");

                drawCells(graphics);
            }
            else
                if (result.equals("O") == true) {
                    System.out.println("Check : O Won!");

                    drawCells(graphics);
                }
                else {
                    System.out.println("Check : No One Won!");

                    flagWin = false;
                }
        }
    }

}
