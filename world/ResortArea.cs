using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Game02Editor.world
{
    public class ResortArea
    {
        public int x, y, size;
        public string id, name;
        public ResortArea(string ID, string Name,int X, int Y, int Size)
        {
            x = X;
            y = Y;
            size = Size;
            id = ID;
            name = Name;
        }
    }
}
