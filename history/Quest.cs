using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Game02Editor.history
{
    internal class Quest
    {
        public string name = "";
        public string map = "";
        public List<string> taskData = new List<string>();

        public Quest(string fileData)
        {
            StreamReader streamReader = new StreamReader(fileData);
            string line = "";
            int cursor = 0;
            int countOfData = 1;
            while ((line = streamReader.ReadLine()) != null)
            {
                if(cursor == 0)
                {
                    name = line;
                    cursor++;
                }
                else if (cursor == 1)
                {
                    map = line;
                    cursor++;
                }
                else if (cursor == 2)
                {
                    countOfData = int.Parse(line);
                    cursor++;
                } else
                {
                    taskData.Add(line);
                }
            }
            streamReader.Close();
        }

        internal void saveToFile(string name)
        {
            StreamWriter streamReader = new StreamWriter(name);

            streamReader.WriteLine(this.name);
            streamReader.WriteLine(map);
            streamReader.WriteLine(taskData.Count);
            foreach(string el in taskData)
            {
                streamReader.WriteLine(el);
            }
            streamReader.Close();

        }
    }
}
