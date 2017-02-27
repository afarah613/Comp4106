package Comp.Assignment2;

/**
 * Created by Ali on 2017-02-21.
 */
public class BoardStack {

    private Player[] stack;
    private int index;

    public BoardStack(Player player)
    {
        this.index = 0;
        this.stack = new Player[5];
        this.stack[this.index] = player;
    }

    public int size()
    {
        return this.stack.length;
    }

    public Player getFirst()
    {
        return this.stack[this.index];
    }

    public Player getLast()
    {
        return this.stack[0];
    }

    public void add(Player player)
    {
        this.index++;
        this.stack[index] = player;
    }

    public String toString()
    {
        return this.index + this.getFirst().toString();
    }

}
