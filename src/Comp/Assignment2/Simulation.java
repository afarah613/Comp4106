package Comp.Assignment2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Ali on 2017-03-05.
 */
public class Simulation {

    private IPlayer player1;
    private IPlayer player2;
    private FocusBoard focusBoard;

    public Simulation(FocusBoard focusBoard, IPlayer player1, IPlayer player2)
    {
        this.player1 = player1;
        this.player2 = player2;
        this.focusBoard = focusBoard;
    }

    public void simulate()
    {
        System.out.println(this.focusBoard);

        while (!this.focusBoard.isGameOver())
        {
            Move player1Move = player1.getMove();

            while(!this.focusBoard.canPlayMove(player1Move))
            {
                System.out.println("Move is invalid! Please try again");
                player1Move = player1.getMove();
            }

            this.focusBoard.applyMove(player1Move);
            System.out.println(this.focusBoard);
            Move player2Move = player2.getMove();

            if(this.focusBoard.isGameOver())
                break;

            while(!this.focusBoard.canPlayMove(player2Move))
            {
                System.out.println("Move is invalid! Please try again");
                player2Move = player2.getMove();
            }

            this.focusBoard.applyMove(player2Move);
            System.out.println(this.focusBoard);
        }
    }

    private static IHeuristic getHeuristic()
    {
        System.out.println("Select the type of heuristic. Enter 1 for Difference or 2 for Captured");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true)
        {
            try{
                String line = br.readLine();
                int type = Integer.parseInt(line);

                if(type == 1)
                    return new DifferenceHeuristic();
                else if(type ==2)
                    return new CapturedHeuristic();


            }catch(Exception e){
                System.err.println("Invalid Format! Please try again");
                System.out.println("Enter 1 for Difference or 2 for Captured");
            }
        }
    }

    public static IPlayer getPlayer(char color, FocusBoard board)
    {
        System.out.println("Select the type of player. Enter 1 for Human or 2 for AI");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true)
        {
            try{
                String line = br.readLine();
                int type = Integer.parseInt(line);

                if(type == 1)
                    return new HumanPlayer(color);
                else
                {
                    IHeuristic heuristic = getHeuristic();
                    return new ComputerPlayer(color, heuristic, board);
                }
            }catch(Exception e){
                System.err.println("Invalid Format! Please try again");
                System.out.println("Enter 1 for Human or 2 for AI");
            }
        }
    }

    public static void main(String[] args)
    {
        FocusBoard board = new FocusBoard();
//        IHeuristic heuristic = new DifferenceHeuristic();
//        IHeuristic heuristic1 = new CapturedHeuristic();
//        IPlayer player = new ComputerPlayer(BoardPiece.GREEN,heuristic,board);
//        IPlayer player2 = new ComputerPlayer(BoardPiece.RED,heuristic1,board);

        IPlayer player = getPlayer(BoardPiece.GREEN,board);
        IPlayer player2 = getPlayer(BoardPiece.RED,board);
        Simulation simulation = new Simulation(board, player, player2);
        simulation.simulate();
    }
}
