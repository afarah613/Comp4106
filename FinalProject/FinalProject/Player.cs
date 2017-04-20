using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FinalProject
{
    public class Player
    {
        public string name;
        public string team;
        public string position;
        public PlayerStats playerStats;

        public Player(
            string firstName,
            string team,
            string position,
            PlayerStats stats)
        {
            this.name = firstName;
            this.team = team;
            this.playerStats = stats;
            this.position = position;
        }

        public override int GetHashCode()
        {
            return (31 * this.name.GetHashCode()) + (31 * this.team.GetHashCode());
        }

        public override bool Equals(object obj)
        {
            if(obj == null || this.GetType() != obj.GetType())
            {
                return false;
            }

            var player = (Player)obj;

            return player.name == name && player.team == team && player.position == position;
        }

        public override string ToString()
        {
            return $"{this.name} Pts: {this.playerStats.points} Rebounds: {this.playerStats.rebounds} Assists: {this.playerStats.assists} Stl: {this.playerStats.steals} Blocks: {this.playerStats.blocks} Tov: {this.playerStats.turnovers}";
        }

        public double CalculateFantasyPoints()
        {
            return this.playerStats.CalculateFantasyPoints();
        }
    }
}
