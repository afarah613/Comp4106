package Comp.Assignment2;

import java.util.List;

/**
 * Created by Ali on 2017-03-11.
 */
public class ComputerPlayer implements IPlayer {

    private char color;
    private IHeuristic heuristic;
    private FocusBoard focusBoard;
    private final int MAX_DEPTH = 4;
    private Move bestMove;

    public ComputerPlayer(char color, IHeuristic heuristic, FocusBoard board)
    {
        this.color = color;
        this.focusBoard = board;
        this.heuristic = heuristic;
    }

    @Override
    public Move getMove() {
        alphaBeta(this.focusBoard,MAX_DEPTH, Integer.MIN_VALUE,Integer.MAX_VALUE);
        return bestMove;
    }

    @Override
    public char getColor() {
        return this.color;
    }

    @Override
    public void setColor(char color) {
        this.color = color;
    }

    private int alphaBeta(
            FocusBoard board,
            int depth,
            int alpha,
            int beta)
    {
        List<Move> moves = board.generateAllMoves();

        if(depth == 0 || moves.isEmpty() || board.isGameOver())
            return heuristic.getValue(board, this.color);

        if (board.getCurrentTurn() == color)
        {
            for (Move move: moves) {

                FocusBoard newBoard = new FocusBoard(board);
                newBoard.applyMove(move);
                int newDepth = depth-1;
                int result = alphaBeta(newBoard,newDepth,alpha,beta);

                if(result > alpha)
                {
                    if(depth == MAX_DEPTH)
                        bestMove = move;
                    alpha = result;
                }

                if(beta<= alpha)
                    break;
            }

            return alpha;
        }
        else
        {
            for (Move move: moves) {

                FocusBoard newBoard = new FocusBoard(board);
                newBoard.applyMove(move);
                int newDepth = depth-1;
                beta = Math.min(beta, alphaBeta(newBoard,newDepth, alpha,beta));
                if(beta<= alpha)
                    break;;
            }
            return beta;
        }
    }
}
