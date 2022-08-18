using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Game02Editor.world
{
    public class Map
    {
        public string id;
        public string name;
        public string minimap;
        public int camX = 0;
        public int camY = 0;
        public int sizeX = 22;
        public int sizeY = 22;

        public string explore, battle;

        List<int> tilesCodes = new List<int>();
        List<int> passment = new List<int>();

        int localNextItem = 0; List<Item> items = new List<Item>();
        int localNextActi = 0; List<Special> activators = new List<Special>();
        int localNextAnim = 0; List<Special> animTiles = new List<Special>();
        int localNextChes = 0; public List<Special> chests = new List<Special>();
        int localNextDoor = 0; List<Special> doors = new List<Special>();
        int localNextSoud = 0; List<Special> sounds = new List<Special>();
        int localNextStrc = 0; List<Special> structures = new List<Special>();

        public List<ResortArea> resortAreas = new List<ResortArea>();

        int localNextChar = 0; public List<CharSpawner> spawners = new List<CharSpawner>();

        public Map(string ID, string Name, string Minimap, int x, int y, string Explore, string Battle)
        {
            tilesCodes = new List<int>();
            for(int i = 0; i < x * y; i++)
            {
                tilesCodes.Add(0);
                passment.Add(1);
            }
            sizeX = x;
            sizeY = y;
            id = ID;
            name = Name;
            minimap = Minimap;
            explore = Explore;
            battle = Battle;
        }

        public void setTile(int x, int y, int index)
        {
            tilesCodes[y * sizeX + x] = index;
        }

        internal IEnumerable<CharSpawner> getSpawners()
        {
            return spawners;
        }

        public void setPass(int x, int y, int val)
        {
            passment[y * sizeX + x] = val;
        }

        internal void cameraShift(int x, int y)
        {
            camX += x;
            camY += y;
            if (camX < 0) camX = 0;
            if (camY < 0) camY = 0;
            if (camX > sizeX-22) camX = sizeX - 22;
            if (camY > sizeY-22) camY = sizeY - 22;
        }

        internal int getTile(int x, int y)
        {
            return tilesCodes[y * sizeX + x];
        }

        internal int getPass(int x, int y)
        {
            return passment[y * sizeX + x];
        }

        public void addItem(Item item, int x, int y)
        {
            Item copy = new Item(item,x,y);
            copy.localID = "item"+(localNextItem++);
            items.Add(copy);
        }
        public void addItem(Item item,string localID, int x, int y)
        {
            Item copy = new Item(item, x, y);
            copy.localID = localID;
            items.Add(copy);
        }
        public void addSpecial(Special item, int x, int y)
        {
            Special copy = new Special(item, x, y);
            if (copy.type == 0) { copy.localID = "activator" + (localNextActi++); activators.Add(copy);  }
            if (copy.type == 1) { copy.localID = "animTile" + (localNextAnim++); animTiles.Add(copy); }
            if (copy.type == 2) { copy.localID = "chest" + (localNextChes++); chests.Add(copy); }
            if (copy.type == 3) { copy.localID = "door" + (localNextDoor++); doors.Add(copy); }
            if (copy.type == 4) { copy.localID = "sound" + (localNextSoud++); sounds.Add(copy); }
            if (copy.type == 5) { copy.localID = "structure" + (localNextStrc++); structures.Add(copy); }

        }
        public void addSpecial(Special item,string localID, int x, int y)
        {
            Special copy = new Special(item, x, y);
            if (copy.type == 0) { copy.localID = localID; activators.Add(copy); }
            if (copy.type == 1) { copy.localID = localID; animTiles.Add(copy); }
            if (copy.type == 2) { copy.localID = localID; chests.Add(copy); }
            if (copy.type == 3) { copy.localID = localID; doors.Add(copy); }
            if (copy.type == 4) { copy.localID = localID; sounds.Add(copy); }
            if (copy.type == 5) { copy.localID = localID; structures.Add(copy); }

        }
        internal void save(string name)
        {
            string path = name;
            StreamWriter writer = new StreamWriter(path, false);
            writer.WriteLine(id + ";" + this.name + ";" + minimap + ";" + sizeX + ";" + sizeY + ";" + localNextItem + ";" + localNextActi + ";" + localNextAnim + ";" +
                localNextChes + ";" + localNextDoor + ";" + localNextSoud + ";" + localNextStrc + ";" + localNextChar+";"+explore+";"+battle);
            for (int i = 0; i < sizeX * sizeY; i++)
            {
                writer.Write(tilesCodes[i] + ";" + passment[i] + ";");

            }
            writer.WriteLine();
            for (int i = 0; i < items.Count; i++)
            {
                writer.Write(items[i].ID + ";" + items[i].localID + ";" + items[i].x + ";" + items[i].y + ";");
            }
            writer.WriteLine();
            for (int i = 0; i < activators.Count; i++)
            {
                writer.Write(activators[i].ID + ";" + activators[i].localID + ";" + activators[i].x + ";" + activators[i].y + ";");
            }
            writer.WriteLine();
            for (int i = 0; i < animTiles.Count; i++)
            {
                writer.Write(animTiles[i].ID + ";" + animTiles[i].localID + ";" + animTiles[i].x + ";" + animTiles[i].y + ";");
            }
            writer.WriteLine();
            for (int i = 0; i < chests.Count; i++)
            {
                writer.Write(chests[i].ID + ";" + chests[i].localID + ";" + chests[i].x + ";" + chests[i].y + ";" + chests[i].chestGold + "," + chests[i].chestLoot.Count);
                for (int j = 0; j < chests[i].chestLoot.Count; j++)
                {
                    writer.Write("," + chests[i].chestLoot[j]);
                }
                writer.Write(";");
            }
            writer.WriteLine();
            for (int i = 0; i < doors.Count; i++)
            {
                writer.Write(doors[i].ID + ";" + doors[i].localID + ";" + doors[i].x + ";" + doors[i].y + ";");
            }
            writer.WriteLine();
            for (int i = 0; i < sounds.Count; i++)
            {
                writer.Write(sounds[i].ID + ";" + sounds[i].localID + ";" + sounds[i].x + ";" + sounds[i].y + ";");
            }
            writer.WriteLine();
            for (int i = 0; i < structures.Count; i++)
            {
                writer.Write(structures[i].ID + ";" + structures[i].localID + ";" + structures[i].x + ";" + structures[i].y + ";");
            }
            writer.WriteLine();
            for (int i = 0; i < resortAreas.Count; i++)
            {
                writer.Write(resortAreas[i].id + ";" + resortAreas[i].name + ";" + resortAreas[i].x + ";" + resortAreas[i].y + ";" + resortAreas[i].size + ";");
            }
            writer.WriteLine();
            for (int i = 0; i < spawners.Count; i++)
            {
                writer.Write(spawners[i].playerID + ";" + spawners[i].localID + ";" + spawners[i].x + ";" + spawners[i].y + ";");
                writer.Write(spawners[i].startAI + "," + spawners[i].walkLeft + "," + spawners[i].walkTop + "," + spawners[i].walkRight + "," + spawners[i].walkBot + "," + spawners[i].walkWaitMin + "," + spawners[i].walkWaitMax + ",");
                writer.Write(spawners[i].buy + "," + spawners[i].sell + "," + spawners[i].teach + "," + spawners[i].spellChanging + "," + spawners[i].oneTimer + "," + spawners[i].enebled + ",");
                writer.Write(spawners[i].sellListID + "," + spawners[i].teachListID + "," + spawners[i].script + ",");
                writer.Write(spawners[i].patrolPoints.Length + ",");
                foreach (int[] el in spawners[i].patrolPoints)
                {
                    writer.Write(el[0] + "," + el[1] + "," + el[2] + ",");
                }
                writer.Write(";");
            }
            writer.WriteLine();
            writer.Close();
        }

        internal Special getSpecialByCoord(int x, int y)
        {
            foreach (Special el in chests)
            {
                if (el.x == x && el.y == y)
                {
                    return el;
                }
            }
            return null;
        }

        internal CharSpawner getPlayerByCoord(int x, int y)
        {
            foreach (CharSpawner el in spawners)
            {
                if (el.x == x && el.y == y)
                {
                    return el;
                }
            }
            return null;
        }

        internal void addCharSpawner(Char choosenSpecial, int x, int y)
        {
            CharSpawner copy = new CharSpawner(choosenSpecial, x, y);
            copy.localID = "char" + (localNextChar++);
            spawners.Add(copy);
        }

        internal void addCharSpawner(Char choosenSpecial, string localID, int x, int y)
        {
            CharSpawner copy = new CharSpawner(choosenSpecial, x, y);
            copy.localID = localID;
            spawners.Add(copy);
        }

  

        internal void load(string path)
        {
            StreamReader streamReader = new StreamReader(path);
            string line;
            int curLine = 0;
            tilesCodes.Clear();
            passment.Clear();
            items.Clear();
            doors.Clear();
            structures.Clear();
            sounds.Clear();
            chests.Clear();
            activators.Clear();
            animTiles.Clear();
            resortAreas.Clear();
            spawners.Clear();
            while ((line = streamReader.ReadLine()) != null)
            {
                if (curLine == 0)
                {
                    string[] metadata = line.Split(';');
                    id = metadata[0];
                    name = metadata[1];
                    minimap = metadata[2];
                    sizeX = int.Parse(metadata[3]);
                    sizeY = int.Parse(metadata[4]);

                    localNextItem = int.Parse(metadata[5]); 
                    localNextActi = int.Parse(metadata[6]); 
                    localNextAnim = int.Parse(metadata[7]); 
                    localNextChes = int.Parse(metadata[8]); 
                    localNextDoor = int.Parse(metadata[9]); 
                    localNextSoud = int.Parse(metadata[10]); 
                    localNextStrc = int.Parse(metadata[11]);
                    localNextChar = int.Parse(metadata[12]);

                    explore = metadata[13];
                    battle = metadata[14];

                    curLine++;
                } else if (curLine == 1)
                {
                    string[] tileData = line.Split(';');
                    for (int i = 0; i < sizeX * sizeY; i++)
                    {
                        tilesCodes.Add(int.Parse(tileData[i * 2]));
                        passment.Add(int.Parse(tileData[i * 2 + 1]));
                    }
                    curLine++;
                }
                else if (curLine == 2)
                {
                    if (line != "")
                    {
                        string[] tileData = line.Split(';');
                        for (int i = 0; i < tileData.Length-1; i += 4)
                        {
                            Item item = Item.getItemByID(tileData[i]);
                            int X = int.Parse(tileData[i + 2]);
                            int Y = int.Parse(tileData[i + 3]);
                            string localID = tileData[i + 1];
                            addItem(item, localID, X, Y);

                        }
                    }
                    curLine++;
                }
                else if (curLine == 3 || curLine == 4 || curLine == 6 || curLine == 7 || curLine == 8)
                {
                    string[] tileData = line.Split(';');
                    for (int i = 0; i < tileData.Length-1; i += 4)
                    {
                        Special item = Special.getSpecialByID(tileData[i]);
                        int X = int.Parse(tileData[i + 2]);
                        int Y = int.Parse(tileData[i + 3]);
                        string localID = tileData[i + 1];
                        addSpecial(item,localID, X, Y);

                    }
                    curLine++;
                }
                else if (curLine == 5)
                {
                    string[] tileData = line.Split(';');
                    for (int i = 0; i < tileData.Length - 1; i += 5)
                    {
                        Special item = Special.getSpecialByID(tileData[i]);
                        int X = int.Parse(tileData[i + 2]);
                        int Y = int.Parse(tileData[i + 3]);
                        string localID = tileData[i + 1];
                        string lootData = tileData[i + 4];
                        item.setLoot(lootData);
                        addSpecial(item, localID, X, Y);

                    }
                    curLine++;
                }
                else if (curLine == 9)
                {
                    string[] tileData = line.Split(';');
                    for (int i = 0; i < tileData.Length - 1; i += 5)
                    {
                        string id = tileData[i];
                        string name = tileData[i+1];
                        int X = int.Parse(tileData[i + 2]);
                        int Y = int.Parse(tileData[i + 3]);
                        int size = int.Parse(tileData[i + 4]);
                        ResortArea area = new ResortArea(id,name,X,Y,size);
                        resortAreas.Add(area);
                    }
                    curLine++;
                }
                else if (curLine == 10)
                {
                    string[] tileData = line.Split(';');
                    for (int i = 0; i < tileData.Length - 1; i += 5)
                    {
                        string id = tileData[i];
                        string name = tileData[i + 1];
                        int X = int.Parse(tileData[i + 2]);
                        int Y = int.Parse(tileData[i + 3]);
                        string AIData = tileData[i + 4];

                        CharSpawner area = new CharSpawner(id, name, X, Y, AIData);
                        spawners.Add(area);
                    }
                    curLine++;
                }
            }
            streamReader.Close();
        }

        internal void removeItem(int x, int y)
        {
            foreach(Item el in items)
            {
                if(el.x == x && el.y == y)
                {
                    items.Remove(el);
                    break;
                }
            }
        }

        internal void removeCharSpawner(int x, int y)
        {
            foreach (CharSpawner el in spawners)
            {
                if (el.x == x && el.y == y)
                {
                    spawners.Remove(el);
                    break;
                }
            }
        }

        internal void removeSpecial(int type, int x, int y)
        {
            List<Special> data = new List<Special>();
            if (type == 0) data = activators;
            if (type == 1) data = animTiles;
            if (type == 2) data = chests;
            if (type == 3) data = doors;
            if (type == 4) data = sounds;
            if (type == 5) data = structures;

            foreach (Special el in data)
            {
                if (el.x == x && el.y == y)
                {
                    data.Remove(el);
                    break;
                }
            }
        }

        internal IEnumerable<Item> getItems()
        {
            return items;
        }

        internal IEnumerable<Special> getSpecials(int v)
        {
           
            if(v == 0) return activators;
            if (v == 1) return animTiles;
            if (v == 2) return chests;
            if (v == 3) return doors;
            if (v == 4) return sounds;
            if (v == 5) return structures;

            return new List<Special>();
        }
    }
}
