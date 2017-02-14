package Comp.Assignment1;

import Comp.Assignment1.Part2.Board;

public class Main {

    public static void main(String[] args) {
        // write your code here

//        int[] crossingValues = new int[] {1,2,3,5,8,13,15,17};
//        ArrayList<Person> persons = new ArrayList<>();
//        for(int i = 0; i<crossingValues.length; i++)
//        {
//            persons.add(new Person("a" + i, crossingValues[i]));
//        }
//        TransportationSystem state = new TransportationSystem(persons, new ArrayList<Person>(), 0,0,null);
//        IProductionSystem productionSystem= ProductionSystemSolver.BreadthFirstSearch(state);
//        String s = state.getPathToSuccess(productionSystem);
//        System.out.print(s);

        int[] tiles = new int[] {8,0,4,9,3,6,5,2,7,1};

        long startTime = System.currentTimeMillis();

        Board board = new Board(tiles, 2, 5,null);
        IProductionSystem productionSystem = ProductionSystemSolver.BreadthFirstSearch(board);

        //Move move2 = ProductionSystemSolver.DepthFirstSearch(board);
        String solved2 = board.getPathToSuccess(productionSystem);
        System.out.print(solved2);
        long endTime = System.currentTimeMillis();
        System.out.println((endTime -startTime)/1000);
    }
}
