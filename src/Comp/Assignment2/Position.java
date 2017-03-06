package Comp.Assignment2;

/**
 * Created by Ali on 2017-02-21.
 */
public class Position {

    private int row;


    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    private int column;

    public Position(int row, int column)
    {
        this.row = row;
        this.column = column;
    }

    public int calculateDistanceTo(Position position)
    {
        int x = Math.abs(position.row - this.row);
        int y = Math.abs(position.column- this.column);
        int sumOfPowers = (int) (Math.pow(x,2) + Math.pow(y, 2));
        return (int) Math.sqrt(sumOfPowers);
    }
}
