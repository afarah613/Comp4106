using System;
using System.Collections.Generic;
using System.Data.SQLite;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace FinalProject
{
    public class Program
    {
        static void Main(string[] args)
        {
            var teams = new string[] { "MIA", "NYK", "OKC" };
            var players = GetAllPlayers(teams);
            var lineup = new PlayerLineup(players);
            var lineups = ProductionSystemSolver.AStarSearch(lineup);
        }

        public static List<Player> GetAllPlayers(IEnumerable<String> teams)
        {
            var m_dbConnection = new SQLiteConnection(@"Data Source=C:\Users\Ali\Documents\Comp4106\FinalProject\FinalProject\Database\NBA.sqlite;Version=3;");
            m_dbConnection.Open();

            var players = new List<Player>();

            foreach (var team in teams)
            {
                string sql = $"SELECT * FROM season16 WHERE team LIKE '%{team}%'";
                SQLiteCommand command = new SQLiteCommand(sql, m_dbConnection);
                SQLiteDataReader reader = command.ExecuteReader();

                while (reader.Read())
                {
                    var playerStats = new PlayerStats(
                            (long)reader["G"],
                            (double)reader["Points"],
                            (double)reader["TRB"],
                            (double)reader["AST"],
                            (double)reader["STL"],
                            (double)reader["BLK"],
                            (double)reader["TOV"],
                            (double)reader["FGA"],
                            (double)reader["FG"]);

                    var player = new Player(
                        (String)reader["Player"],
                         (String)reader["Team"],
                         (String)reader["Pos"],
                         playerStats);
                    players.Add(player);

                }
            }

            m_dbConnection.Close();
            return players;
        }
    }
}
