package Comp.Assignment2;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class HumanPlayer implements IPlayer {

    private char color;

    public HumanPlayer(char color)
    {
        this.color = color;
    }

    @Override
    public void setColor(char color) {
        this.color = color;
    }

    @Override
    public Move getMove() {

        System.out.println("Select position you would like to move");
        Position currentPlayerPosition = getPosition();

        System.out.println("Select destination position.");
        Position opponentPosition = getPosition();

        int numberOfPieces = this.getNumberOfPieces();

        return new Move(currentPlayerPosition, opponentPosition, numberOfPieces);
    }

    @Override
    public char getColor() {
        return this.color;
    }

    private int getNumberOfPieces()
    {
        int numberOfPieces = 0;

        System.out.println("Enter the number of pieces you would like to move");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true)
        {
            try{
                String line = br.readLine();
                numberOfPieces = Integer.parseInt(line);

                if(numberOfPieces > 0)
                    break;
            }catch(Exception e){
                System.err.println("Invalid Format! Please try again");
                System.out.println("Enter the number of pieces you would like to move");
            }
        }

        return numberOfPieces;
    }

    private Position getPosition()
    {
        int row = 0, col = 0;

        System.out.println("Enter the row then the column separated by a comma. Ex 1,2");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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

        return new Position(row, col);
    }
}
