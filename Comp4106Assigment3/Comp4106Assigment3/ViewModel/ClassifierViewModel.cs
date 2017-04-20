using System;
using System.Collections.Generic;
using System.IO;
using QuickGraph;
using Comp4106Assigment3.Model;
using GraphSharp;
using Comp4106Assigment3.Utilities;
using System.ComponentModel;
using System.Linq;

namespace Comp4106Assigment3.ViewModel
{
    public class ClassifierViewModel : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;

        private List<Class> Classes;
        private IBidirectionalGraph<Node, IEdge<Node>> _graphToVisualize;
        private String _confusionMatrix;
        private List<double[]> vectors;

        public String ConfusionMatrix
        {
            get
            {
                return this._confusionMatrix;
            }
            set
            {
                this._confusionMatrix = value;
                OnPropertyChanged("ConfusionMatrix");
            }
        }

        public IBidirectionalGraph<Node, IEdge<Node>> GraphToVisualize
        {
            get
            {
                return this._graphToVisualize;
            }
            set
            {
                this._graphToVisualize = value;
                OnPropertyChanged("GraphToVisualize");
            }
        }

        public ClassifierViewModel(String filePath)
        {
            this.vectors = FileUtility.ConvertCSVToList(filePath);
            var fileName = Path.GetFileName(filePath);

            if (fileName != "testData")
                VecotorUtility.ConvertListToBinary(vectors);
            

            this.Classes = new List<Class>(CreateClasses(vectors));
        }

        public void IndependentClassification()
        {

            var classifiers = new List<Classifier>();

            foreach(var c in this.Classes)
            {
                classifiers.Add(new BayesClassifier(c.classId));
            }

            this.ClassifyData(classifiers);     
        }

        public void DecisionClassification()
        {
            var decisionTree = new DecisionTree(this.vectors, this.Classes.Count);
            this.GraphToVisualize = decisionTree.GraphToVisualize;

            var classifiers = new List<Classifier>();

            foreach (var c in this.Classes)
            {
                classifiers.Add(new DecisionTreeClassifier(c.classId, this.Classes.Count()));
            }

            this.ClassifyData(classifiers);
        }

        public void DependentClassifcation()
        {
            var classifiers = new List<Classifier>();
            var dependentTree = new DependenceTree(this.vectors);

            foreach (var c in this.Classes)
            {
                classifiers.Add(new DependentTreeClassifier(c.classId, dependentTree));
            }

            this.GraphToVisualize = dependentTree.GraphToVisualize;
            this.ClassifyData(classifiers);
        }

        private void ClassifyData(IEnumerable<Classifier> classifiers)
        {
            var matrices = TrainerUtility.TestAndTrainData(this.Classes, classifiers);
            var accuracies = matrices.Select(matrix => matrix.CalculateAccuaracy());
            var averageAccuracy =  accuracies.Average();
            averageAccuracy *= 100;

            this.ConfusionMatrix = matrices.Select(matrix => matrix.ToString()).Aggregate((a, b) => a + "\n" + b);
            this.ConfusionMatrix += $"\nAverage Accuracy: {averageAccuracy}%";
        }

        private IEnumerable<Class> CreateClasses(List<double[]> values)
        {
            var classes = new List<Class>();
            var visitedIds = new HashSet<int>();

            foreach(var feature in values)
            {
                var classId = (int)feature[0];

                if (visitedIds.Contains(classId))
                {
                    classes.Single(cl => classId == cl.classId)
                        .AddFeatures(feature);
                }
                else
                {
                    var newClass = new Class(classId);
                    classes.Add(newClass);
                    visitedIds.Add(classId);
                }
            }

            return classes;
        }

        protected void OnPropertyChanged(PropertyChangedEventArgs e)
        {
            PropertyChanged?.Invoke(this, e);
        }

        protected void OnPropertyChanged(string propertyName)
        {
            OnPropertyChanged(new PropertyChangedEventArgs(propertyName));
        }
    }
}
