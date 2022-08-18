using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Game02Editor.history
{
    internal class Dialog
    {
        private string name;
        private string initCond = "";
        private string finishCond = "";
        private List<string> lines = new List<string>();

        public Dialog(string name, string initCond, string finishCond, List<string> lines)
        {
            this.name = name;
            this.initCond = initCond;
            this.finishCond = finishCond;
            this.lines = lines;
        }

        public Dialog()
        {
        }

        internal string getConds()
        {
            return initCond;
        }

        internal string getResult()
        {
            return finishCond;
        }

        internal List<string> getLines()
        {
            return lines;
        }

        internal string getName()
        {
            return name;
        }

        internal void setCond(string cond)
        {
            this.initCond = cond;
        }
        internal void setResults(string results)
        {
            this.finishCond = results;
        }

        internal void setName(string text)
        {
            name = text;
        }
    }
}
