package Comp.Assignment2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 2017-03-10.
 */
public class BoardPiece {

    private StringBuilder stack;
    public final int MAX_SIZE = 5;

    public BoardPiece(String piece)
    {
        this.stack = new StringBuilder(piece);
    }

    public BoardPiece(BoardPiece piece)
    {
        this.stack = new StringBuilder(piece.stack);
    }

    public char getFirst()
    {
        return stack.charAt(0);
    }

    public int length()
    {
        return  stack.length();
    }

    public boolean isEmpty()
    {
        return this.getFirst() == 'E';
    }

    public void capturePiece(BoardPiece piece, int numberOfPieces)
    {
        piece.deleteEmptyPieces();
        piece.prependPiece(this,numberOfPieces);
        this.resetStack(numberOfPieces);
    }

    public List<Move> generateAllMoves(Position currentPosition)
    {
        List<Move> moves = new ArrayList<>();

        for(int i = this.length(); i>0; i--)
        {
            moves.addAll(this.generateHorizontalMoves(currentPosition,i));
            moves.addAll(this.generateVerticalMoves(currentPosition,i));
        }

        return moves;
    }

    private List<Move> generateHorizontalMoves(Position currentPosition, int value)
    {
        List<Move> moves = new ArrayList<>();

        Position leftPosition = new Position(currentPosition.getRow(),
                currentPosition.getColumn() - value);
        Position rightPosition = new Position(currentPosition.getRow(),
                currentPosition.getColumn() + value);

        Move leftMove = new Move(currentPosition, leftPosition, value);
        Move rightMove = new Move(currentPosition, rightPosition, value);

        moves.add(leftMove);
        moves.add(rightMove);
        return moves;
    }

    private List<Move> generateVerticalMoves(Position currentPosition, int value)
    {
        List<Move> moves = new ArrayList<>();

        Position upPosition = new Position(currentPosition.getRow() - value,
                currentPosition.getColumn());
        Position downPosition = new Position(currentPosition.getRow() + value,
                currentPosition.getColumn());

        Move upMove = new Move(currentPosition, upPosition, value);
        Move downMove = new Move(currentPosition, downPosition, value);

        moves.add(upMove);
        moves.add(downMove);
        return moves;
    }

    private void prependPiece(BoardPiece piece, int numberOfPieces) {

        String playerString = piece.stack.substring(0, numberOfPieces);
        this.stack.insert(0, playerString);

        if(this.length() > MAX_SIZE)
            this.stack.delete(MAX_SIZE, this.length());

    }

    private void deleteEmptyPieces()
    {
        for(int i =0; i< this.length(); i++)
        {
            if(this.stack.charAt(i) == 'E')
                this.stack.deleteCharAt(i);
        }
    }

    private void resetStack(int numberOfPiecesTaken)
    {
        if(numberOfPiecesTaken == this.length())
        {
            this.stack = new StringBuilder("E");
        }
        else
        {
            String leftOver = this.stack.substring(numberOfPiecesTaken,this.length());
            this.stack = new StringBuilder(leftOver);
        }
    }

    public String toString()
    {
        int i = length();
        StringBuilder builder = new StringBuilder(this.stack);

        while (i <= MAX_SIZE)
        {
            builder.append(" ");
            i++;
        }
        
        return builder.toString();
    }
}
