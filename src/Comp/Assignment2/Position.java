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
}
