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
            return productionSystem;
        }

        Queue<IProductionSystem> productionSystemQueue = new ArrayDeque<>();
        HashSet<Integer> visitedStates = new HashSet<>();

        productionSystemQueue.add(productionSystem);
        visitedStates.add(productionSystem.hashCode());

        while(!productionSystemQueue.isEmpty())
        {
            IProductionSystem currentState = productionSystemQueue.poll();

            for (IProductionSystem newState  : currentState.generateAllChildStates(currentState))
            {
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

            for (IProductionSystem newState : currentState.generateAllChildStates(currentState))
            {
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

     public static IProductionSystem AStarSearch(IProductionSystem productionSystem) {

         if (productionSystem.isSolved()) {
             return productionSystem;
         }

         Comparator<IProductionSystem> comparator = new ProductionSystemComparator();
         Queue<IProductionSystem> open = new PriorityQueue<>(10, comparator);
         HashSet<IProductionSystem> closed = new HashSet<>();
            int i =0;
         open.add(productionSystem);

         while (!open.isEmpty()) {

             IProductionSystem currentState = open.poll();
             closed.add(currentState);

             for (IProductionSystem newState : currentState.generateAllChildStates(currentState))
             {
                 if (newState.isSolved()) {
                     return newState;
                 }

                 boolean inOpen = open.contains(newState);
                 boolean inClosed = closed.contains(newState);

                 if (!inClosed && !inOpen) {
                     open.add(newState);
                 }

                 else
                 {
                     IProductionSystem oldState = inOpen ? getObject(open, newState): getObject(closed, newState);

                     if(oldState.getStateValue() > newState.getStateValue()) {
                         boolean remove = inOpen ? open.remove(oldState) : closed.remove(oldState);
                         open.add(newState);
                         i++;
                     }
                 }
             }
         }

         return null;
     }

    private static IProductionSystem reverseStates(IProductionSystem productionSystem)
    {
        IProductionSystem previousSystem = null;
        IProductionSystem currentSystem = productionSystem;

        while(currentSystem != null)
        {
            IProductionSystem temp =  currentSystem.getPreviousState();
            currentSystem.setPreviousState( previousSystem);
            previousSystem = currentSystem;
            currentSystem = temp;
        }

        return previousSystem;
    }


    public static String getPathToSuccess(IProductionSystem productionSystem)
    {
        StringBuilder builder = new StringBuilder();
        productionSystem = reverseStates(productionSystem);

        while(productionSystem != null)
        {
            builder.append(productionSystem.toString());
            productionSystem =  productionSystem.getPreviousState();
        }

        return builder.toString();
    }

     private static IProductionSystem getObject(
             Collection<IProductionSystem> productionSystems,
             IProductionSystem productionSystem)
     {
         for (IProductionSystem system: productionSystems) {
             if(productionSystem.equals(system))
                 return system;
         }

         return null;
     }
}
