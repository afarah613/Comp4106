package Comp.Assignment2;

import java.util.ArrayList;
import java.util.List;

public class FocusBoard {

    private BoardPiece[][] board;
    private int redCaptured;
    private int greenCaptured;
    private char currentTurn;

    public static final int COLUMNS = 8;
    public static final int ROWS = 8;
    private final int MAX_CAPTURED = 3;

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

        this.currentTurn = BoardPiece.GREEN;
        this.board = new BoardPiece[ROWS][COLUMNS];
        this.greenCaptured = 0;
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

    public FocusBoard(FocusBoard board)
    {
        this.currentTurn = board.currentTurn;
        this.board = new BoardPiece[ROWS][COLUMNS];
        this.greenCaptured = board.greenCaptured;
        this.redCaptured = board.redCaptured;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {

                Position position = new Position(i,j);
                BoardPiece piece = board.getElement(position);

                if(piece!=null)
                {
                    this.board[i][j] = new BoardPiece(piece);
                }
            }
        }
    }

    public int getRedCaptured() {
        return redCaptured;
    }

    public BoardPiece[][] getBoard()
    {
        return this.board;
    }

    public int getGreenCaptured() {
        return greenCaptured;

    }

    public char getCurrentTurn()
    {
        return this.currentTurn;
    }

    public boolean canPlayMove(Move move)
    {
        Position currentPlayerPosition = move.getPlayerPosition();
        Position capturePosition = move.getCapturePosition();

        if(!isValidPosition(currentPlayerPosition)
                || !isValidPosition(capturePosition)
                || !currentPlayerPosition.isInSameRowOrColumn(capturePosition))
            return false;

        BoardPiece currentPlayerPiece = getElement(currentPlayerPosition);

        if(!currentPlayerPiece.isEmpty()
                && currentPlayerPiece.getFirst() == currentTurn
                && currentPlayerPiece.length() >= move.getNumberOfPieces())
        {
            int distance = currentPlayerPosition.calculateDistanceTo(capturePosition);
            return distance == move.getNumberOfPieces();
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

                if(piece != null && piece.getFirst() == currentTurn)
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

    public void applyMove(Move move)
    {
        Position currentPlayerPosition = move.getPlayerPosition();
        Position capturePosition = move.getCapturePosition();

        BoardPiece currentPlayerPiece = getElement(currentPlayerPosition);
        BoardPiece capturePlayerPiece = getElement(capturePosition);

        this.addToCapturePile(move.getNumberOfPieces() + capturePlayerPiece.length());
        currentPlayerPiece.capturePiece(capturePlayerPiece, move.getNumberOfPieces());

        this.switchTurn();
    }

    public boolean isGameOver()
    {
        if(this.greenCaptured == MAX_CAPTURED || this.redCaptured == MAX_CAPTURED)
            return true;
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
        if(this.currentTurn == BoardPiece.GREEN)
            this.currentTurn = BoardPiece.RED;
        else
            this.currentTurn = BoardPiece.GREEN;
    }

    private void addToCapturePile(int total)
    {
        if(total > BoardPiece.MAX_SIZE)
        {
            if(this.currentTurn == BoardPiece.GREEN)
                this.greenCaptured += total - BoardPiece.MAX_SIZE;
            else
                this.redCaptured += total - BoardPiece.MAX_SIZE;
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

        builder.append("\nRED Capture: " + this.redCaptured + " GREEN Capture: " + this.greenCaptured);
        builder.append("\nTurn: " + this.currentTurn);
        return builder.toString();
    }
}


