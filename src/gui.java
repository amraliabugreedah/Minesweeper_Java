import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * Created by amr on 8/29/17.
 */
public class gui extends JFrame implements MouseListener {

    long tStart;
    long tEnd;
    long tDelta;
    double elapsedSeconds;

    JPanel panel0;
    JLabel labelLength;
    JLabel labelWidth;
    JLabel labelBombsNo;
    JTextField lengthTextField;
    JTextField widthTextField;
    JTextField bombsNoTextField;
    JButton startBtn;

    private engine eng;
    int length;
    int width;
    int bombsNo;

    JPanel panel1;
    JButton[][] sqrBtns;

    int neigh;

    boolean firstClick = true;

    JLabel end;


    public gui() {
        super();

        this.setLayout(null);
        this.setVisible(true);
        this.setSize(800, 1000);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setTitle("Bomb it!");

        panel0 = new JPanel();
        panel0.setLayout(null);
        panel0.setBounds(0, 0, 800, 400);
        this.getContentPane().add(panel0);
        panel0.setVisible(true);

        labelLength = new JLabel();
        labelLength.setBounds(20, 50, 100, 100);
        labelLength.setForeground(Color.gray);
        labelLength.setText("Length");
        panel0.add(labelLength);

        lengthTextField = new JTextField();
        lengthTextField.setBounds(130, 80, 100, 50);
        lengthTextField.setVisible(true);
        panel0.add(lengthTextField);

        labelWidth = new JLabel();
        labelWidth.setBounds(20, 130, 100, 100);
        labelWidth.setForeground(Color.gray);
        labelWidth.setText("Width");
        panel0.add(labelWidth);

        widthTextField = new JTextField();
        widthTextField.setBounds(130, 150, 100, 50);
        widthTextField.setVisible(true);
        panel0.add(widthTextField);

        labelBombsNo = new JLabel();
        labelBombsNo.setBounds(20, 200, 100, 100);
        labelBombsNo.setForeground(Color.gray);
        labelBombsNo.setText("Bombs no.");
        panel0.add(labelBombsNo);

        bombsNoTextField = new JTextField();
        bombsNoTextField.setBounds(130, 230, 100, 50);
        bombsNoTextField.setVisible(true);
        panel0.add(bombsNoTextField);

        startBtn = new JButton("start");
        startBtn.setBounds(300, 250, 100, 100);
        startBtn.addMouseListener(this);
//        button1.setContentAreaFilled(true);
        startBtn.setBorderPainted(true);
        panel0.add(startBtn);


        panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(0, 0, 800, 400);
        this.getContentPane().add(panel1);
        panel1.setVisible(false);

        end = new JLabel();
        end.setBounds(200, 500, 800, 100);
        end.setForeground(Color.gray);
        end.setVisible(false);
        this.add(end);


        this.validate();
        this.repaint();
    }

    public static void main(String[] args) {

        final gui gui = new gui();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == startBtn) {

            length = Integer.parseInt(lengthTextField.getText());
            width = Integer.parseInt(widthTextField.getText());
            bombsNo = Integer.parseInt(bombsNoTextField.getText());

            eng = new engine(length, width, bombsNo);

            panel0.setVisible(false);
            panel1.setVisible(true);
            panel1.setLayout(new GridLayout(length, width));
            sqrBtns = new JButton[length][width];
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < width; j++) {
                    sqrBtns[i][j] = new JButton("");
                    sqrBtns[i][j].setBounds(0, 0, 30, 30);
                    sqrBtns[i][j].addMouseListener(this);
//        button1.setContentAreaFilled(true);
                    sqrBtns[i][j].setBorderPainted(true);
                    panel1.add(sqrBtns[i][j]);
                }
            }

        } else {
            if (firstClick == true) {
                tStart = System.currentTimeMillis();
                firstClick = false;
            }

            for (int i = 0; i < length; i++) {
                for (int j = 0; j < width; j++) {

                    if (e.getSource() == sqrBtns[i][j]) {

                        boolean isThereBomb = eng.board[i][j];

                        if (isThereBomb == true) {
                            sqrBtns[i][j].setText("Boom!");
                            count(i, j);
                        } else {

                            count(i, j);

                            if (neigh == 0) {
                                for (int x = 0; x < 3; x++) {
                                    for (int y = 0; y < 3; y++) {
                                        if (i - 1 + x >= 0 && j - 1 + y >= 0 && i - 1 + x < length && j - 1 + y < width) {
                                            count(i - 1 + x, j - 1 + y);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            checkWin();
        }
    }

    public void count(int i, int j) {

        boolean isThereBomb = eng.board[i][j];

        if (isThereBomb == true) {

            for (int a = 0; a < length; a++) {
                for (int b = 0; b < width; b++) {
                    isThereBomb = eng.board[a][b];
                    if (isThereBomb == true) {

                        sqrBtns[a][b].setText("Boom!");

                    }
                    sqrBtns[a][b].setEnabled(false);
                    sqrBtns[a][b].removeMouseListener(this);
                }
            }
            tEnd = System.currentTimeMillis();
            tDelta = tEnd - tStart;
            elapsedSeconds = tDelta / 1000.0;
            end.setVisible(true);
            end.setText("Sorry, it took you: " + String.valueOf(elapsedSeconds) + "secs to lose!");

            return;

        }
        neigh = 0;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (i - 1 + x >= 0 && j - 1 + y >= 0 && i - 1 + x < length && j - 1 + y < width) {
                    int ii = i - 1 + x;
                    int jj = j - 1 + y;
                    System.out.println(eng.board[i - 1 + x][j - 1 + y]);
                    System.out.println(ii + " " + jj);
                    if (eng.board[i - 1 + x][j - 1 + y] == true) {
                        neigh++;
                    }

                }
            }
        }

        sqrBtns[i][j].setText(String.valueOf(neigh));
    }

    public void checkWin() {
        int safeSqrs = length * width - bombsNo;
        for (int a = 0; a < length; a++) {
            for (int b = 0; b < width; b++) {
                if (sqrBtns[a][b].getText() != "" && sqrBtns[a][b].getText() != "Boom!") {
                    safeSqrs--;
                }

            }
        }
        if (safeSqrs == 0) {
            for (int a = 0; a < length; a++) {
                for (int b = 0; b < width; b++) {
                    sqrBtns[a][b].setEnabled(false);
                    sqrBtns[a][b].removeMouseListener(this);
                }
            }
            tEnd = System.currentTimeMillis();
            tDelta = tEnd - tStart;
            elapsedSeconds = tDelta / 1000.0;
            end.setVisible(true);
            end.setText("Gratz!, it took you: " + String.valueOf(elapsedSeconds) + "secs to win!");
        return;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

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
}
