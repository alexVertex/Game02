using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Game02Editor.world
{
    internal class Merch
    {

        public static Dictionary<string, Merch> data = new Dictionary<string, Merch>();

        public static void loadAllMerch()
        {
            StreamReader streamReader = new StreamReader("res/data/players/trades.csv");

            string line;
            while ((line = streamReader.ReadLine()) != null)
            {
                Merch item = new Merch(line);
                data[item.name] = item;
            }

            streamReader.Close();
        }

        public string name;
        public List<string> ids = new List<string>();
        public bool isMagic;

        public Merch(string line)
        {
            string[] split = line.Split(';');
            name = split[0];
            for(int i = 1; i < split.Length-1; i++)
            {
                ids.Add(split[i]);
            }
        }

        internal static void saveAllMerch()
        {
            StreamWriter streamReader = new StreamWriter("res/data/players/trades.csv");

            foreach(string key in data.Keys)
            {
                Merch one = data[key];
                string line = one.name + ";";
                foreach (string item in one.ids)
                {
                    line += item+";";
                }
                streamReader.WriteLine(line);
            }

            streamReader.Close();
        }
    }
}
