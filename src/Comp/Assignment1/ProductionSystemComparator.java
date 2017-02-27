package Comp.Assignment1;


import java.util.Comparator;

public class ProductionSystemComparator implements Comparator<IProductionSystem>
{
    public int compare(IProductionSystem board, IProductionSystem board2)
    {
        if(board.getStateValue() > board2.getStateValue())
            return 1;
        else if(board.getStateValue() < board2.getStateValue())
            return -1;
        else
            return 0;
    }

}