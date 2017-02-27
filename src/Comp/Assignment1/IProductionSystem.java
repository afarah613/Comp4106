package Comp.Assignment1;

import java.util.Collection;

/**
 * Created by Ali on 2017-02-11.
 */
public interface IProductionSystem {

    boolean isSolved();
    double getStateValue();
    void setHeuristic(int heuristic);
    void setPreviousState(IProductionSystem previousState);
    IProductionSystem getPreviousState();
    Collection<IProductionSystem> generateAllChildStates(IProductionSystem previousState);
}
