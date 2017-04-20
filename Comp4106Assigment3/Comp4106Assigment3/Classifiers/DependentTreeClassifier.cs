using Comp4106Assigment3.Utilities;
using System;
using System.Collections.Generic;
using QuickGraph;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using GraphSharp;

namespace Comp4106Assigment3.Model
{
    public class DependentTreeClassifier : Classifier
    {
        private DependenceTree dependentTree;
        private IBidirectionalGraph<Node, IEdge<Node>> graph;
        private int Id;

        public DependentTreeClassifier(int Id, DependenceTree dependentTree)
        {
            this.dependentTree = dependentTree;
            this.graph = CreateGraph(dependentTree.GraphToVisualize);
            this.Id = Id;
        }

        private IBidirectionalGraph<Node, IEdge<Node>> CreateGraph(IBidirectionalGraph<Node, IEdge<Node>> graph)
        {
            var newGraph = new BidirectionalGraph<Node, IEdge<Node>>();
            var nodes = new HashSet<Node>();

            foreach(var node in graph.Vertices)
            {
                var newNode = new Node(node.Id);
                newGraph.AddVertex(newNode);
                nodes.Add(newNode);
            }

            foreach(WeightedEdge<Node> edge in graph.Edges)
            {
                var source = nodes.First(node => node.Id == edge.Source.Id);
                var target = nodes.First(node => node.Id == edge.Target.Id);
                var newEdge = new WeightedEdge<Node>(source, target, edge.Weight);
                newGraph.AddEdge(newEdge);
            }

            return newGraph;
        }

        public override double FindProbability(double[] vector)
        {

            var nodes = new Queue<Node>();
            var root = this.GetRootNode();
            var probability = root.GetProbability(vector[root.Id], 0);
            nodes.Enqueue(root);

            while (nodes.Count > 0)
            {
                var parentNode = nodes.Dequeue();

                foreach (var neighbourNode in graph.GetOutNeighbours(parentNode))
                {
                    double x = neighbourNode.GetProbability(vector[neighbourNode.Id], vector[parentNode.Id]);
                    probability *= x;
                    nodes.Enqueue(neighbourNode);
                }
            }

            return probability;
        }

        public override int GetId()
        {
            return this.Id;
        }

        public override void Train(List<double[]> vectors)
        {
            var nodes = new Queue<Node>();
            var root =  this.GetRootNode();
            root.probabilityGivenParentZero = (double)VecotorUtility.CountNumberOfTimesColumnEquals(vectors, root.Id, 0) / vectors.Count;
            root.probabilityGivenParentOne = root.probabilityGivenParentZero;
            nodes.Enqueue(root);

            while(nodes.Count> 0)
            {
                var parentNode = nodes.Dequeue();

                foreach (var childNode in graph.GetOutNeighbours(parentNode))
                {
                    childNode.probabilityGivenParentZero = GetConditionalProbability(vectors, childNode.Id, parentNode.Id, 0, 0);
                    childNode.probabilityGivenParentOne = GetConditionalProbability(vectors, childNode.Id, parentNode.Id, 0, 1);
                    nodes.Enqueue(childNode);
                }
            }
        }

        private double GetConditionalProbability(List<double[]> vectors, int childId, int parentId, int childValue, int parentValue)
        {
            double probabilityChildAndParentEqual =
              (double)VecotorUtility.CountNumberOfTimesColumnsEquals(vectors, childId, parentId, childValue, parentValue) / vectors.Count;
            double probabilityParentEqualsValue = (double)VecotorUtility.CountNumberOfTimesColumnEquals(vectors, parentId, parentValue) / vectors.Count;
            return probabilityChildAndParentEqual / probabilityParentEqualsValue;
        }

        private Node GetRootNode()
        {
            return this.graph.Vertices.
                Where(node => graph.InDegree(node) == 0).First();
        }
    }
}
