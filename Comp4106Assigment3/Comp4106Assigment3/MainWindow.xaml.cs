 using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using QuickGraph;
using GraphSharp;
using System.ComponentModel;
using Comp4106Assigment3.Model;
using Comp4106Assigment3.ViewModel;

namespace Comp4106Assigment3
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            this.DataContext = new ClassifierViewModel(@"C:\Users\Ali\Downloads\Datasets\Datasets\testData.csv");
            InitializeComponent();
        }

        private void IndependentClassification(object sender, RoutedEventArgs e)
        {
            ((ClassifierViewModel)this.DataContext).IndependentClassification();
        }

        private void DependentClassification(object sender, RoutedEventArgs e)
        {
            ((ClassifierViewModel)this.DataContext).DependentClassifcation();
        }

        private void DecisionClassification(object sender, RoutedEventArgs e)
        {
            ((ClassifierViewModel)this.DataContext).DecisionClassification();
        }
    }
}
