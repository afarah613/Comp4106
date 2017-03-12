package Comp.Assignment2;

public class CapturedHeuristic implements IHeuristic {
    @Override
    public int getValue(FocusBoard focusBoard, IPlayer player) {

        if (player.getColor() == BoardPiece.GREEN)
            return focusBoard.getGreenCaptured();
        else
            return focusBoard.getRedCaptured();
    }
}
