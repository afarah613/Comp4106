package Comp.Assignment2;

import java.util.ArrayList;
import java.util.List;

public class FocusBoard {

    private BoardPiece[][] board;
    private int redCaptured;
    private int greedCaptured;
    private char turn;

    private final int COLUMNS = 8;
    private final int ROWS = 8;
    private final int MAXSIZE = 5;

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

        this.turn = 'G';
        this.board = new BoardPiece[ROWS][COLUMNS];
        this.greedCaptured = 0;
        this.redCaptured = 0;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (isValidPosition(i, j)) {
                    if (i > 0 && i < 7 && j > 0 && j < 7) {
                        if(j%2 ==0) {
                            this.board[i][j] = new BoardPiece("G");
                        }
                        else {
                            this.board[i][j] = new BoardPiece("R");;
                        }
                    } else {
                        this.board[i][j] = new BoardPiece("E");
                    }
                }
            }
        }
    }

    public FocusBoard(char turn)
    {
        this();
        this.turn = turn;
    }

    private boolean canPlayMove(Move move)
    {
        Position currentPlayerPosition = move.getPlayerPosition();
        Position opponentPosition = move.getCapturePosition();

        if(!isValidPosition(currentPlayerPosition) || !isValidPosition(opponentPosition))
            return false;

        BoardPiece currentPlayerPiece = getElement(currentPlayerPosition);

        if(!currentPlayerPiece.isEmpty() && currentPlayerPiece.getFirst() == turn
                && currentPlayerPiece.length() >= move.getNumberOfPieces() && move.isInTheSameRowOrColumn())
        {
            int distance = currentPlayerPosition.calculateDistanceTo(opponentPosition);
            return distance <= move.getNumberOfPieces();
        }

        return false;
    }

    public List<Move> generateAllMoves()
    {
        List<Move> moves =new ArrayList<>();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {

                Position position = new Position(i,j);
                BoardPiece piece = this.getElement(position);

                if(piece != null && piece.getFirst() == turn)
                {
                    for (Move move: piece.generateAllMoves(position))
                    {
                        if(this.canPlayMove(move))
                            moves.add(move);
                    }
                }
            }
        }


        return moves;
    }

    public boolean applyMove(Move move)
    {
        if(!canPlayMove(move))
            return false;

        Position currentPlayerPosition = move.getPlayerPosition();
        Position capturePosition = move.getCapturePosition();
        BoardPiece currentPlayerPiece = getElement(currentPlayerPosition);
        BoardPiece capturePlayerPiece = getElement(capturePosition);
        this.addToCapturePile(move.getNumberOfPieces() + capturePlayerPiece.length());
        currentPlayerPiece.capturePiece(capturePlayerPiece, move.getNumberOfPieces());
        this.switchTurn();
        return true;
    }

    public boolean isGameOver()
    {
        // TODO check if game is over
        return false;
    }

    private  boolean isValidPosition(Position position)
    {
        return isValidPosition(position.getRow(), position.getColumn());
    }

    private boolean isValidPosition(int row, int column) {

        if( row < 0 || row >= ROWS || column < 0 || column >= COLUMNS)
            return false;

        for (int[] array : this.blockedPosition) {
            if (array[0] == row && array[1] == column)
                return false;
        }
        return true;
    }

    private BoardPiece getElement(Position position)
    {
        return this.board[position.getRow()][position.getColumn()];
    }

    private void switchTurn()
    {
        if(this.turn == 'G')
            this.turn = 'R';
        else
            this.turn = 'G';
    }

    private void addToCapturePile(int total)
    {
        if(total > MAXSIZE)
        {
            if(this.turn == 'G')
                this.greedCaptured += total - MAXSIZE;
            else
                this.redCaptured += total - MAXSIZE;
        }
    }

    public String toString() {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (isValidPosition(i, j)) {

                    builder.append(this.board[i][j].toString());
                } else {
                    builder.append("      ");
                }
            }

            builder.append("\n");
        }
        builder.append("\nRed Capture: " + this.redCaptured + " Green Capture: " + this.greedCaptured);
        builder.append("\nTurn: " + this.turn);
        return builder.toString();
    }
}


