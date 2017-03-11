package Comp.Assignment2;

/**
 * Created by Ali on 2017-03-05.
 */
public class Move {

    private Position playerPosition;
    private Position capturePosition;
    private int numberOfPieces;

    public Move (Position playerPosition, Position capturePosition)
    {
        this.playerPosition = playerPosition;
        this.capturePosition = capturePosition;
    }

    public Move(Position playerPosition, Position capturePosition, int numberOfPieces)
    {
        this(playerPosition, capturePosition);
        this.numberOfPieces = numberOfPieces;
    }

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public Position getCapturePosition() {
        return capturePosition;
    }

    public boolean isInTheSameRowOrColumn()
    {
        return this.playerPosition.getRow() == this.capturePosition.getRow()
                || this.playerPosition.getColumn() == this.capturePosition.getColumn();
    }

    public int getNumberOfPieces() {
        return numberOfPieces;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Current Position: "+ playerPosition);
        builder.append(" Capture Position: "+ capturePosition + " Number of Pieces " + numberOfPieces);
        return builder.toString();
    }
}
