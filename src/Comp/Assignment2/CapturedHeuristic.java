package Comp.Assignment2;

public class CapturedHeuristic implements IHeuristic {
    @Override
    public int getValue(FocusBoard focusBoard, IPlayer player) {

        BoardPiece[][] board = focusBoard.getBoard();
        int capturedPieces = 0;
        int currentPlayerPieces = 0;

        for (int i = 0; i < FocusBoard.ROWS; i++) {
            for (int j = 0; j < FocusBoard.COLUMNS; j++) {
                if (board[i][j] != null && board[i][j].getFirst() == player.getColor())
                    currentPlayerPieces += board[i][j].length();
            }
        }

        if (player.getColor() == BoardPiece.GREEN)
            capturedPieces = focusBoard.getGreenCaptured();
        else
            capturedPieces = focusBoard.getRedCaptured();

        return currentPlayerPieces + (capturedPieces * 5);
    }
}
