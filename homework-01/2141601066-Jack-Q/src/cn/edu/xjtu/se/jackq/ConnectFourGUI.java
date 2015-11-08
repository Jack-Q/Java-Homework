/*
 * Created by JFormDesigner on Fri Oct 23 10:48:47 CST 2015
 */

package cn.edu.xjtu.se.jackq;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

/**
 * A simple Swing application which implements a GUI version of
 * ConnectFour based on its console version.
 *
 * @author Jack Q (qiaobo@outlook.com)
 * @see ConnectFour
 */
public class ConnectFourGUI extends ConnectFour {
    // Disk color
    private static final Color BLANK_COLOR = new Color(0xddeeeeee, true);
    private static final Color RED_COLOR = new Color(0xeefe3a22, true);
    private static final Color YELLOW_COLOR = new Color(0xeecaf313, true);

    // Background color
    private static final Color BACKGROUND_ODD = new Color(0xff409cef);
    private static final Color BACKGROUND_EVEN = new Color(0xff107cbf);
    private static final Color BACKGROUND_HIGHLIGHT_RED = new Color(0xffd0acbf);
    private static final Color BACKGROUND_HIGHLIGHT_YELLOW = new Color(0xffb0dcbf);

    /** Current background highlight color. It will be changed during alternating player */
    private static Color backgroundHighlight = new Color(0xff60ccff);

    /**
     * main windows frame of this game
     */
    private static class ConnectFourGUIFrame extends JFrame {
        private final ConnectFourGUI game;
        private JLabel prompter;
        private JLabel status;
        private Disk[][] gameBoard;

        public ConnectFourGUIFrame(ConnectFourGUI game) {
            this.game = game;
            initComponents();
        }

        public void prompt(String str) {
            prompter.setText(str);
            prompter.setBackground(new Color(0xcc307cae, true));
            repaint();

        }

        public void promptReset() {
            prompter.setText("");
            prompter.setBackground(new Color(0, true));
            repaint();
        }

        private void diskClickHandler(MouseEvent e) {
            promptReset();
            Disk disk = (Disk) e.getComponent();

            if (game.isFinish()) {
                prompt("The game has finished!");
                return;
            }

            int currentColumn = disk.getColumn();
            if (!game.canDropDisk(currentColumn)) {
                prompt("The selected column is filled,\n please select another one!");
                return;
            }
            int currentRow = ROW - game.dropCurrentDisk(currentColumn);
            gameBoard[currentRow][currentColumn].setDiskColor(game.currentColor);
            game.nextStep();
        }


        private void highlightColumn(int column) {
            for (Disk[] diskRow : gameBoard) {
                for (Disk disk : diskRow) {
                    disk.setHighlight(column == disk.getColumn());
                }
            }
            this.repaint();
        }

