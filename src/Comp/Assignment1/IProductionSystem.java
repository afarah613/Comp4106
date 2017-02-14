package Comp.Assignment1;

import java.util.Collection;

/**
 * Created by Ali on 2017-02-11.
 */
public interface IProductionSystem {

    boolean isSolved();
    IProductionSystem applyMove(Move move, IProductionSystem prevState);
    Collection<Move> generateAllMoves();
    String getPathToSuccess(IProductionSystem productionSystem);


}
