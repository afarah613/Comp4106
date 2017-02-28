package Comp.Assignment1;

import Comp.Assignment1.Part1.*;
import Comp.Assignment1.Part2.Board;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // write your code here

//        int[] crossingValues = new int[] {1,2,3,8,5,13};
//        ArrayList<Person> persons = new ArrayList<>();
//        for(int i = 0; i<crossingValues.length; i++)
//        {
//            persons.add(new Person("a" + i, crossingValues[i]));
//        }
//        TransportationSystem state = new TransportationSystem(persons, new ArrayList<Person>(), 0,0,0,2,null);
//        IProductionSystem productionSystem= ProductionSystemSolver.AStarSearch(state);
//        String s = ProductionSystemSolver.getPathToSuccess(productionSystem);
//        System.out.print(s);
//
       int[] tiles = new int[] {5,6,7,8,9,4,0,3,2,1};
//
       long startTime = System.currentTimeMillis();
//
        Board board = new Board(tiles, 2, 5,null,0,2 );
        IProductionSystem productionSystem = ProductionSystemSolver.AStarSearch( board);

        String solved2 = ProductionSystemSolver.getPathToSuccess(productionSystem);
        System.out.println(solved2);
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime)/1000);

        //Simulation.simulate();
    }
}
