package Comp.Assignment1;

import Comp.Assignment1.Part1.*;
import Comp.Assignment1.Part2.Board;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // write your code here

//        int[] crossingValues = new int[] {1,2,3,5,8,13};
//        ArrayList<Person> persons = new ArrayList<>();
//        for(int i = 0; i<crossingValues.length; i++)
//        {
//            persons.add(new Person("a" + i, crossingValues[i]));
//        }
//        TransportationSystem state = new TransportationSystem(persons, new ArrayList<Person>(), 0,0,0,1,null);
//        IProductionSystem productionSystem= ProductionSystemSolver.AStarSearch(state);
//        String s = ProductionSystemSolver.getPathToSuccess(productionSystem);
//        System.out.print(s);

        int[] tiles = new int[] {9,8,7,6,5,4,3,2,1,0};

        long startTime = System.currentTimeMillis();

        Board board = new Board(tiles, 2, 5,null,0,1);
        IProductionSystem productionSystem = ProductionSystemSolver.AStarSearch(board);

        String solved2 = ProductionSystemSolver.getPathToSuccess(productionSystem);
        System.out.print(solved2);
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime)/1000);

      //  Simulation.simulate();
    }
}
