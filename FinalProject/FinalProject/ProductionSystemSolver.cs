using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FinalProject
{
    public static class ProductionSystemSolver
    {
        public static PlayerLineup BreadthFirstSearch(PlayerLineup productionSystem)
        {
            Queue<PlayerLineup> productionSystemQueue = new Queue<PlayerLineup>();
            HashSet<PlayerLineup> visitedStates = new HashSet<PlayerLineup>();

            productionSystemQueue.Enqueue(productionSystem);
            visitedStates.Add(productionSystem);

            while (productionSystemQueue.Count() > 0)
            {
                PlayerLineup currentState = productionSystemQueue.Dequeue();

                foreach(PlayerLineup newState in currentState.GenerateNextStates())
                {
                    if (!visitedStates.Contains(newState))
                    {
                        visitedStates.Add(newState);

                        if (newState.IsSolved())
                        {
                            return newState;
                        }
                        else 
                        {
                            productionSystemQueue.Enqueue(newState);
                        }
                    }
                }
            }

            return null;
        }

        public static PlayerLineup DepthFirstSearch(PlayerLineup productionSystem)
        {
            Stack<PlayerLineup> productionSystemQueue = new Stack<PlayerLineup>();
            HashSet<PlayerLineup> visitedStates = new HashSet<PlayerLineup>();

            productionSystemQueue.Push(productionSystem);
            visitedStates.Add(productionSystem);

            while (productionSystemQueue.Count() > 0)
            {
                PlayerLineup currentState = productionSystemQueue.Pop();

                foreach (PlayerLineup newState in currentState.GenerateNextStates())
                {
                    if (!visitedStates.Contains(newState))
                    {
                        visitedStates.Add(newState);

                        if (newState.IsSolved())
                        {
                            return newState;
                        }
                        else
                        {
                            productionSystemQueue.Push(newState);
                        }
                    }
                }
            }

            return null;
        }

        public static PlayerLineup AStarSearch(PlayerLineup productionSystem)
        {
            var open = new C5.IntervalHeap<PlayerLineup>(new PlayerLineupComparer());
            var closed = new HashSet<PlayerLineup>();
            open.Add(productionSystem);

            while (open.Count() > 0)
            {
                PlayerLineup currentState = open.FindMax();
                closed.Add(currentState);

                if (currentState.IsSolved())
                {
                    return currentState;
                }

                foreach (PlayerLineup newState in currentState.GenerateNextStates())
                {
                    var inClosed = closed.Contains(newState);

                    if (!inClosed)
                    {
                        var inOpen = open.Contains(newState);
                        if (!inOpen)
                            open.Add(newState);
                        else
                        {
                            PlayerLineup openState = open.First(line => line.Equals(newState));

                            if (newState.GetStateValue() > openState?.GetStateValue())
                            {
                                open.Add(newState);
                            }
                        }

                    }

                }
            }

            return null;
        }

        public static PlayerLineup HillClimbing(PlayerLineup productionSystem)
        {

            var neighbors = productionSystem.GenerateNextStates();
            var counter = 1;

            while(neighbors.Count() > 0)
            {
                PlayerLineup nextNode = null;
                double maxEval = Int32.MinValue;

                foreach(var node in neighbors)
                {
                    var currentValue = node.GetStateValue();
                    counter++;
                    if(currentValue > maxEval)
                    {
                        maxEval = currentValue;
                        nextNode = node;
                    }
                }

                if (nextNode.IsSolved())
                    return nextNode;
                else
                    neighbors = nextNode.GenerateNextStates();
            }
           
            return null;
        }
    }
}
