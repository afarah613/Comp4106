package Comp.Assignment1.Part1;

import Comp.Assignment1.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 2017-02-11.
 */
public class TransportationMove extends Move {

    private List<Person> people;
    public int transportationTime;

    public TransportationMove(List<Person> people, int transportationTime)
    {
        this.people = new ArrayList<>(people);
        this.transportationTime = transportationTime;
    }

    public List<Person> getPeople()
    {
        return this.people;
    }
}
