package Comp.Assignment2;

/**
 * Created by Ali on 2017-03-05.
 */
public class Move {

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public Position getOpponentPosition() {
        return opponentPosition;
    }

    private Position playerPosition;
    private Position opponentPosition;

    public Move (Position playerPosition, Position computerPosition)
    {
        this.playerPosition = playerPosition;
        this.opponentPosition = computerPosition;
    }
}
