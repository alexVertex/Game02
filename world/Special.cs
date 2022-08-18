using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Game02Editor.world
{
    public class Special
    {
        public static List<Special> specials = new List<Special>();

        public List<string> chestLoot = new List<string>();
        public int chestGold;

        public string script;

        public int type = 0;
        public Bitmap image;
        public string localID;
        public int x, y;

        public string ID, texture = "test";
        public int startX = 0, startY = 0, sizeX = 32, sizeY = 32;
        public int transparentPart;
        public bool isFloor;
        public int lockLevel;

        public int animSpeed = 1, animFrames = 1;

        public bool isLoot;

        public string soundName;
        public int hearRadius;
        public Special()
        {

        }

        public static Special getSpecialByID(string id)
        {
            foreach (Special el in specials)
            {
                if (el.ID.Equals(id))
                    return el;
            }
            return null;
        }
        static string[] textures = { "res/tex/activators/", "res/tex/animTiles/", "res/tex/chests/", "res/tex/doors/", "res/tex/", "res/tex/objects/" };
        public Special(Special copy, int X, int Y)
        {
            ID = copy.ID;
            x = X;
            y = Y;
            texture = copy.texture;
            chestGold = copy.chestGold;
            chestLoot.AddRange(copy.chestLoot);
    
            type = copy.type;
            if (type != 5)
            {
                image = new Bitmap(copy.sizeX, copy.sizeY);
                Bitmap copyImage = new Bitmap(textures[copy.type] + texture + ".png");
                for (int i = 0; i < copy.sizeX; i++)
                {
                    for (int j = 0; j < copy.sizeY; j++)
                    {
                        image.SetPixel(i, j, copyImage.GetPixel(copy.startX * 32 + i, copy.startY * 32 + j));
                    }
                }
            }
            else
            {
                image = new Bitmap(copy.sizeX * 32, copy.sizeY * 32);
                Bitmap copyImage = new Bitmap(textures[copy.type] + texture + ".png");
                for (int i = 0; i < copy.sizeX*32; i++)
                {
                    for (int j = 0; j < copy.sizeY*32; j++)
                    {
                        image.SetPixel(i, j, copyImage.GetPixel(copy.startX * 32 + i, copy.startY * 32 + j));
                    }
                }
                sizeX = copy.sizeX * 32;
                sizeY = copy.sizeY * 32;
            }
            
        }

        public Special(string data, int i)
        {
            string[] split = data.Split(';');
            ID = split[0];
            script = split[1];
            type = i;

            if (i == 0)
            {
                texture = split[2]; startX = int.Parse(split[3]); isFloor = bool.Parse(split[4]); lockLevel = int.Parse(split[5]);
                sizeY = 128;
            }
            if (i == 1)
            {
                split[3] = split[3].Replace('.', ',');
                texture = split[2]; animSpeed = (int)(double.Parse(split[3])*100); animFrames = int.Parse(split[4]);
            }
            if (i == 2)
            {
                texture = split[2]; startX = int.Parse(split[3]); lockLevel = int.Parse(split[4]); isLoot = bool.Parse(split[5]);
            }
            if (i == 3)
            {
                texture = split[2]; lockLevel = int.Parse(split[3]); 
            }
            if (i == 4)
            {
                soundName = split[2]; hearRadius = int.Parse(split[3]);
            }
            if (i == 5)
            {
                texture = split[2]; startX = int.Parse(split[3]); startY = int.Parse(split[4]); sizeX = int.Parse(split[5]); sizeY = int.Parse(split[6]); transparentPart = int.Parse(split[7]);
            }
        }

        static string[] datas = { "res/data/objects/activators.csv" , "res/data/objects/animTiles.csv" , "res/data/objects/chests.csv" , "res/data/objects/doors.csv" ,
        "res/data/objects/soundSources.csv","res/data/objects/structures.csv"};

        internal static void loadItems()
        {
            for (int i = 0; i < datas.Length; i++)
            {
                StreamReader streamReader = new StreamReader(datas[i]);
                string line;
                while ((line = streamReader.ReadLine()) != null)
                {
                    Special item = new Special(line, i);
                    specials.Add(item);
                }
                streamReader.Close();
            }
        }

        public static void addItem(Special item)
        {

            specials.Add(item);
            string lineGeneral = item.ID + ";"+item.script+";";

            if (item.type == 0)
            {
                StreamWriter streamReader = new StreamWriter("res/data/objects/activators.csv", true);
                string special = item.texture + ";" + item.startX + ";" + item.isFloor + ";" + item.lockLevel;
                streamReader.WriteLine();
                streamReader.Write(lineGeneral + special);
                streamReader.Close();
            }
            if (item.type == 1)
            {
                StreamWriter streamReader = new StreamWriter("res/data/objects/animTiles.csv", true);
                string special = item.texture + ";" + item.animSpeed/100.0 + ";" + item.animFrames;
                streamReader.WriteLine();
                streamReader.Write(lineGeneral + special);
                streamReader.Close();
            }
            if (item.type == 2)
            {
                StreamWriter streamReader = new StreamWriter("res/data/objects/chests.csv", true);
                string special = item.texture + ";" + item.startX + ";" + item.lockLevel + ";" + item.isLoot;
                streamReader.WriteLine();
                streamReader.Write(lineGeneral + special);
                streamReader.Close();
            }
            if (item.type == 3)
            {
                StreamWriter streamReader = new StreamWriter("res/data/objects/doors.csv", true);
                string special = item.texture + ";" + item.lockLevel;
                streamReader.WriteLine();
                streamReader.Write(lineGeneral + special);
                streamReader.Close();
            }
            if (item.type == 4)
            {
                StreamWriter streamReader = new StreamWriter("res/data/objects/soundSources.csv", true);
                string special = item.soundName + ";" + item.hearRadius;
                streamReader.WriteLine();
                streamReader.Write(lineGeneral + special);
                streamReader.Close();
            }
            if (item.type == 5)
            {
                StreamWriter streamReader = new StreamWriter("res/data/objects/structures.csv", true);
                string special = item.texture + ";" + item.startX + ";" + item.startY + ";" + item.sizeX + ";" + item.sizeY + ";" + item.transparentPart;
                streamReader.WriteLine();
                streamReader.Write(lineGeneral + special);
                streamReader.Close();
            }
        }

        internal static List<Special> get(int v)
        {
            List<Special> result = new List<Special>();
            foreach (Special el in specials)
            {
                if (v == -1)
                {
                    result.Add(el);
                }
                else if (v >= 0 && el.type == v)
                {
                    result.Add(el);
                }
            }
            return result;
        }

        internal static void update()
        {
            string lineGeneral, special = "";
            List<StreamWriter> streams = new List<StreamWriter>();
            for (int i = 0; i < datas.Length; i++)
            {
                StreamWriter streamReader = new StreamWriter(datas[i]);
                streams.Add(streamReader);
            }
            for (int i = 0; i < 6; i++)
            {
                List<Special> data = get(i);
                foreach (Special item in data)
                {
                    lineGeneral = item.ID + ";" + item.script + ";";
                    if(item.type == 0)
                        special = item.texture + ";" + item.startX + ";" + item.isFloor + ";" + item.lockLevel;
                    if (item.type == 1)
                        special = item.texture + ";" + item.animSpeed / 100.0 + ";" + item.animFrames;
                    if (item.type == 2)
                        special = item.texture + ";" + item.startX + ";" + item.lockLevel + ";" + item.isLoot;
                    if (item.type == 3)
                        special = item.texture + ";" + item.lockLevel;
                    if (item.type == 4)
                        special = item.soundName + ";" + item.hearRadius;
                    if (item.type == 5)
                        special = item.texture + ";" + item.startX + ";" + item.startY + ";" + item.sizeX + ";" + item.sizeY + ";" + item.transparentPart;

                    streams[i].WriteLine(lineGeneral + special);

                }
            }
            foreach (StreamWriter el in streams)
            {
                el.Close();
            }
        }

        internal void setLoot(string lootData)
        {
            string[] split = lootData.Split(',');

            chestGold = int.Parse(split[0]);
            int lootCount = int.Parse(split[1]);

            for (int i = 0; i < lootCount; i++)
            {
                chestLoot.Add(split[2 + i]);
            }
        }
    }
}
