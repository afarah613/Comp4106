using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Comp4106Assigment3.Model
{
    public abstract class Classifier
    {
        private int id;

        public abstract void Train(List<double[]> vectors);
        public abstract double FindProbability(double[] vector);
        public abstract int GetId();
    }
}
