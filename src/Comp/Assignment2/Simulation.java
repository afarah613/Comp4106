package Comp.Assignment2;

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

    public static void main(String[] args)
    {
        FocusBoard board = new FocusBoard();
        IHeuristic heuristic = new DifferenceHeuristic();
        IHeuristic heuristic1 = new CapturedHeuristic();
        IPlayer player = new ComputerPlayer(BoardPiece.GREEN,heuristic,board);
        IPlayer player2 = new ComputerPlayer(BoardPiece.RED,heuristic1,board);
        Simulation simulation = new Simulation(board, player, player2);
        simulation.simulate();
    }
}
