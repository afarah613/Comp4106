package Comp.Assignment2;

import java.util.ArrayList;
import java.util.List;

public class FocusBoard {

    private List<Color>[][] boardStacks;
    private int redCaptured;
    private int greedCaptured;
    private Color turn;

    private final int COLUMNS = 8;
    private final int ROWS = 8;
    private final int MAXLISTSIZE = 5;

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

        this.turn = Color.Green;
        this.boardStacks = new List[ROWS][COLUMNS];
        this.greedCaptured = 0;
        this.redCaptured = 0;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (isValidPosition(i, j)) {
                    if (i > 0 && i < 7 && j > 0 && j < 7) {
                        this.boardStacks[i][j] = new ArrayList<>();
                        if(j%2 ==0) {
                            this.boardStacks[i][j].add(Color.Green);
                        }
                        else {
                            this.boardStacks[i][j].add(Color.Red);
                        }
                    } else {
                        this.boardStacks[i][j] = new ArrayList<>();
                    }
                }
            }
        }
    }

    public FocusBoard(Color turn)
    {
        this();
        this.turn = turn;
    }

    private boolean canPlayMove(Move move)
    {
        Position currentPlayerPosition = move.getPlayerPosition();
        Position opponentPosition = move.getOpponentPosition();

        if(!isValidPosition(currentPlayerPosition) || !isValidPosition(opponentPosition))
            return false;

        List<Color> currentColorList = getElement(currentPlayerPosition);
        List<Color> opponentList = getElement(opponentPosition);

        if(!currentColorList.isEmpty() && currentColorList.get(0) == turn
                && (currentColorList.isEmpty() || opponentList.get(0) != turn))
        {
            int distance = currentPlayerPosition.calculateDistanceTo(opponentPosition);
            return distance <= currentColorList.size();

        }

        return false;

    }

    public boolean applyMove(Move move)
    {
        if(!canPlayMove(move))
            return false;

        Position currentPlayerPosition = move.getPlayerPosition();
        Position opponentPosition = move.getOpponentPosition();
        List<Color> currentColorStack = getElement(currentPlayerPosition);
        List<Color> opponentStack = getElement(opponentPosition);

        opponentStack.addAll(0, currentColorStack);
        currentColorStack.clear();

        if(opponentStack.size() > MAXLISTSIZE)
        {
            this.addToCapture(opponentStack.size() - MAXLISTSIZE);
            opponentStack.removeAll(
                    opponentStack.subList(MAXLISTSIZE, opponentStack.size()));
        }

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

    private List<Color> getElement(Position position)
    {
        return this.boardStacks[position.getRow()][position.getColumn()];
    }

    private void switchTurn()
    {
        if(this.turn == Color.Green)
            this.turn = Color.Red;
        else
            this.turn = Color.Green;
    }

    private void addToCapture(int total)
    {
        if(this.turn == Color.Green)
            this.greedCaptured += total;
        else
            this.redCaptured += total;
    }

    public String toString() {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (isValidPosition(i, j)) {
                    String playerString;

                    if(!this.boardStacks[i][j].isEmpty())
                        playerString = this.boardStacks[i][j].get(0).toString();
                    else
                        playerString = "N";

                    int count = this.boardStacks[i][j].size();
                    builder.append(count + playerString + " ");
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
}


