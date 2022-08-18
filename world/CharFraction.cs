using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Game02Editor.world
{
    public class CharFraction
    {
        public Dictionary<string, int> attitude = new Dictionary<string, int>();
        public string name;
        public CharFraction(string name)
        {
            this.name = name;
        }
    }
}
