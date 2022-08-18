using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Game02Editor.world
{
    public class Item
    {
        public static List<Item> items = new List<Item>();

        public string ID, name, texture;
        public int partX, partY;
        public int weight, cost;
        public int classs, type;
        public Bitmap image;
        public int x, y;
        public string localID;

        public int damage, damageType, interval, ammo, distant, balanceDamage;

        public int damageReduction, staminaDamageReduction;

        public int defensePierce, defenseSlash, defenseChop, defenseStrike, defenseFire, defenseIce, defenseShock, defenseAcid, defensePoison, defenseMental, defenseNegative;

        public int effectID, effectPower, effectTime;

        public int ammoIndex, radius;

        public int whatToRaise, raiseMult, staminaNeed, difficult;
        public string label;

        public string desc = "";
        public Item()
        {

        }

        public static Item getItemByID(string id)
        {
            foreach (Item el in items)
            {
                if (el.ID.Equals(id))
                    return el;
            }
            return null;
        }
        public Item(Item copy, int X, int Y)
        {
            ID = copy.ID;
            name = copy.name;
            x = X;
            y = Y;
            texture = copy.texture;
            partX = copy.partX;
            partY = copy.partY;

            image = new Bitmap(32, 32);
            Bitmap copyImage = new Bitmap("res/tex/items/" + texture + ".png");
            for (int i = 0; i < 32; i++)
            {
                for (int j = 0; j < 32; j++)
                {
                    image.SetPixel(i, j, copyImage.GetPixel(partX * 32 + i, partY * 32 + j));
                }
            }
        }

        public Item(string data, int i)
        {
            string[] split = data.Split(';');
            ID = split[0];
            texture = split[1];
            partX = int.Parse(split[2]);
            partY = int.Parse(split[3]);
            name = split[4];
            classs = int.Parse(split[5]);
            type = int.Parse(split[6]);
            weight = int.Parse(split[7]);
            cost = int.Parse(split[8]);
           
            if(i == 0)
            {
                damage = int.Parse(split[9]); damageType = int.Parse(split[10]); interval = int.Parse(split[11]); ammo = int.Parse(split[12]); 
                distant = int.Parse(split[13]); balanceDamage = int.Parse(split[14]);
            }
            if (i == 1)
            {
                damageReduction = int.Parse(split[9]); staminaDamageReduction = int.Parse(split[10]);
            }
            if (i == 2)
            {
                defensePierce = int.Parse(split[9]); defenseSlash = int.Parse(split[10]); defenseChop = int.Parse(split[11]); defenseStrike = int.Parse(split[12]);
                defenseFire = int.Parse(split[13]); defenseIce = int.Parse(split[14]); defenseShock = int.Parse(split[15]); defenseAcid = int.Parse(split[16]);
                defensePoison = int.Parse(split[17]); defenseMental = int.Parse(split[18]); defenseNegative = int.Parse(split[19]);
            }
            if (i == 3)
            {
                effectID = int.Parse(split[9]); effectPower = int.Parse(split[10]); effectTime = int.Parse(split[11]);
                desc = split[12];
            }
            if (i == 4)
            {
                ammoIndex = int.Parse(split[9]); effectID = int.Parse(split[10]); effectPower = int.Parse(split[11]); effectTime = int.Parse(split[12]);
                ammo = int.Parse(split[13]); distant = int.Parse(split[14]); radius = int.Parse(split[15]);
                desc = split[16];
            }
            if (i == 5)
            {
                whatToRaise = int.Parse(split[9]); raiseMult = int.Parse(split[10]); staminaNeed = int.Parse(split[11]); difficult = int.Parse(split[12]);
                desc = split[13]; label = split[14];
            }
            if (i == 6)
            {
                desc = split[9];
            }
        }
        static string[] datas = { "res/data/items/weapons.csv" , "res/data/items/shields.csv" , "res/data/items/armours.csv" , "res/data/items/talismans.csv" ,
        "res/data/items/uses.csv","res/data/items/runes.csv","res/data/items/misc.csv"};
        internal static void loadItems()
        {
            for (int i = 0; i < datas.Length; i++)
            {
                StreamReader streamReader = new StreamReader(datas[i]);
                string line;
                while ((line = streamReader.ReadLine()) != null)
                {
                    Item item = new Item(line, i);
                    items.Add(item);
                }
                streamReader.Close();
            }
        }
    

        public static void addItem(Item item)
        {
            
            items.Add(item);
            string lineGeneral = item.ID + ";" + item.texture + ";" + item.partX + ";" + item.partY +";"+item.name+ ";" + item.classs + ";" + item.type + ";" + item.weight + ";" + item.cost + ";";

            if (item.classs <= 3)
            { 
                StreamWriter streamReader = new StreamWriter("res/data/items/weapons.csv",true);
                string special = item.damage + ";" + item.damageType + ";" + item.interval + ";" + item.ammo + ";" + item.distant + ";" + item.balanceDamage;
                streamReader.WriteLine();
                streamReader.Write(lineGeneral+special);
                streamReader.Close();
            }
            if (item.classs == 4)
            {
                StreamWriter streamReader = new StreamWriter("res/data/items/shields.csv", true);
                string special = (item.damageReduction) + ";" + item.staminaDamageReduction;
                streamReader.WriteLine();
                streamReader.Write(lineGeneral + special);
                streamReader.Close();
            }
            if (item.classs == 5)
            {
                StreamWriter streamReader = new StreamWriter("res/data/items/armours.csv", true);
                string special = item.defensePierce + ";" + item.defenseSlash + ";" + item.defenseChop + ";" + item.defenseStrike + ";" + item.defenseFire + ";" + item.defenseIce+";"+
                    item.defenseShock + ";" + item.defenseAcid + ";" + item.defensePoison + ";" + item.defenseMental + ";" + item.defenseNegative + ";";
                streamReader.WriteLine();
                streamReader.Write(lineGeneral + special);
                streamReader.Close();
            }
            if (item.classs == 6)
            {
                StreamWriter streamReader = new StreamWriter("res/data/items/talismans.csv", true);
                string special = item.effectID + ";" + item.effectPower + ";" + item.effectTime + ";" + item.desc;
                streamReader.WriteLine();
                streamReader.Write(lineGeneral + special);
                streamReader.Close();
            }
            if (item.classs == 7)
            {
                StreamWriter streamReader = new StreamWriter("res/data/items/uses.csv", true);
                string special = item.ammoIndex+";"+item.effectID + ";" + item.effectPower + ";" + item.effectTime +";"+item.ammo+ ";" + item.distant + ";" + item.radius + ";" + item.desc;
                streamReader.WriteLine();
                streamReader.Write(lineGeneral + special);
                streamReader.Close();
            }
            if (item.classs == 8)
            {
                StreamWriter streamReader = new StreamWriter("res/data/items/runes.csv", true);
                string special = item.whatToRaise + ";" + item.raiseMult + ";" + item.staminaNeed + ";" + item.difficult + ";" + item.desc + ";" + item.label;
                streamReader.WriteLine();
                streamReader.Write(lineGeneral + special);
                streamReader.Close();
            }
            if (item.classs == 9)
            {
                StreamWriter streamReader = new StreamWriter("res/data/items/misc.csv", true);
                string special = item.desc;
                streamReader.WriteLine();
                streamReader.Write(lineGeneral + special);
                streamReader.Close();
            }
        }

        internal static List<Item> get(int v)
        {
            List<Item> result = new List<Item>();
            foreach (Item el in items)
            {
                if (v == -1)
                {
                    result.Add(el);
                }
                else if (v <= 3 && el.classs <= 3)
                {
                    result.Add(el);
                } 
                else if (v > 0 && el.classs == v)
                {
                    result.Add(el);
                }
            }
            return result;
        }

        internal static void upDate()
        {
            string lineGeneral, special = "";
            List<StreamWriter> streams = new List<StreamWriter>();
            for(int i = 0; i < datas.Length; i++)
            {
                StreamWriter streamReader = new StreamWriter(datas[i]);
                streams.Add(streamReader);
            }
            for(int i = 3; i <= 9; i++)
            {
                List<Item> data = get(i);
                foreach (Item item in data)
                {
                    lineGeneral = item.ID + ";" + item.texture + ";" + item.partX + ";" + item.partY + ";" + item.name + ";" + item.classs + ";" + item.type + ";" + item.weight + ";" + item.cost + ";";
                    if(i==3)
                        special = item.damage + ";" + item.damageType + ";" + item.interval + ";" + item.ammo + ";" + item.distant + ";" + item.balanceDamage;
                    if(i == 4)
                        special = (item.damageReduction) + ";" + item.staminaDamageReduction;
                    if(i == 5)
                        special = item.defensePierce + ";" + item.defenseSlash + ";" + item.defenseChop + ";" + item.defenseStrike + ";" + item.defenseFire + ";" + item.defenseIce + ";" +
                    item.defenseShock + ";" + item.defenseAcid + ";" + item.defensePoison + ";" + item.defenseMental + ";" + item.defenseNegative + ";";
                    if(i == 6)
                        special = item.effectID + ";" + item.effectPower + ";" + item.effectTime + ";" + item.desc;
                    if(i == 7)
                        special = item.ammoIndex + ";" + item.effectID + ";" + item.effectPower + ";" + item.effectTime + ";" + item.ammo + ";" + item.distant + ";" + item.radius + ";" + item.desc;
                    if(i == 8)
                        special = item.whatToRaise + ";" + item.raiseMult + ";" + item.staminaNeed + ";" + item.difficult + ";" + item.desc + ";" + item.label;
                    if(i == 9)
                        special = item.desc;

                    streams[i - 3].WriteLine(lineGeneral + special);
                }

            }
            foreach (StreamWriter el in streams)
            {
                el.Close();
            }
        }
    }
}
