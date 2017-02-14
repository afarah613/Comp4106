package Comp.Assignment1.Part2;


import Comp.Assignment1.Move;

public class BoardMove extends Move {

    public int position1;
    public int position2;

    public BoardMove(int position1, int position2) {
        this.position1 = position1;
        this.position2 = position2;
    }

    public String toString() {
        return position1 + ":" + position2;
    }

}
