package Comp.Assignment1;

import java.util.*;

/**
 * Created by Ali on 2017-01-19.
 */
public class ProductionSystemSolver {

    public static IProductionSystem BreadthFirstSearch(IProductionSystem productionSystem)
    {
        if(productionSystem.isSolved())
        {
            return null;
        }

        Queue<IProductionSystem> productionSystemQueue = new ArrayDeque<>();
        HashSet<Integer> visitedStates = new HashSet<>();

        productionSystemQueue.add(productionSystem);
        visitedStates.add(productionSystem.hashCode());

        while(!productionSystemQueue.isEmpty())
        {
            IProductionSystem currentState = productionSystemQueue.poll();

            for (Move move : currentState.generateAllMoves())
            {
                IProductionSystem newState = currentState.applyMove(move, currentState);

                if(newState.isSolved())
                {
                   return newState;
                }

                if(!visitedStates.contains(newState.hashCode())) {
                    productionSystemQueue.add(newState);
                    visitedStates.add(newState.hashCode());
                }
            }
        }

        return null;
    }

    public static IProductionSystem DepthFirstSearch(IProductionSystem productionSystem)
    {
        if(productionSystem.isSolved())
        {
            return productionSystem;
        }

        Stack<IProductionSystem> productionSystemStack = new Stack<>();
        HashSet<Integer> visitedStates = new HashSet<>();

        productionSystemStack.push(productionSystem);
        visitedStates.add(productionSystem.hashCode());

        while(!productionSystemStack.isEmpty())
        {
            IProductionSystem currentState = productionSystemStack.pop();

            for (Move move :
                    currentState.generateAllMoves())
            {
                IProductionSystem newState = currentState.applyMove(move, currentState);

                if(newState.isSolved())
                {
                    return newState;
                }

                if(!visitedStates.contains(newState.hashCode())) {
                    visitedStates.add(newState.hashCode());
                    productionSystemStack.push(newState);
                }
            }
        }

        return null;
    }
}
