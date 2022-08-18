using Game02Editor.world;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Game02Editor
{
    public partial class Form1 : Form
    {
        int modeAdd = 0;

        InMapEditSpawner spawnerSets = null;

        List<Item> intable = new List<Item>();
        Item choosen = null;
        List<Special> intableSpecials = new List<Special>();
        Special choosenSpecial = null;
        List<world.Char> intableChars = new List<world.Char>();
        world.Char choosenChar = null;
        List<Spell> intableSpell = new List<Spell>();
        Spell choosenSpell = null;

        static int mapSizeDef = 32;
        public static Map map = new Map("limb","Лимб","Limbo",mapSizeDef, mapSizeDef,"","");

        public bool inTileMod = false;
        Form2 tiles;
        ResortAreas editScript;
        ContentForm contentForm = new ContentForm();
        public Form1()
        {
            InitializeComponent();
            tiles = new Form2(this);
            Item.loadItems();
            Special.loadItems();
            world.Char.loadChars();
            EditFraction.loadFractionsData();
            Spell.loadSpells();
            Merch.loadAllMerch();
        }

        private void toolStripStatusLabel1_Click(object sender, EventArgs e)
        {

        }

        private void treeView1_AfterSelect(object sender, TreeViewEventArgs e)
        {
            string node = treeView1.SelectedNode.Name;
            dataGridView1.Rows.Clear();
            intable.Clear();
            intableSpecials.Clear();
            intableChars.Clear();
            
            List<Item> items = null;
            List<Special> specials = null;
            List<world.Char> chars = null;
            List<Spell> spells = null;

            switch (node)
            {
                case "weapons":
                    items = Item.get(0);
                    break;
                case "shields":
                    items = Item.get(4);
                    break;
                case "armours":
                    items = Item.get(5);
                    break;
                case "talismans":
                    items = Item.get(6);
                    break;
                case "uses":
                    items = Item.get(7);
                    break;
                case "runes":
                    items = Item.get(8);
                    break;
                case "misc":
                    items = Item.get(9);
                    break;
                case "items":
                    items = Item.get(-1);
                    break;
                case "objects":
                    specials = Special.get(-1);
                    break;
                case "activators":
                    specials = Special.get(0);
                    break;
                case "animTiles":
                    specials = Special.get(1);
                    break;
                case "chest":
                    specials = Special.get(2);
                    break;
                case "doors":
                    specials = Special.get(3);
                    break;
                case "sounds":
                    specials = Special.get(4);
                    break;
                case "structures":
                    specials = Special.get(5);
                    break;
                case "chars":
                    chars = world.Char.chars;
                    break;
                case "spells":
                    spells = Spell.get(-1);
                    break;

            }
            if (items != null)
            {
                modeAdd = 0;
                foreach (Item el in items)
                {
                    dataGridView1.Rows.Add(el.ID, el.name);
                }
                intable.AddRange(items);
                
            }

            if(specials != null)
            {
                modeAdd = 1;
                foreach (Special el in specials)
                {
                    dataGridView1.Rows.Add(el.ID, el.texture);
                }
                intableSpecials.AddRange(specials);
            }
            if (chars != null)
            {
                modeAdd = 2;
                foreach (world.Char el in chars)
                {
                    dataGridView1.Rows.Add(el.ID, el.texture);
                }
                intableChars.AddRange(chars);
            }
            if (spells != null)
            {
                modeAdd = 3 ;
                foreach (Spell el in spells)
                {
                    dataGridView1.Rows.Add(el.ID, el.texture);
                }
                intableSpell.AddRange(spells);
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void тайлыToolStripMenuItem_Click(object sender, EventArgs e)
        {
            inTileMod = true;
            tiles = new Form2(this);
            tiles.Show();
        }

        private void pictureBox1_Click(object sender, EventArgs e)
        {
            
        }

        private void pictureBox1_MouseClick(object sender, MouseEventArgs e)
        {
            int x = e.X / 32;
            int y = e.Y / 32;
            if (inTileMod)
            {
               
                if (tiles.changeTiles)
                {
                    map.setTile(x + map.camX, y + map.camY, tiles.curTile);
                }
                if (tiles.changePass)
                {
                    map.setPass(x + map.camX, y + map.camY, tiles.pass);
                }
            } else
            {
               
                if(choosen != null && modeAdd == 0)
                {
                    if (e.Button == MouseButtons.Left)
                    {
                        map.addItem(choosen, x + map.camX, y + map.camY);
                    }
                    if (e.Button == MouseButtons.Middle)
                    {
                        map.removeItem(x + map.camX, y + map.camY);
                    }
                }
                if(choosenSpecial != null && modeAdd == 1)
                {
                    if (e.Button == MouseButtons.Left)
                    {
                        map.addSpecial(choosenSpecial, x + map.camX, y + map.camY);
                    }
                    if (e.Button == MouseButtons.Middle)
                    {
                        map.removeSpecial(choosenSpecial.type, x + map.camX, y + map.camY);
                    }
                    if (e.Button == MouseButtons.Right)
                    {
                        Special cur = map.getSpecialByCoord(x + map.camX, y + map.camY);
                        if (cur != null)
                        {
                            InMapEditChest chestEdit = new InMapEditChest(cur);
                            chestEdit.Show();
                        }
                    }
                }
                if (choosenChar != null && modeAdd == 2)
                {
                    if (e.Button == MouseButtons.Left)
                    {
                        map.addCharSpawner(choosenChar, x + map.camX, y + map.camY);
                    }
                    if (e.Button == MouseButtons.Middle)
                    {
                        map.removeCharSpawner(x + map.camX, y + map.camY);
                    }
                    if (e.Button == MouseButtons.Right)
                    {
                        CharSpawner cur = map.getPlayerByCoord(x + map.camX, y + map.camY);
                        if (cur != null)
                        {
                            spawnerSets = new InMapEditSpawner(cur);
                            spawnerSets.Show();
                        }
                    }
                }
            }
        }



        private void menuStrip1_KeyDown(object sender, KeyEventArgs e)
        {
            if(e.KeyCode == Keys.W)
            {
                map.cameraShift(0, -1);
            }
            if (e.KeyCode == Keys.S)
            {
                map.cameraShift(0, 1);
            }
            if (e.KeyCode == Keys.A)
            {
                map.cameraShift(-1, 0);
            }
            if (e.KeyCode == Keys.D)
            {
                map.cameraShift(1, 0);
            }
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            Refresh();
            if (inTileMod)
            {
                if(tiles == null)
                {
                    inTileMod = false;
                }
            }
        }

        Bitmap greenPass = new Bitmap("res/tex/greedGreen.png");
        Bitmap orangePass = new Bitmap("res/tex/greedOrange.png");
        Bitmap redPass = new Bitmap("res/tex/greedRed.png");
        Bitmap soundsource = new Bitmap("res/tex/test.png");
        Bitmap bonfire = new Bitmap("res/tex/bonfire.png");

        internal bool drawResortAreas;

        private void Form1_Paint(object sender, PaintEventArgs e)
        {

            toolStripStatusLabel1.Text = "Текущая карта: " + map.name + ". Позиция: " + map.camX + "-"+map.camY;
            int camX = map.camX;
            int camY = map.camY;
            for (int i = 0; i < 22; i++)
            {
                for (int j = 0; j < 22; j++)
                {
                    Bitmap tile = Tile.getTile(map.getTile(camX + i, camY + j)).image;
                    e.Graphics.DrawImage(tile, 537+i*32, 27+j*32);
                }
            }
            foreach(Item el in map.getItems()){
                Bitmap tile = el.image;
                e.Graphics.DrawImage(tile, 537 + (el.x - camX) * 32, 27 + (el.y-camY) * 32);
            }
            foreach (Special el in map.getSpecials(1))
            {
                Bitmap tile = el.image;
                e.Graphics.DrawImage(tile, 537 + (el.x - camX) * 32, 27 + (el.y - camY) * 32);
            }
            foreach (Special el in map.getSpecials(0))
            {
                Bitmap tile = el.image;
                Rectangle clip = new Rectangle(537 + (el.x - camX) * 32, 27 + (el.y - camY) * 32, 32, 32);

                e.Graphics.DrawImageUnscaledAndClipped(tile, clip);
            }
            foreach (Special el in map.getSpecials(2))
            {
                Bitmap tile = el.image;
                Rectangle clip = new Rectangle(537 + (el.x - camX) * 32, 27 + (el.y - camY) * 32, 32, 32);

                e.Graphics.DrawImageUnscaledAndClipped(tile, clip);
            }
            foreach (Special el in map.getSpecials(3))
            {
                Bitmap tile = el.image;
                Rectangle clip = new Rectangle(537 + (el.x - camX) * 32, 27 + (el.y - camY) * 32, 32, 32);

                e.Graphics.DrawImageUnscaledAndClipped(tile, clip);
            }
            foreach (Special el in map.getSpecials(4))
            {
                Bitmap tile = soundsource;
                Rectangle clip = new Rectangle(537 + (el.x - camX) * 32, 27 + (el.y - camY) * 32, 32, 32);
                e.Graphics.DrawImageUnscaledAndClipped(tile, clip);
            }
            foreach (Special el in map.getSpecials(5))
            {
                Bitmap tile = el.image;
                Rectangle clip = new Rectangle(537 + (el.x - camX) * 32, 27 + (el.y - camY) * 32, el.sizeX, el.sizeY);
                e.Graphics.DrawImageUnscaledAndClipped(tile, clip);
            }
            foreach (CharSpawner el in map.spawners)
            {
                Bitmap tile = el.image;
                Rectangle clip = new Rectangle(537 + (el.x - camX) * 32, 27 + (el.y - camY) * 32, 32, 32);
                e.Graphics.DrawImageUnscaledAndClipped(tile, clip);
            }
            if(spawnerSets != null)
            {
                spawnerSets.draw(e);
            }
            if (drawResortAreas)
            {
                foreach(ResortArea el in map.resortAreas)
                {
                    Bitmap tile = bonfire;
                    Rectangle clip = new Rectangle(537 + (el.x - camX) * 32, 27 + (el.y - camY) * 32, 32, 32);
                    e.Graphics.DrawImageUnscaledAndClipped(tile, clip);
                }
            }
            if (inTileMod)
            {
                if (tiles.showPass)
                {
                    for (int i = 0; i < 22; i++)
                    {
                        for (int j = 0; j < 22; j++)
                        {
                            switch(map.getPass(camX + i, camY + j))
                            {
                                case 1:
                                    e.Graphics.DrawImage(greenPass, 537 + i * 32, 27 + j * 32);
                                    break;
                                case 0:
                                    e.Graphics.DrawImage(orangePass, 537 + i * 32, 27 + j * 32);
                                    break;
                                case -1:
                                    e.Graphics.DrawImage(redPass, 537 + i * 32, 27 + j * 32);
                                    break;
                            }
                        }
                    }
                }
            }
        }
        public string defPath;
        private void создатьКартуToolStripMenuItem_Click(object sender, EventArgs e)
        {
            CreateMap form = new CreateMap(this, true);
            form.Show();

        }

        private void загрузитьКартуToolStripMenuItem_Click(object sender, EventArgs e)
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            if (openFileDialog.ShowDialog() == DialogResult.OK)
            {
                openFileDialog.InitialDirectory = "res";
                string file = openFileDialog.FileName;
                map.load(file);
                defPath = file;
            }
        }

        private void сохранитьКартуToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (defPath != null)
            {
                map.save(defPath);
            } else
            {
                MessageBox.Show("Воспользуйтесь пунктом 'Сохранить как'");
            }
        }

        private void openFileDialog1_FileOk(object sender, CancelEventArgs e)
        {

        }

        private void миникартаToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Minimap form = new Minimap(this);
            form.Show();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (modeAdd == 0)
            {
                EditItem form = new EditItem(null);
                form.Show();
            }
            if (modeAdd == 1)
            {
                EditObjext form = new EditObjext(null);
                form.Show();
            }
            if (modeAdd == 2)
            {
                EditCharacter form = new EditCharacter(null);
                form.Show();
            }
            if (modeAdd == 3)
            {
                EditSpell form = new EditSpell(null);
                form.Show();
            }
        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            
            
        }

        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            
        }

        private void dataGridView1_CellMouseUp(object sender, DataGridViewCellMouseEventArgs e)
        {
            if (e.RowIndex == -1) return;
            if (modeAdd == 0)
            {
                choosen = intable[e.RowIndex];
                if (e.Button == MouseButtons.Right)
                {
                    EditItem form = new EditItem(intable[e.RowIndex]);
                    form.Show();
                }
            }
            if(modeAdd == 1)
            {
                choosenSpecial = intableSpecials[e.RowIndex];
                if(e.Button == MouseButtons.Right)
                {
                    EditObjext form = new EditObjext(intableSpecials[e.RowIndex]);
                    form.Show();
                }
            }
            if (modeAdd == 2)
            {
                choosenChar = intableChars[e.RowIndex];
                if (e.Button == MouseButtons.Right)
                {
                    EditCharacter form = new EditCharacter(intableChars[e.RowIndex]);
                    form.Show();
                }
            }
            if (modeAdd == 3)
            {
                choosenSpell = intableSpell[e.RowIndex];
                if (e.Button == MouseButtons.Right)
                {
                    EditSpell form = new EditSpell(intableSpell[e.RowIndex]);
                    form.Show();
                }
            }
        }

        private void содержимоеToolStripMenuItem_Click(object sender, EventArgs e)
        {
            contentForm = new ContentForm();
            contentForm.Show();
        }

        private void создатьToolStripMenuItem_Click(object sender, EventArgs e)
        {
            EditScript editScript = new EditScript(null);
            editScript.Show();
        }

        private void открытьToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                string file = openFileDialog1.FileName;
                EditScript editScript = new EditScript(file);
                editScript.Show();
            }
        }

        private void зоныОтдыхаToolStripMenuItem_Click(object sender, EventArgs e)
        {
            editScript = new ResortAreas(this);
            editScript.Show();
        }

        private void menuStrip1_ItemClicked(object sender, ToolStripItemClickedEventArgs e)
        {

        }

        private void фракцииToolStripMenuItem_Click(object sender, EventArgs e)
        {
            EditFraction editScript = new EditFraction();
            editScript.Show();
        }

        private void диалогиToolStripMenuItem_Click(object sender, EventArgs e)
        {
            DialogForm dialogEdit = new DialogForm();
            dialogEdit.Show();
        }

        private void заданияToolStripMenuItem_Click(object sender, EventArgs e)
        {
            QuestForm giestEdit = new QuestForm();
            giestEdit.Show();
        }

        private void настройкиКартыToolStripMenuItem_Click(object sender, EventArgs e)
        {
            CreateMap form = new CreateMap(this, false);
            form.Show();
        }

        private void сохранитьКакToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (saveFileDialog1.ShowDialog() == DialogResult.OK)
            {
         
                string file = saveFileDialog1.FileName;
                map.save(file);
            }
        }

        private void ассортиментToolStripMenuItem_Click(object sender, EventArgs e)
        {
            EditMerch form = new EditMerch();
            form.Show();
        }
    }
}
