package Comp.Assignment1.Part2;

import Comp.Assignment1.IProductionSystem;
import Comp.Assignment1.Move;

import java.util.*;

public class Board implements IProductionSystem {


    private int[] tiles;
    private int rows;
    private  int columns;
    private IProductionSystem prevState;

    private static final int[] solution3by3 = new int[]
            {
                    1,2,3,4,0,5,6,7,8
            };

    private static final int[] solution2by4 = new int[]
            {
                    1,2,3,4,5,6,7,0
            };

    private static final int[] solution2by5 = new int[]
            {
                    1,2,3,4,5,6,7,8,9,0
            };

    public Board(int[] tiles, int rows, int columns, IProductionSystem prevState)
    {
        this.rows = rows;
        this.columns = columns;
        this.tiles = new int[rows * columns];
        this.prevState = prevState;
        for(int i=0; i<tiles.length; i++)
        {
            this.tiles[i]=tiles[i];
        }
    }

    public boolean isSolved()
    {
        if(this.rows == 3 && this.columns == 3)
            return Arrays.equals(this.tiles, solution3by3);
        else if(this.rows == 2 && this.columns == 4)
            return Arrays.equals(this.tiles, solution2by4);
        else if(this.rows == 2 && this.columns == 5)
            return Arrays.equals(this.tiles, solution2by5);
        return false;
    }

    public Collection<Move> generateAllMoves()
    {
        List<Move> moves = new ArrayList<>();
        moves.addAll(generateAllChessHorseMoves());
        moves.addAll(generateAllZeroMoves());

        return moves;
    }

    private Collection<BoardMove> generateAllChessHorseMoves() {

        ArrayList<BoardMove> moves = new ArrayList<>();
        List<Integer> positions = new ArrayList<>();

        for (int i = 0; i < this.tiles.length; i++) {

            if(this.tiles[i] != 0) {

                int currentPosistion = i;
                if (isValidColumn(currentPosistion, Direction.Left, 1)) {
                    positions.add(currentPosistion - 1 - (this.columns * 2)); // down 2, 1 left
                    positions.add(currentPosistion - 1 + (this.columns * 2)); // up 2, 1 left
                }

                if (isValidColumn(currentPosistion, Direction.Right, 1)) {
                    positions.add(currentPosistion + 1 - (this.columns * 2)); // down 2, 1 right
                    positions.add(currentPosistion + 1 + (this.columns * 2)); // up 2, 1 right
                }

                if (isValidColumn(currentPosistion, Direction.Left, 2)) {
                    positions.add(currentPosistion - 2 - this.columns); // left 2, 1 down
                    positions.add(currentPosistion - 2 + this.columns); // left 2, 1 up
                }

                if (isValidColumn(currentPosistion, Direction.Right, 2)) {
                    positions.add(currentPosistion + 2 - this.columns); // right 2, 1 down
                    positions.add(currentPosistion + 2 + this.columns); // right 2, 1 up
                }

                for (int position : positions) {
                    if (isValidPosition(position) && this.tiles[position] != 0)
                        moves.add(new BoardMove(position, currentPosistion));
                }

                positions.clear();
            }
        }

        return moves;
    }

    private Collection<BoardMove> generateAllZeroMoves()
    {
        List<BoardMove> moves = new ArrayList<>();
        int zeroPosition = getZeroPosition();
        List<Integer> positions = new ArrayList<>();

        if(isValidColumn(zeroPosition, Direction.Left,1))
        {
            positions.add(zeroPosition -1);
            positions.add(zeroPosition -1 + this.columns); // leftUpHorizontal
            positions.add(zeroPosition -1 - this.columns); // leftDownHorizontal
        }

        if(isValidColumn(zeroPosition, Direction.Right,1))
        {
            positions.add(zeroPosition +1);
            positions.add(zeroPosition +1 + this.columns); // rightUpHorizontal
            positions.add(zeroPosition +1 - this.columns); // rightDownHorizontal
        }

        positions.add(zeroPosition + this.columns); // up
        positions.add(zeroPosition - this.columns); // down

        for (int position: positions) {
            if(isValidPosition(position))
                moves.add(new BoardMove(position, zeroPosition));
        }

        return moves;
    }

    private  boolean isValidPosition(int position)
    {
        return position < this.tiles.length && position >=0 ;
    }

    private  boolean isValidColumn(int position,Direction direction, int move)
    {
        int value = position % this.columns;
        if(direction == Direction.Left)
            value = value - move;
        else
            value = value + move;
        return value < this.columns && value >=0 ;
    }
    private Board reverseMoves(Board board)
    {
        Board prevBoard = null;
        Board currentBoard = board;

        while(currentBoard != null)
        {
            Board temp = (Board) currentBoard.prevState;
            currentBoard.prevState = prevBoard;
            prevBoard = currentBoard;
            currentBoard = temp;
        }

        return prevBoard;
    }

    public String getPathToSuccess(IProductionSystem productionSystem)
    {
        StringBuilder builder = new StringBuilder();
        Board board = (Board) productionSystem;
        board = reverseMoves(board);
        int i =0;
        while(board != null)
        {
            i++;
            builder.append(board.toString());
            board = (Board) board.prevState;
        }

        return builder.toString();
    }

    public Board applyMove(Move move, IProductionSystem prevState)
    {
        BoardMove boardMove = (BoardMove) move;
        int pos1 = boardMove.position1;
        int pos2 = boardMove.position2;

        Board board = new Board(this.tiles, this.rows, this.columns, prevState);
        int temp = board.tiles[pos2];
        board.tiles[pos2]= board.tiles[pos1];
        board.tiles[pos1]= temp;

        return board;
    }

    private int getZeroPosition()
    {
        for(int i =0; i<this.tiles.length; i++) {
            int value = this.tiles[i];
            if (value == 0) {
                return i;
            }
        }
        return -1;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        for(int i =0; i<tiles.length; i++) {

            builder.append(this.tiles[i] + " ");
            if((i +1)%this.columns == 0 && i!= 0)
                builder.append("\n");
        }

        builder.append("\n");
        return builder.toString();
    }

    public int hashCode()
    {
        long result = 0;
        for(int i = 0; i < this.tiles.length; i++)
        {
            result += Math.pow(10,i) * this.tiles[this.tiles.length - i - 1];
        }

        return Long.valueOf(result).hashCode();
    }

    public boolean equals(Object o)
    {
        if(!(o instanceof Board))
        {
            return false;
        }

        Board board = (Board)o;

        return board.rows == this.rows
                && board.columns == this.columns
                && Arrays.equals(this.tiles, board.tiles);
    }

    private enum Direction
    {
        Left,
        Right
    }
}

