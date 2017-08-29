/**
 * Created by amr on 8/29/17.
 */
public class engine {

    public boolean [][] board;

    public engine(int length, int width, int bombs){

       board = new boolean[length][width];
        System.out.println(length);
        System.out.println(width);
        System.out.println(bombs);
        for (int i = 0; i < bombs; i++) {

            int x = (int) (Math.random() * ((length)));
            int y = (int) (Math.random() * ((width)));


            if (board[x][y] == true) {
                i--;
                continue;
            } else {
                board[x][y] = true;
                System.out.println("X = " + x);
                System.out.println("y = " + y);
                System.out.println("i = " + i);
            }
        }
    }
}
