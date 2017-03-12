package Comp.Assignment2;


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


    public int getNumberOfPieces() {
        return numberOfPieces;
    }

    public static Move horizontalMove(Position currentPosition, int value, int direction)
    {
        int directionValue = direction * value;

        Position newPosition = new Position(currentPosition.getRow(),
                currentPosition.getColumn() + directionValue);

        return new Move(currentPosition, newPosition, value);
    }

    public static Move verticalMove(Position currentPosition, int value, int direction)
    {
        int directionValue = direction* value;

        Position newPosition = new Position(currentPosition.getRow() + directionValue,
                currentPosition.getColumn());

        return new Move(currentPosition, newPosition, value);
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Current Position: "+ playerPosition);
        builder.append(" Capture Position: "+ capturePosition + " Number of Pieces " + numberOfPieces);
        return builder.toString();
    }
}
