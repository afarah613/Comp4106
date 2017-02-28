package Comp.Assignment1.Part1;

import Comp.Assignment1.IProductionSystem;
import Comp.Assignment1.Move;

import java.util.*;

public class TransportationSystem implements IProductionSystem {

    private ArrayList<Person> leftSide;
    private ArrayList<Person> rightSide;
    private int torch;
    private int time;
    private int stateValue;
    private int level;
    private int heuristic;

    private static final int torchOnLeft = 0;
    private static final int torchOnRight = 1;
    private IProductionSystem previousState;

    public TransportationSystem(ArrayList<Person> leftSide,
                                ArrayList<Person> rightSide,
                                int torch,
                                int time,
                                int level,
                                int heuristic,
                                IProductionSystem prevState)
    {
        this.leftSide = new ArrayList<>(leftSide);
        this.rightSide = new ArrayList<>(rightSide);
        this.time = time;
        this.torch = torch;
        this.level = level;
        this.heuristic = heuristic;
        this.previousState = prevState;
        setValue();
    }

    public boolean isSolved()
    {
        return this.leftSide.isEmpty();
    }

    public void setStateValue(int value)
    {
        this.stateValue = value;
    }

    public Collection<IProductionSystem> generateAllChildStates(IProductionSystem previousState)
    {
        HashSet<IProductionSystem> productionSystemHashSet = new HashSet<>();

        for(Move move: generateAllMoves())
        {
            productionSystemHashSet.add(applyMove(move, previousState));
        }

        return productionSystemHashSet;
    }

    public void setHeuristic(int heuristic)
    {
        this.heuristic = heuristic;
    }

    private Collection<Move> generateAllMoves()
    {
        if(torch == torchOnLeft)
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
                List<Person> people = new ArrayList<>();
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
            List<Person> people = new ArrayList<>();
            people.add(person1);
            moves.add(new TransportationMove(people, transportationTime));

        }
        return moves;
    }

    private int maxCrossingTime(List<Person> persons)
    {
        int max =0;

        for (Person person: persons) {

            int crossingTime = person.getCrossingTime();
            if(crossingTime > max)
                max = crossingTime;
        }

        return max;
    }

    private int maxNCrossingTime(List<Person> persons)
    {
        int n = this.leftSide.size()/2;
        int i = this.leftSide.size()-1;
        int sumN = 0;
        Collections.sort(this.leftSide);

        while(n>0)
        {
            sumN+= this.leftSide.get(i).getCrossingTime();
            i--;
            n--;
        }

        return sumN;
    }

    private void setValue() {

        int heuristicValue;
        if (this.heuristic == 1)
            heuristicValue = maxCrossingTime(this.leftSide);
        else if (this.heuristic == 2)
            heuristicValue = maxNCrossingTime(this.leftSide);
        else
            heuristicValue = (maxNCrossingTime(this.leftSide) + maxCrossingTime(this.leftSide))/2;
        this.stateValue = heuristicValue + this.level;
    }

    public int getStateValue()
    {
        return this.stateValue;
    }

    public void setPreviousState(IProductionSystem previousState)
    {
        this.previousState = previousState;
    }

    private TransportationSystem applyMove(Move move, IProductionSystem prevState)
    {
        TransportationMove transportationMove = (TransportationMove) move;

        if(this.torch == torchOnLeft)
        {
            TransportationSystem newState = new TransportationSystem(
                    this.leftSide,
                    this.rightSide,
                    torchOnRight,
                    this.time,
                    this.level + transportationMove.transportationTime,
                    this.heuristic,
                    prevState);
            newState.leftSide.removeAll(transportationMove.getPeople());
            newState.rightSide.addAll(transportationMove.getPeople());
            newState.time += transportationMove.transportationTime;
            newState.setValue();
            return newState;
        }
        else {
            TransportationSystem newState = new TransportationSystem(
                    this.leftSide,
                    this.rightSide,
                    torchOnLeft,
                    this.time,
                    this.level + transportationMove.transportationTime,
                    this.heuristic,
                    prevState);
            newState.rightSide.removeAll(transportationMove.getPeople());
            newState.leftSide.addAll(transportationMove.getPeople());
            newState.time += transportationMove.transportationTime;
            newState.setValue();
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

    private String getTorchSide()
    {
        if(torch == torchOnLeft)
            return "Left side";
        return "Right side";
    }

    public IProductionSystem getPreviousState()
    {
        return this.previousState;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Left Side: ");
        for (Person person: this.leftSide) {
            builder.append(person.getCrossingTime() + " ");
        }

        builder.append("\nRight Side: ");

        for (Person person: this.rightSide) {
            builder.append(person.getCrossingTime() + " ");
        }

        builder.append("\nTorch: " + getTorchSide() + "\nElapsed Time: " +this.time + "\n\n");
        return builder.toString();
    }
}
