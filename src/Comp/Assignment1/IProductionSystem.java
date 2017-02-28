package Comp.Assignment1;

import java.util.Collection;

/**
 * Created by Ali on 2017-02-11.
 */
public interface IProductionSystem {

    boolean isSolved();
    int getStateValue();
    void setStateValue(int value);
    void setHeuristic(int heuristic);
    void setPreviousState(IProductionSystem previousState);
    IProductionSystem getPreviousState();
    Collection<IProductionSystem> generateAllChildStates(IProductionSystem previousState);
}
