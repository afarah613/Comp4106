package Comp.Assignment2;

/**
 * Created by Ali on 2017-02-21.
 */
public class FocusBoard {

    private BoardStack[][] boardStacks;
    private int redReserve;
    private int greenReserve;
    private final int ROWS = 8;
    private final int COLUMNS = 8;

    private final int[][] blockedPosition = {
            {0, 0},
            {0, 1},
            {1, 0},
            {6, 0},
            {7, 0},
            {0, 6},
            {0, 7},
            {1, 7},
            {6, 7},
            {7, 7}
    };

    public FocusBoard() {
        this.boardStacks = new BoardStack[ROWS][COLUMNS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (isValidPosition(i, j)) {
                    if (i > 0 && i < 7 && j > 0 && j < 7) {
                        if(i%2 ==0)
                            this.boardStacks[i][j] = new BoardStack(Player.Red);
                        else
                            this.boardStacks[i][j] = new BoardStack(Player.Green);
                    } else
                        this.boardStacks[i][j] = new BoardStack(Player.None);
                }
            }
        }
    }

    private boolean isValidPosition(int row, int column) {

        for (int[] array : this.blockedPosition) {
            if (array[0] == row && array[1] == column)
                return false;
        }
        return true;
    }

    public String toString() {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (isValidPosition(i, j)) {
                    builder.append(this.boardStacks[i][j].toString() + " ");
                }
                else
                {
                    builder.append("   ");
                }
            }

            builder.append("\n");
        }

        return builder.toString();
    }

    public static void main(String[] args)
    {
        FocusBoard board = new FocusBoard();
        System.out.print(board);
    }
}


