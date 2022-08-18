using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Game02Editor.history
{
    internal class DialogData
    {
        Dictionary<string,Dialog> dialogs = new Dictionary<string, Dialog>();

        public Dictionary<string, Dialog> getDialogsFull()
        {
            return dialogs;
        }

        public DialogData()
        {
        }

        public DialogData(string inFile)
        {
            StreamReader streamReader = new StreamReader(inFile);
            int lineStatus = 0;
            string line;
            string name = "";
            string initCond = "";
            string finishCond = "";
            int stringsCount = 0;
            List<string> lines = new List<string>();
            while ((line = streamReader.ReadLine()) != null)
            {
                if(lineStatus == 0)
                {
                    name = line;
                    lineStatus++;
                }
                else if (lineStatus == 1)
                {
                    initCond = line;
                    lineStatus++;
                }
                else if (lineStatus == 2)
                {
                    finishCond = line;
                    lineStatus++;
                }
                else if (lineStatus == 3)
                {
                    stringsCount = int.Parse(line);
                    lineStatus++;
                    lines = new List<string>();
                }
                else if (lineStatus == 4)
                {
                    lines.Add(line);
                    stringsCount--;
                    if(stringsCount == 0)
                    {
                        lineStatus = 0;
                        Dialog di = new Dialog(name, initCond, finishCond, lines);
                        dialogs.Add(name,di);
                    }
                }
            }
            streamReader.Close();
        }

        internal Dialog getDialog(string text)
        {
            return dialogs[text];
        }

        internal object[] getDialogs()
        {
            return dialogs.Keys.ToArray();
        }

        internal void save(string file)
        {
            StreamWriter streamReader = new StreamWriter("res/data/history/dialogs/" + file + ".csv");


            foreach(Dialog el in dialogs.Values)
            {
                streamReader.WriteLine(el.getName());
                streamReader.WriteLine(el.getConds());
                streamReader.WriteLine(el.getResult());
                streamReader.WriteLine(el.getLines().Count);
                foreach(string ele in el.getLines())
                {
                    streamReader.WriteLine(ele);
                }

            }
            streamReader.Close();
        }
    }
}
