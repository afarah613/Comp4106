package Comp.Assignment2;

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
