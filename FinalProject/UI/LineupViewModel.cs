using FinalProject;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UI
{
    public class LineupViewModel : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;

        public List<Player> players;
        private string _lineup;

        public String Lineup
        {
            get
            {
                return this._lineup;
            }
            set
            {
                this._lineup = value;
                OnPropertyChanged("Lineup");
            }
        }

        public LineupViewModel()
        {
        }

        public void BFS(IEnumerable<String> teams)
        {
            var players = Program.GetAllPlayers(teams);
            if (players.Any())
            {
                var lineup = new PlayerLineup(players);
                var optimalLineup = ProductionSystemSolver.BreadthFirstSearch(lineup);
                this.Lineup = optimalLineup?.ToString();
            }
        }

        public void DFS(IEnumerable<String> teams)
        {
            var players = Program.GetAllPlayers(teams);
            if (players.Any())
            {
                var lineup = new PlayerLineup(players);
                var optimalLineup = ProductionSystemSolver.DepthFirstSearch(lineup);
                this.Lineup = optimalLineup?.ToString();
            }
        }

        public void AStar(IEnumerable<String> teams)
        {
            var players = Program.GetAllPlayers(teams);
            if (players.Any())
            {
                var lineup = new PlayerLineup(players);
                var optimalLineup = ProductionSystemSolver.AStarSearch(lineup);
                this.Lineup = optimalLineup?.ToString();
            }
        }

        public void HillClimbing(IEnumerable<string> teams)
        {
            var players = Program.GetAllPlayers(teams);
            if (players.Any())
            {
                var lineup = new PlayerLineup(players);
                var optimalLineup = ProductionSystemSolver.HillClimbing(lineup);
                this.Lineup = optimalLineup?.ToString();
            }
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
