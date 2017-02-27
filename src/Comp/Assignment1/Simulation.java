package Comp.Assignment1;

import Comp.Assignment1.Part1.Person;
import Comp.Assignment1.Part1.TransportationSystem;
import Comp.Assignment1.Part2.Board;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Simulation {

    public static int getHeuristic()
    {
        System.out.println("Select a heuristic: 1,2,3.");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true)
        {
            try {
                String line = br.readLine();
                int value = Integer.parseInt(line);

                if(value ==1)
                    return 1;
                else if(value == 2)
                    return 2;
                else if (value == 3)
                    return 3;

                System.err.println("Invalid number! Please try again");
            }
            catch(Exception e){
                System.err.println("Invalid number! Please try again");
            }
        }
    }

    public static IProductionSystem createBoard()
    {
        System.out.println("Enter the row then the column separated by a comma. Ex 1,2");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int row,col;
        int[] tiles;

        while(true)
        {
            try{
                String line = br.readLine();
                String[] result = line.split(",");
                if(result.length!= 2) {
                    System.err.println("Invalid Format! Please try again");
                }
                else{
                    row = Integer.parseInt(result[0]);
                    col = Integer.parseInt(result[1]);
                    break;
                }
            }catch(Exception e){
                System.err.println("Invalid Format! Please try again");
            }
        }

        System.out.println("Enter the starting board with the number separated by a comma. Ex 1,2,3,4,5,6,7,8,0");

        while (true)
        {
            try{
                String line = br.readLine();
                String[] result = line.split(",");
                if(result.length!= (row *col)) {
                    System.err.println("Invalid Format! Please try again");
                }
                else{
                    tiles = new int[result.length];

                    for(int i =0 ; i< tiles.length; i++) {
                        tiles[i] = Integer.parseInt(result[i]);
                    }
                    break;
                }
            }catch(Exception e){
                System.err.println("Invalid Format! Please try again");
            }
        }

        return new Board(tiles, row, col,null,0,0);
    }

    public static IProductionSystem createTransportationSystem()
    {
        System.out.println("Enter the crossing times for each person with the " +
                "number separated by a comma. Ex 1,2,3,4,5,6,7,8,0");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int[] crossingValues;

        while (true) {
            try {
                String line = bufferedReader.readLine();
                String[] result = line.split(",");


                crossingValues = new int[result.length];

                for (int i = 0; i < crossingValues.length; i++) {
                    crossingValues[i] = Integer.parseInt(result[i]);
                }
                break;

            } catch (Exception e) {
                System.err.println("Invalid Format! Please try again");
            }
        }

        ArrayList<Person> persons = new ArrayList<>();

        for(int i = 0; i<crossingValues.length; i++)
        {
            persons.add(new Person("a" + i, crossingValues[i]));
        }

        return new TransportationSystem(persons, new ArrayList<Person>(), 0, 0,0,0,null);
    }

    public static void simulate()
    {
        System.out.println("Welcome. Would You like to run part 1 or 2?\nEnter 1 or 2");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        IProductionSystem productionSystem;

        while(true)
        {
            try
            {
                String line = br.readLine();
                if(line.equals("1"))
                {
                    productionSystem = createTransportationSystem();
                    break;
                }
                else if(line.equals("2"))
                {
                    productionSystem = createBoard();
                    break;
                }
                System.out.println("Invalid Format! Please enter 1 or 2");

            } catch (Exception e) {
                System.err.println("Invalid");
            }
        }

        System.out.println("Select the strategy?\nEnter BFS or DFS or A*");

        while(true)
        {
            try
            {
                String line = br.readLine().toLowerCase();
                if(line.equals("bfs"))
                {
                    long startTime = System.currentTimeMillis();
                    IProductionSystem finalState =ProductionSystemSolver.BreadthFirstSearch(productionSystem);
                    String solved = ProductionSystemSolver.getPathToSuccess(finalState);
                    System.out.println(solved);
                    long endTime = System.currentTimeMillis();
                    System.out.println("Total elapsed time: " +(endTime - startTime)/1000 + " seconds");
                    break;
                }
                else if(line.equals("dfs"))
                {
                    long startTime = System.currentTimeMillis();
                    IProductionSystem finalState = ProductionSystemSolver.DepthFirstSearch(productionSystem);
                    String solved = ProductionSystemSolver.getPathToSuccess(finalState);
                    System.out.println(solved);
                    long endTime = System.currentTimeMillis();
                    System.out.println("Total elapsed time: " +(endTime - startTime)/1000 + " seconds");

                    break;
                }
                else if(line.equals("a*"))
                {
                    long startTime = System.currentTimeMillis();
                    productionSystem.setHeuristic(getHeuristic());
                    IProductionSystem finalState = ProductionSystemSolver.AStarSearch(productionSystem);
                    String solved = ProductionSystemSolver.getPathToSuccess(finalState);
                    System.out.println(solved);
                    long endTime = System.currentTimeMillis();
                    System.out.println("Total elapsed time: " +(endTime - startTime)/1000 + " seconds");
                    break;
                }

                System.out.println("Invalid Format! Enter BFS or DFS or A*");

            } catch (Exception e) {
                System.err.println("Invalid Format! Enter BFS or DFS or A*");
            }
        }
    }
}
