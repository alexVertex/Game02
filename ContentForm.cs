using Game02Editor.world;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Game02Editor
{
    public partial class ContentForm : Form
    {
        string[] superTypes = { "Активатор", "Аним. тайл", "Контейнер", "Дверь", "Источник звука", "Структура" };
        List<Item> items;
        static List<Special> activators = new List<Special>();
        static List<Special> animTiles = new List<Special>();
        static List<Special> chests = new List<Special>();
        static List<Special> doors = new List<Special>();
        static List<Special> sounds = new List<Special>();
        static List<Special> structures = new List<Special>();
        List<Special>[] specials = { activators, animTiles, chests, doors, sounds, structures };
        List<CharSpawner> chars;

        public ContentForm()
        {
            InitializeComponent();
            items = (List<Item>)Form1.map.getItems();

            foreach (Item el in items)
            {
                {
                    dataGridView1.Rows.Add("Предмет", el.ID, el.localID, el.x, el.y);
                }
            }



            for (int i = 0; i < 6; i++)
            {
                specials[i] = (List<Special>)Form1.map.getSpecials(i);
                foreach (Special el in specials[i])
                {
                    dataGridView1.Rows.Add(superTypes[i], el.ID, el.localID, el.x, el.y);
                }
            }
            chars = (List<CharSpawner>)Form1.map.getSpawners();
            foreach (CharSpawner el in Form1.map.getSpawners())
            {
                {
                    dataGridView1.Rows.Add("Персонаж", el.playerID, el.localID, el.x, el.y);
                }
            }
        }

        private void dataGridView1_CellMouseUp(object sender, DataGridViewCellMouseEventArgs e)
        {
            int index = e.RowIndex;
            if (e.RowIndex == -1) return;
            string type = (string)dataGridView1.Rows[e.RowIndex].Cells[0].Value;
            if (type.Equals("Предмет"))
            {
                Item cur = items[e.RowIndex];
                Form1.map.camX = cur.x;
                Form1.map.camY = cur.y;
                if (Form1.map.camX > Form1.map.sizeX - 22) Form1.map.camX = Form1.map.sizeX - 22;
                if (Form1.map.camY > Form1.map.sizeY - 22) Form1.map.camY = Form1.map.sizeY - 22;
            }
            index -= items.Count;
            for (int i = 0; i < 6; i++)
            {
                if (type.Equals(superTypes[i]))
                {
                    Special cur = specials[i][index];
                    Form1.map.camX = cur.x;
                    Form1.map.camY = cur.y;
                    if (Form1.map.camX > Form1.map.sizeX - 22) Form1.map.camX = Form1.map.sizeX - 22;
                    if (Form1.map.camY > Form1.map.sizeY - 22) Form1.map.camY = Form1.map.sizeY - 22;
                    return;
                }
                index -= specials[i].Count;

            }
            if (type.Equals("Персонаж"))
            {
                CharSpawner cur = chars[index];
                Form1.map.camX = cur.x;
                Form1.map.camY = cur.y;
                if (Form1.map.camX > Form1.map.sizeX - 22) Form1.map.camX = Form1.map.sizeX - 22;
                if (Form1.map.camY > Form1.map.sizeY - 22) Form1.map.camY = Form1.map.sizeY - 22;
            }
            index -= chars.Count;

        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void ContentForm_Load(object sender, EventArgs e)
        {

        }
    }
}
