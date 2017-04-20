using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FinalProject
{
    public class PlayerLineup
    {
        private IEnumerable<Player> players;
        private HashSet<Player> currentLineup;
        private const int threshold = 170;
        private double stateValue;
        private double level;

        public PlayerLineup(List<Player> players)
        {
            this.players = players;
            this.stateValue = 1;
            this.currentLineup = new HashSet<Player>();
        }

        public PlayerLineup(IEnumerable<Player> players, IEnumerable<Player> currentLineup, Player newPlayer, double stateValue, double level)
        {
            this.players = players;
            this.stateValue = stateValue;
            this.level = level;
            this.currentLineup = new HashSet<Player>(currentLineup);
            this.currentLineup.Add(newPlayer);
        }

        public IEnumerable<PlayerLineup> GenerateNextStates()
        {
            var states = new List<PlayerLineup>();
            var playersToAdd = GetPlayers();

            foreach(var player in playersToAdd)
            {
                if (!this.currentLineup.Contains(player))
                {
                    double newLevel = 1 + this.level;
                    var state = new PlayerLineup(this.players, this.currentLineup, player, this.stateValue, newLevel);
                    state.setValue();
                    states.Add(state);
                }
            }
            return states;
        }

        public bool IsSolved()
        {
            return this.currentLineup.Count == 5 && this.CalculateFantasyPoints() >= threshold;
        }

        private IEnumerable<Player> GetPlayers()
        {
            var numberOfGuards = CountNumberOfPositionInLineup("G");
            var numberOfForwards = CountNumberOfPositionInLineup("F");

            if(this.IsSolved())
            {
                return new List<Player>();
            }

            else if (numberOfGuards < 2)
            {
                return GetPosistion("G");
            }       

            else if (numberOfForwards < 2)
            {
                return GetPosistion("F");
            }

            else
            {
                return GetPosistion("C");
            }
        }

        private int CountNumberOfPositionInLineup(String posistion)
        {
            return this.currentLineup.Count(player => player.position.Contains(posistion));
        }

        public double GetStateValue()
        {
            return this.stateValue;
        }

        private IEnumerable<Player> GetPosistion(String posistion)
        {
            return this.players.Where(player => player.position.Contains(posistion));
        }

        private void setValue()
        {          
            this.stateValue = CalculateFantasyPoints() + this.level;
        }

        public override string ToString()
        {
            var builder = new StringBuilder();

            foreach(var player in currentLineup)
            {
                builder.AppendLine(player.ToString());
            }

            builder.AppendLine("Fantasy Points: " + this.CalculateFantasyPoints());

            return builder.ToString();
        }

        private double CalculateFantasyPoints()
        {
            double score = 0;

            foreach(var player in currentLineup)
            {
                score += player.CalculateFantasyPoints();
            }

            return score;
        }

        public override bool Equals(object obj)
        {
            if(obj != null && obj.GetType() != this.GetType())
            {
                return false;
            }

            var lineup = (PlayerLineup)obj;
            var lineupSet = new HashSet<Player>(lineup.currentLineup);
            return new HashSet<Player>(this.currentLineup).SetEquals(lineupSet);
        }

        public override int GetHashCode()
        {
            int hash = 0;

            foreach(Player player in this.currentLineup)
            {
                hash = hash ^ EqualityComparer<String>.Default.GetHashCode(player.name);
            }

            return hash;
        }
    }

    internal class PlayerLineupComparer : IComparer<PlayerLineup>
    {
        public int Compare(PlayerLineup x, PlayerLineup y)
        {
            if (x.GetStateValue() > y.GetStateValue())
                return 1;
            else if (y.GetStateValue() > x.GetStateValue())
                return -1;
            else
                return 0;
        }
    }
}
