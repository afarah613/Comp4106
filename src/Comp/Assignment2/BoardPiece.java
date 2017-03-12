package Comp.Assignment2;

import java.util.ArrayList;
import java.util.List;

public class BoardPiece {

    private StringBuilder stack;

    public static final int MAX_SIZE = 5;
    public static final char GREEN = 'G';
    public static final char RED = 'R';

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

        Move leftMove = Move.horizontalMove(currentPosition,value,-1);
        Move rightMove = Move.horizontalMove(currentPosition,value, 1);
        moves.add(leftMove);
        moves.add(rightMove);

        return moves;
    }

    private List<Move> generateVerticalMoves(Position currentPosition, int value)
    {
        List<Move> moves = new ArrayList<>();

        Move upMove = Move.verticalMove(currentPosition,value, -1);
        Move downMove = Move.verticalMove(currentPosition,value, 1);
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
