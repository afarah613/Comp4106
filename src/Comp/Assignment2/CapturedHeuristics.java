package Comp.Assignment2;

/**
 * Created by Ali on 2017-03-11.
 */
public class CapturedHeuristics implements IHeuristic {

    @Override
    public int getValue(FocusBoard focusBoard, char color) {

        BoardPiece[][] board = focusBoard.getBoard();
        int sum = 0;

        for (int i = 0; i < FocusBoard.ROWS; i++) {
            for (int j = 0; j < FocusBoard.COLUMNS; j++) {
                if(board[i][j]!= null && board[i][j].getFirst() == color)
                    sum+= board[i][j].length();
            }
        }

     return sum;
    }
}
