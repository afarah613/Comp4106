package Comp.Assignment1.Part1;


public class Person {

    private int crossingTime;
    private String name;

    public Person(String name, int crossingTime)
    {
        this.crossingTime = crossingTime;

        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public int getCrossingTime()
    {
        return  this.crossingTime;
    }
}