using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
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

namespace UI
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public List<string> Teams = new List<string>() {
            "SAC",
            "MEM",
            "OKC",
            "NYK",
            "NOP",
            "LAC",
            "SAS",
            "POR",
            "IND",
            "MIA",
            "WAS",
            "DAL",
            "MIL",
            "DET",
            "ORL",
            "HOU",
            "DEN",
            "CHI",
            "GSW",
            "BRK",
            "LAL",
            "CHO",
            "ATL",
            "TOR",
            "MIN",
            "PHO",
            "UTA",
            "BOS",
            "PHI",
            "CLE"};

        public MainWindow()
        {
            this.DataContext = new LineupViewModel();
            InitializeComponent();
            this.SelectedTeams.ItemsSource = this.Teams;
        }

        private void BFS(object sender, RoutedEventArgs e)
        {          
          ((LineupViewModel) this.DataContext).BFS(GetSelectedTeams());
        }

        private void DFS(object sender, RoutedEventArgs e)
        {
            ((LineupViewModel)this.DataContext).DFS(GetSelectedTeams());
        }

        private IEnumerable<String> GetSelectedTeams()
        {
            var teams = new List<String>();

            foreach(var team in this.SelectedTeams.SelectedItems)
            {
                teams.Add((String)team);
            }

            return teams;
        }

        private void AStar(object sender, RoutedEventArgs e)
        {
            ((LineupViewModel)this.DataContext).AStar(GetSelectedTeams());
        }

        private void HillClimbing(object sender, RoutedEventArgs e)
        {
            ((LineupViewModel)this.DataContext).HillClimbing(GetSelectedTeams());
        }
    }
}
