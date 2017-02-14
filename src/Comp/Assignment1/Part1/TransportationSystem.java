package Comp.Assignment1.Part1;

import Comp.Assignment1.IProductionSystem;
import Comp.Assignment1.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TransportationSystem implements IProductionSystem {

    private ArrayList<Person> leftSide;
    private ArrayList<Person> rightSide;
    private int boat;
    private int time;

    private static final int boatOnLeft = 0;
    private static final int boatOnRight = 1;
    private IProductionSystem prevState;

    public TransportationSystem(ArrayList<Person> leftSide,
                                ArrayList<Person> rightSide,
                                int boat, int time,
                                IProductionSystem prevState)
    {
        this.leftSide = new ArrayList<>(leftSide);
        this.rightSide = new ArrayList<>(rightSide);
        this.time = time;
        this.boat = boat;
        this.prevState = prevState;
    }

    public boolean isSolved()
    {
        return this.leftSide.isEmpty();
    }

    public Collection<Move> generateAllMoves()
    {
        List<Move> moves = new ArrayList<>();

        if(boat == boatOnLeft)
             return generateAllLeftToRightMoves();

        return generateAllRightToLeftMoves();
    }

    private Collection<Move> generateAllLeftToRightMoves()
    {
        List<Move> moves = new ArrayList<>();
        for(int i = 0; i<this.leftSide.size() -1; i++) {
            Person person1 = this.leftSide.get(i);
            for (int j = i + 1; j < this.leftSide.size(); j++) {
                Person person2 = this.leftSide.get(j);
                int transportationTime = getTransportationTime(person1,person2);
                List<Person> people = new ArrayList<Person>();
                people.add(person1);
                people.add(person2);
                moves.add(new TransportationMove(people,transportationTime));
            }
        }
        return moves;
    }

    private Collection<Move> generateAllRightToLeftMoves()
    {
        List<Move> moves = new ArrayList<>();
        for(int i = 0; i<this.rightSide.size() -1; i++) {
            Person person1 = this.rightSide.get(i);
            int transportationTime = person1.getCrossingTime();
            List<Person> people = new ArrayList<Person>();
            people.add(person1);
            moves.add(new TransportationMove(people, transportationTime));

        }
        return moves;
    }

    public TransportationSystem applyMove(Move move, IProductionSystem prevState)
    {
        TransportationMove transportationMove = (TransportationMove) move;

        if(this.boat == boatOnLeft)
        {
            TransportationSystem newState = new TransportationSystem(
                    this.leftSide,
                    this.rightSide,
                    boatOnRight,
                    this.time,
                    prevState);
            newState.leftSide.removeAll(transportationMove.getPeople());
            newState.rightSide.addAll(transportationMove.getPeople());
            newState.time += transportationMove.transportationTime;
            return newState;
        }
        else {
            TransportationSystem newState = new TransportationSystem(
                    this.leftSide,
                    this.rightSide,
                    boatOnLeft,
                    this.time,
                    prevState);
            newState.rightSide.removeAll(transportationMove.getPeople());
            newState.leftSide.addAll(transportationMove.getPeople());
            newState.time += transportationMove.transportationTime;
            return newState;
        }
    }

    private int getTransportationTime(Person person1, Person person2)
    {
        int person1CrossingTime = person1.getCrossingTime();
        int person2CrossingTime = person2.getCrossingTime();

        if(person1CrossingTime > person2CrossingTime)
        {
            return person1CrossingTime;
        }

        return person2CrossingTime;
    }

    private String getSideOfBoat()
    {
        if(boat == boatOnLeft)
            return "Left side";
        return "Right side";
    }

    private TransportationSystem reverseMoves(TransportationSystem transportationSystem)
    {
        TransportationSystem prevMove = null;
        TransportationSystem currentMove = transportationSystem;

        while(currentMove != null)
        {
            TransportationSystem temp = (TransportationSystem) currentMove.prevState;
            currentMove.prevState = prevMove;
            prevMove = currentMove;
            currentMove = temp;
        }

        return prevMove;
    }

    public String getPathToSuccess(IProductionSystem productionSystem)
    {
        StringBuilder builder = new StringBuilder();
        TransportationSystem transportationSystem = (TransportationSystem) productionSystem;
        transportationSystem = reverseMoves(transportationSystem);

        while(transportationSystem != null)
        {
            builder.append(transportationSystem.toString());
            transportationSystem = (TransportationSystem) transportationSystem.prevState;
        }

        return builder.toString();
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Left Side: ");
        for (Person person: this.leftSide) {
            builder.append(person.getName() + " ");
        }

        builder.append("\nRight Side: ");

        for (Person person: this.rightSide) {
            builder.append(person.getName() + " ");
        }

        builder.append("\nBoat: " + getSideOfBoat() + "\nElapsed Time: " +this.time + "\n\n");
        return builder.toString();
    }
}