        /**
         * Draw the UI and setup events handler
         */
        private void initComponents() {
            int width = 600;
            int height = 500;

            this.setTitle("Connect Four");
            Container basePane = getContentPane();
            basePane.setLayout(null);

            JPanel contentPane = new JPanel(new GridLayout(ROW, COLUMN));
            basePane.add(contentPane);
            contentPane.setSize(width, height);

            gameBoard = new Disk[ROW][COLUMN];
            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COLUMN; j++) {
                    Disk disk = new Disk(i, j);
                    disk.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            diskClickHandler(e);
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            if (game.isFinish()) return;
                            Disk disk = (Disk) e.getComponent();
                            int column = disk.getColumn();
                            highlightColumn(column);
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            if(game.isFinish()) return;
                            highlightColumn(-1);
                        }
                    });
                    contentPane.add(disk);
                    gameBoard[i][j] = disk;
                }
            }

            JPanel indexPane = new JPanel();
            indexPane.setLayout(new BoxLayout(indexPane, BoxLayout.Y_AXIS));
            basePane.add(indexPane);
            basePane.setComponentZOrder(indexPane, 0);
            indexPane.setLocation(0, 10);
            indexPane.setSize(width, 120);
            indexPane.setBackground(new Color(0, true));

            prompter = new JLabel();
            prompter.setHorizontalTextPosition(SwingConstants.CENTER);
            prompter.setOpaque(true);
            prompter.setSize(600, 30);
            prompter.setBackground(new Color(0, true));
            prompter.setForeground(new Color(0xccffffff, true));
            prompter.setFont(prompter.getFont().deriveFont(Font.BOLD, 18.0f));
            indexPane.add(prompter);
            status = new JLabel();
            status.setHorizontalTextPosition(SwingConstants.LEFT);
            status.setHorizontalAlignment(SwingConstants.LEFT);
            status.setOpaque(true);
            status.setSize(150, 40);
            status.setBackground(new Color(0xccfcac12, true));
            status.setForeground(new Color(0xeeffffee, true));
            status.setFont(status.getFont().deriveFont(Font.PLAIN, 23.0f));
            indexPane.add(status, 0);
            indexPane.setOpaque(true);


            this.setSize(width, height + 50);
            this.setResizable(false);
        }

        public void setStatus(String status) {
            this.status.setText(status);
        }

        public void updateHighlight() {
            for(Disk[] diskRow: gameBoard){
                for(Disk disk: diskRow){
                    disk.updateHighlight();
                }
            }
        }
    }

    /**
     * disk element in visual game board
     */
    private static class Disk extends JPanel {
        private Color diskColor = BLANK_COLOR;
        private final int row;
        private final int column;
        private boolean highlight;

        public Disk(int row, int column) {
            this.row = row;
            this.column = column;
            setHighlight(false);
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public void setDiskColor(int color) {
            switch (color) {
                case RED:
                    diskColor = RED_COLOR;
                    break;
                case YELLOW:
                    diskColor = YELLOW_COLOR;
                    break;
                default:
                    diskColor = BLANK_COLOR;
            }
            this.updateUI();
        }

        public void setHighlight(boolean highlight) {
            this.highlight = highlight;
            if (!highlight) {
                this.setBackground(this.column % 2 == 0 ? BACKGROUND_ODD : BACKGROUND_EVEN);
            }
            updateHighlight();
        }

        private void updateHighlight() {
            if(highlight){
                this.setBackground(backgroundHighlight);
            }
        }

        /**
         * Draw a circle at the center of this {@code JPanel} at its center
         */
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Rectangle bounds = getBounds();
            double radius = Math.min(bounds.getWidth(), bounds.getHeight()) * 0.4;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(diskColor);
            g2d.fill(new Ellipse2D.Double(bounds.getWidth() / 2 - radius,
                    bounds.getHeight() / 2 - radius,
                    2 * radius,
                    2 * radius));
        }
    }

    private final ConnectFourGUIFrame frame;
    private int currentColor;

    public ConnectFourGUI() {
        frame = new ConnectFourGUIFrame(this);
    }

    /**
     * A convenient method to {@link #dropDisk(int, int) drop a disk}
     * in {@link #currentColor}
     *
     * @param column the column index where to drop the disk
     * @return the number of disks in the column after dropping this disk, which is
     *         also the index of the last disk counted form bottom.
     * @see #dropDisk(int, int)
     */
    private int dropCurrentDisk(int column) {
        return dropDisk(currentColor, column);
    }

    /**
     * request for a new step after dropping a disk.
     * This program will check whether the game have finished
     */
    private void nextStep() {
        if (isFinish()) {
            // Someone won
            frame.prompt(String.format("The %s player won", getColorName(currentColor)));
            frame.setStatus("Game Finished");
            return;
        }
        if (isFill()) {
            // Game board filled, no one won
            frame.prompt("Oops! Game board filled. You both failed");
            frame.setStatus("Game Board Filled");
            return;
        }

        // Continue game
        currentColor = currentColor == RED ? YELLOW : RED;
        backgroundHighlight = currentColor == RED ? BACKGROUND_HIGHLIGHT_RED : BACKGROUND_HIGHLIGHT_YELLOW;
        frame.updateHighlight();
        frame.setStatus(String.format("Drop a %s disk.", getColorName(currentColor)));
    }

    /**
     * Show the window of the game
     */
    @Override
    public void start() {
        currentColor = RED;
        frame.prompt("Click a column to begin, red goes first");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * entry point for start an new game
     * @param args command line parameters
     */
    public static void main(String[] args) {
        ConnectFourGUI game = new ConnectFourGUI();
        game.start();
    }
}


