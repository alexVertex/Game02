using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Game02Editor.world
{
    internal class Tile
    {
        static List<Tile> tilesData = new List<Tile>();
        public Bitmap image;

        public Tile(string data)
        {
            string[] split = data.Split(';');
            string name = split[0];
            int x = int.Parse(split[1]);
            int y = int.Parse(split[2]);
            image = new Bitmap(32, 32);
            Bitmap copy = new Bitmap("res/tex/tiles/" + name + ".png");
            for (int i = 0; i < 32; i++)
            {
                for (int j = 0; j < 32; j++)
                {
                    image.SetPixel(i, j, copy.GetPixel(x * 32 + i, y * 32 + j));
                }
            }
        }
        internal static void addTile(string data)
        {
            Tile t = new Tile(data);
            tilesData.Add(t);
        }
        internal static Tile getTile(int cur)
        {
            return tilesData[cur];
        }
    }
}
