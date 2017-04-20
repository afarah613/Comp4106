using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FinalProject
{
    public class PlayerStats
    {
        public long gamePlayed;
        public double points;
        public double rebounds;
        public double assists;
        public double steals;
        public double blocks;
        public double turnovers;
        public double fieldGoalsAttempted;
        public double fieldGoalsMade;

        private const double pointsValue = 0.5;
        private const int reboundsValue = 2;
        private const int assistsValue = 3;
        private const int stealsValue = 4;
        private const int blocksValue = 5;
        private const int turnoversValue = -2;

        public PlayerStats(
            long gamePlayed,
            double points,
            double rebounds,
            double assists,
            double steals,
            double blocks,
            double turnovers,
            double fieldGoalsAttempted,
            double fieldGoalsMade)
        {
            this.gamePlayed = gamePlayed;
            this.points = points;
            this.rebounds = rebounds;
            this.assists = assists;
            this.steals = steals;
            this.blocks = blocks;
            this.turnovers = turnovers;
            this.fieldGoalsAttempted = fieldGoalsAttempted;
            this.fieldGoalsMade = fieldGoalsMade;
        }

        public double CalculateFantasyPoints()
        {
            double score = 0;
            score += this.points * pointsValue;
            score += this.rebounds * reboundsValue;
            score += this.assists * assistsValue;
            score += this.steals * stealsValue;
            score += this.blocks * blocks;
            score += this.turnovers * turnovers;

            return score;
        }
    }
}
