using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Comp4106Assigment3.Model
{
    public class DecisionTreeClassifier : Classifier
    {
        private int id;
        private int numberOfClasses;
        private DecisionTree tree;

        public DecisionTreeClassifier(int id, int numberOfClasses)
        {
            this.id = id;
            this.numberOfClasses = numberOfClasses;
        }

        public override double FindProbability(double[] vector)
        {
            var root = tree.GetRootNode();

            while(root != null && root.right != null && root.left != null)
            {
                var value = vector[root.Id];

                if (value == 0)
                    root = root.left;
                else
                    root = root.right;
            }

            return root.Id == this.id ? 1 : 0;
        }

        public override int GetId()
        {
            return id;
        }

        public override void Train(List<double[]> vectors)
        {
            this.tree = new DecisionTree(vectors, this.numberOfClasses);
            //throw new NotImplementedException();
        }
    }
}
