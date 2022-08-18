using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Game02Editor.world
{
    public class CharSpawner

    {
        public int startAI;

        public int buy, sell, teach, spellChanging, oneTimer, enebled;
        public string sellListID, teachListID, script;

        public int walkLeft, walkRight, walkTop, walkBot, walkWaitMin, walkWaitMax;
        public int[][] patrolPoints = { };

        public string playerID, name, texture;
        public Bitmap image;
        public int x, y;
        public string localID;

        public CharSpawner(Char copy, int X, int Y)
        {
            playerID = copy.ID;
            name = copy.name;
            x = X;
            y = Y;
            texture = copy.texture;


            image = new Bitmap(32, 32);
            Bitmap copyImage = new Bitmap("res/tex/chars/" + texture + ".png");
            for (int i = 0; i < 32; i++)
            {
                for (int j = 0; j < 32; j++)
                {
                    image.SetPixel(i, j, copyImage.GetPixel(1 * 32 + i, 0 * 32 + j));
                }
            }
        }

        public CharSpawner(string id, string localID, int X, int Y, string data)
        {
            Char copy = Char.getItemByID(id);
            playerID = copy.ID;
            name = copy.name;
            x = X;
            y = Y;
            texture = copy.texture;
            this.localID = localID;
            image = new Bitmap(32, 32);
            Bitmap copyImage = new Bitmap("res/tex/chars/" + texture + ".png");
            for (int i = 0; i < 32; i++)
            {
                for (int j = 0; j < 32; j++)
                {
                    image.SetPixel(i, j, copyImage.GetPixel(1 * 32 + i, 0 * 32 + j));
                }
            }

            string[] split = data.Split(',');
            startAI = int.Parse(split[0]);

            walkLeft = int.Parse(split[1]);
            walkTop = int.Parse(split[2]);
            walkRight = int.Parse(split[3]);
            walkBot = int.Parse(split[4]);
            walkWaitMin = int.Parse(split[5]);
            walkWaitMax = int.Parse(split[6]);

            buy = int.Parse(split[7]);
            sell = int.Parse(split[8]);
            teach = int.Parse(split[9]);
            spellChanging = int.Parse(split[10]);
            oneTimer = int.Parse(split[11]);
            enebled = int.Parse(split[12]);

            sellListID = split[13];
            teachListID = split[14];
            script = split[15];

            int count = int.Parse(split[16]);

            List<int[]> point = new List<int[]>();
            for(int i = 0; i < count; i++)
            {
                int[] vals = { int.Parse(split[17 + i * 3]), int.Parse(split[17 + i * 3 + 1]), int.Parse(split[17 + i * 3 + 2]) };
                point.Add(vals);
            }
            patrolPoints = point.ToArray();
        }
    }
}
