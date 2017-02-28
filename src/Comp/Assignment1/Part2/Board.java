package Comp.Assignment1.Part2;

import Comp.Assignment1.IProductionSystem;
import Comp.Assignment1.Move;

import java.util.*;
import java.util.regex.Matcher;

public class Board implements IProductionSystem {

    private int[] tiles;
    private int rows;
    private int stateValue;
    private int level;
    private int columns;
    private int heuristic;
    private IProductionSystem previousState;


    public Board(int[] tiles, int rows, int columns, IProductionSystem prevState, int level, int heuristic)
    {
        this.rows = rows;
        this.columns = columns;
        this.tiles = new int[rows * columns];
        this.previousState = prevState;
        this.level = level;
        this.heuristic = heuristic;
        for(int i=0; i<tiles.length; i++)
        {
            this.tiles[i]=tiles[i];
        }
        this.stateValue = 1;
    }

    public boolean isSolved()
    {
        for(int i =0;i<this.tiles.length;i++)
        {
            if(this.tiles[i] != i)
                return false;
        }
        return true;
    }

    public void setHeuristic(int heuristic)
    {
        this.heuristic = heuristic;
    }

    public Collection<IProductionSystem> generateAllChildStates(IProductionSystem previousState)
    {
        HashSet<IProductionSystem> productionSystemHashSet = new HashSet<>();

        for(Move move: generateAllMoves())
        {
            productionSystemHashSet.add(applyMove(move, previousState));
        }

        return productionSystemHashSet;
    }

    private Collection<Move> generateAllMoves()
    {
        List<Move> moves = new ArrayList<>();
        moves.addAll(generateAllChessHorseMoves());
        moves.addAll(generateAllZeroMoves());

        return moves;
    }

    public int getStateValue()
    {
        return this.stateValue;
    }

    public void setStateValue(int value)
    {
        this.stateValue = value;
    }

    private Collection<BoardMove> generateAllChessHorseMoves() {

        ArrayList<BoardMove> moves = new ArrayList<>();
        List<Integer> positions = new ArrayList<>();

        for (int currentPosition = 0; currentPosition < this.tiles.length; currentPosition++) {

            if(this.tiles[currentPosition] != 0) {

                if (isValidLeftRightMove(currentPosition, Direction.Left, 1)) {
                    positions.add(currentPosition - 1 - (this.columns * 2)); // down 2, 1 left
                    positions.add(currentPosition - 1 + (this.columns * 2)); // up 2, 1 left
                }

                if (isValidLeftRightMove(currentPosition, Direction.Right, 1)) {
                    positions.add(currentPosition + 1 - (this.columns * 2)); // down 2, 1 right
                    positions.add(currentPosition + 1 + (this.columns * 2)); // up 2, 1 right
                }

                if (isValidLeftRightMove(currentPosition, Direction.Left, 2)) {
                    positions.add(currentPosition - 2 - this.columns); // left 2, 1 down
                    positions.add(currentPosition - 2 + this.columns); // left 2, 1 up
                }

                if (isValidLeftRightMove(currentPosition, Direction.Right, 2)) {
                    positions.add(currentPosition + 2 - this.columns); // right 2, 1 down
                    positions.add(currentPosition + 2 + this.columns); // right 2, 1 up
                }

                for (int position : positions) {
                    if (isValidPosition(position) && this.tiles[position] != 0)
                        moves.add(new BoardMove(position, currentPosition));
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

        if(isValidLeftRightMove(zeroPosition, Direction.Left,1))
        {
            positions.add(zeroPosition -1);
            positions.add(zeroPosition -1 + this.columns); // leftUpHorizontal
            positions.add(zeroPosition -1 - this.columns); // leftDownHorizontal
        }

        if(isValidLeftRightMove(zeroPosition, Direction.Right,1))
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

    private  boolean isValidLeftRightMove(int position, Direction direction, int move)
    {
        int value = position % this.columns;
        if(direction == Direction.Left)
            value = value - move;
        else
            value = value + move;
        return value < this.columns && value >=0 ;
    }

    private int rowsAndColumnsOutOfPlace()
    {
        int count =0;

        for(int i =0; i< this.tiles.length; i++)
        {
            if(this.tiles[i] != i && this.tiles[i] != 0)
            {
                int column =  i % this.columns;
                int goalColumns = this.tiles[i] % this.columns;
                int row =  i/this.columns;
                int goalRow = this.tiles[i]/this.columns;
               if(row!=goalRow)
                    count++;
                if(column != goalColumns)
                    count++;
            }
        }

        return count;
    }

    private int outOfPlace()
    {
        int value =0;

        for(int i =0; i< this.tiles.length; i++)
        {
            if(i!= this.tiles[i])
            {
                int column =  i % this.columns;
                int goalColumns = this.tiles[i] % this.columns;
                int row =  i/this.columns;
                int goalRow = this.tiles[i]/this.columns;
                int x = Math.abs(column - goalColumns);
                int y = Math.abs(goalRow - row);
                value+= Math.sqrt(Math.pow(x,1) + Math.pow(y,1)) ;
            }
        }

        return value;
    }

    private void setValue()
    {
        int heuristicValue = 0;

        if(this.heuristic == 1)
            heuristicValue = this.rowsAndColumnsOutOfPlace();
        else if(this.heuristic == 2)
            heuristicValue = outOfPlace();
        else if(this.heuristic == 3)
            heuristicValue = (this.rowsAndColumnsOutOfPlace() + this.outOfPlace())/2;

        this.stateValue = heuristicValue + this.level;
    }

    public IProductionSystem getPreviousState()
    {
        return this.previousState;
    }

    public void setPreviousState(IProductionSystem previousState)
    {
        this.previousState = previousState;
    }

    private Board applyMove(Move move, IProductionSystem prevState)
    {
        BoardMove boardMove = (BoardMove) move;
        int pos1 = boardMove.position1;
        int pos2 = boardMove.position2;

        Board board = new Board(this.tiles, this.rows, this.columns, prevState, this.level+1,this.heuristic);
        int temp = board.tiles[pos2];
        board.tiles[pos2]= board.tiles[pos1];
        board.tiles[pos1]= temp;
        board.setValue();

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

