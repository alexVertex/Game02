using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Game02Editor.world
{
    public class Char
    {
        public static List<Char> chars = new List<Char>();
        


        public string ID, name, texture, voice, fraction;
        public int playerStatus;
        public int health, stamina, armor, poise, speedMove, speedAttack, visionDistant;
        public int damage, damageType, shieldReduct, shieldStability;
        public int defPierce, defSlash, defChop, defStrike, defFire, defIce, defShock, defAcid, defPoison, defMental, defNegative;
        public List<string> itemsLoot = new List<string>();
        public int goldMin, goldMax;

        public Bitmap image;
        public int x, y;
        public string localID;
        public Char()
        {

        }

        public static Char getItemByID(string id)
        {
            foreach (Char el in chars)
            {
                if (el.ID.Equals(id))
                    return el;
            }
            return null;
        }

        public Char(Char copy, int X, int Y)
        {
            ID = copy.ID;
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

        public Char(string data)
        {
            string[] split = data.Split(';');
            ID = split[0];
            texture = split[1];
            name = split[2];
            voice = split[3];
            fraction = split[4];
            playerStatus = int.Parse(split[5]);

            health = int.Parse(split[6]);
            stamina = int.Parse(split[7]);
            armor = int.Parse(split[8]);
            poise = int.Parse(split[9]);
            speedMove = int.Parse(split[10]);
            speedAttack = int.Parse(split[11]);
            visionDistant = int.Parse(split[12]);

            damage = int.Parse(split[13]);
            damageType = int.Parse(split[14]);
            shieldReduct = int.Parse(split[15]);
            shieldStability = int.Parse(split[16]);

            defPierce = int.Parse(split[17]);
            defSlash = int.Parse(split[18]);
            defChop = int.Parse(split[19]);
            defStrike = int.Parse(split[20]);
            defFire = int.Parse(split[21]);
            defIce = int.Parse(split[22]);
            defShock = int.Parse(split[23]);
            defAcid = int.Parse(split[24]);
            defPoison = int.Parse(split[25]);
            defMental = int.Parse(split[26]);
            defNegative = int.Parse(split[27]);

            goldMin = int.Parse(split[28]);
            goldMax = int.Parse(split[29]);

            int lootCount = int.Parse(split[30]);
            for(int i = 0; i < lootCount; i++)
            {
                itemsLoot.Add(split[31 + i]);
            }
        }

        internal static void loadChars()
        {
            {
                StreamReader streamReader = new StreamReader("res/data/players/chars.csv");
                string line;
                while ((line = streamReader.ReadLine()) != null)
                {
                    Char item = new Char(line);
                    chars.Add(item);
                }
                streamReader.Close();
            }
        }

        public static void addItem(Char item)
        {

            chars.Add(item);
            string lineGeneral = item.ID + ";" + 
                item.texture + ";" + 
                item.name + ";" + 
                item.voice + ";" + 
                item.fraction + ";" + 
                item.playerStatus + ";" + 
                item.health + ";" + 
                item.stamina + ";" + 
                item.armor + ";" +
                item.poise + ";" +
                item.speedMove + ";" +
                item.speedAttack + ";" +
                item.visionDistant + ";" +
                item.damage + ";" +
                item.damageType + ";" +
                item.shieldReduct + ";" +
                item.shieldStability + ";" +
                item.defPierce + ";" +
                item.defSlash + ";" +
                item.defChop + ";" +
                item.defStrike + ";" +
                item.defFire + ";" +
                item.defIce + ";" +
                item.defShock + ";" +
                item.defAcid + ";" +
                item.defPoison + ";" +
                item.defMental + ";" +
                item.defNegative + ";"+
                item.goldMin + ";"+
                item.goldMax + ";" +
                item.itemsLoot.Count
                ;
            foreach(string el in item.itemsLoot)
            {
                lineGeneral += ";" + el;
            }

            StreamWriter streamReader = new StreamWriter("res/data/players/chars.csv", true);
            streamReader.WriteLine();
            streamReader.Write(lineGeneral);
            streamReader.Close();

        }

        internal static void upDate()
        {
            StreamWriter streamReader = new StreamWriter("res/data/players/chars.csv");
            foreach(Char item in chars)
            {
                string lineGeneral = item.ID + ";" +
                item.texture + ";" +
                item.name + ";" +
                item.voice + ";" +
                item.fraction + ";" +
                item.playerStatus + ";" +
                item.health + ";" +
                item.stamina + ";" +
                item.armor + ";" +
                item.poise + ";" +
                item.speedMove + ";" +
                item.speedAttack + ";" +
                item.visionDistant + ";" +
                item.damage + ";" +
                item.damageType + ";" +
                item.shieldReduct + ";" +
                item.shieldStability + ";" +
                item.defPierce + ";" +
                item.defSlash + ";" +
                item.defChop + ";" +
                item.defStrike + ";" +
                item.defFire + ";" +
                item.defIce + ";" +
                item.defShock + ";" +
                item.defAcid + ";" +
                item.defPoison + ";" +
                item.defMental + ";" +
                item.defNegative + ";" +
                item.goldMin + ";" +
                item.goldMax + ";" +
                item.itemsLoot.Count
                ;
                foreach (string el in item.itemsLoot)
                {
                    lineGeneral += ";" + el;
                }
                streamReader.WriteLine(lineGeneral);

            }
            streamReader.Close();

        }
    }
}
