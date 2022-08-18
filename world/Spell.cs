using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Game02Editor.world
{



    public class Spell
    {
        public static List<Spell> items = new List<Spell>();


        public string ID, name, texture, desc;
        public int partX, partY, sort, type;
        public int cost;
        public int projectileID, effectID, effectPower, effectTime;
        public int targetType, distant, radius;
        public int staminaNeed, difficult;

        public Bitmap image;

        public Spell()
        {

        }

        public static Spell getItemByID(string id)
        {
            foreach (Spell el in items)
            {
                if (el.ID.Equals(id))
                    return el;
            }
            return null;
        }

        public Spell(string data)
        {
            string[] split = data.Split(';');
            ID = split[0];
            texture = split[1];
            partX = int.Parse(split[2]);
            partY = int.Parse(split[3]);
            name = split[4];
            cost = int.Parse(split[5]);
            sort = int.Parse(split[6]);
            type = int.Parse(split[7]);
            projectileID = int.Parse(split[8]);
            effectID = int.Parse(split[9]);
            effectPower = int.Parse(split[10]);
            effectTime = int.Parse(split[11]);
            targetType = int.Parse(split[12]);
            staminaNeed = int.Parse(split[13]);
            difficult = int.Parse(split[14]);
            distant = int.Parse(split[15]);
            radius = int.Parse(split[16]);
            desc = split[17];   
        }

        internal static void loadSpells()
        {
            StreamReader streamReader = new StreamReader("res/data/items/spells.csv");
            string line;
            while ((line = streamReader.ReadLine()) != null)
            {
                Spell item = new Spell(line);
                items.Add(item);
            }
            streamReader.Close();
        }
        public static void addItem(Spell item)
        {

            items.Add(item);

            StreamWriter streamReader = new StreamWriter("res/data/items/spells.csv", true);

            string special = item.ID + ";" + item.texture + ";" + item.partX + ";" + item.partY + ";" + item.name + ";"+item.cost+";" + item.sort + ";" + item.type
                 + ";" + item.projectileID + ";" + item.effectID + ";" + item.effectPower + ";" + item.effectTime + ";" + item.targetType + ";" + item.staminaNeed + ";" + item.difficult
                 + ";" + item.distant + ";" + item.radius + ";" + item.desc;
            streamReader.WriteLine();
            streamReader.Write(special);
            streamReader.Close();

        }
        internal static List<Spell> get(int v)
        {
            List<Spell> result = new List<Spell>();
            foreach (Spell el in items)
            {
                if (v == -1)
                {
                    result.Add(el);
                }
            }
            return result;
        }

        internal static void upDate()
        {
            StreamWriter streamReader = new StreamWriter("res/data/items/spells.csv");
            foreach (Spell item in items)
            {
                string special = item.ID + ";" + item.texture + ";" + item.partX + ";" + item.partY + ";" + item.name + ";" + item.cost + ";" + item.sort + ";" + item.type
                  + ";" + item.projectileID + ";" + item.effectID + ";" + item.effectPower + ";" + item.effectTime + ";" + item.targetType + ";" + item.staminaNeed + ";" + item.difficult
                  + ";" + item.distant + ";" + item.radius + ";" + item.desc;

                streamReader.WriteLine(special);

            }
            streamReader.Close();

        }
    }

}
