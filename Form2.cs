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
    public partial class Form2 : Form
    {
        Form1 form1;
        string path = "res/data/world/tiles.csv";
        List<string> tiles = new List<string>();
        string textureName = "A0";
        public int curTile;
        internal bool changeTiles = true;
        internal bool changePass = true;
        internal bool showPass = false;

        internal int pass = 1;

        public Form2(Form1 parent)
        {
            form1 = parent;
            InitializeComponent();
            StreamReader streamReader = new StreamReader(path);
            string line;
            while ((line = streamReader.ReadLine()) != null)
            {
                tiles.Add(line);
                Tile.addTile(line);
            }
            streamReader.Close();
        }

        private void pictureBox1_Click(object sender, EventArgs e)
        {
            
           

        }

        private void button2_Click(object sender, EventArgs e)
        {
            StreamWriter writer = new StreamWriter(path, true);
            for(int i = 0; i < 16; i++)
            {
                for (int j = 0; j < 16; j++)
                {
                    if (!tiles.Contains(textureName + ";" + i + ";" + j))
                    {
                        tiles.Add(textureName + ";" + i + ";" + j);
                        Tile.addTile(textureName + ";" + i + ";" + j);
                        writer.WriteLine(textureName + ";" + i + ";" + j);
                    }
                }
            }
            writer.Close();
        }

        private void pictureBox1_MouseClick(object sender, MouseEventArgs e)
        {
            
            int x = e.X/32;
            int y = e.Y/32;
            Rectangle rectangle = new Rectangle(x*32, y*32, 32, 32);
            var pic = (Bitmap)pictureBox1.Image;
            pictureBox2.Image = pic.Clone(rectangle, PixelFormat.Format16bppRgb555);
            string choosen = textureName + ";" + x + ";" + y;
            curTile = tiles.IndexOf(choosen);

           
        }

        private void Form2_Load(object sender, EventArgs e)
        {

        }

        private void Form2_FormClosed(object sender, FormClosedEventArgs e)
        {
            form1.inTileMod = false;
        }

        private void checkBox3_CheckedChanged(object sender, EventArgs e)
        {
            changeTiles = checkBox3.Checked;
        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {
            changePass = checkBox1.Checked;
        }

        private void radioButton3_CheckedChanged(object sender, EventArgs e)
        {
            pass = 1;
        }

        private void radioButton2_CheckedChanged(object sender, EventArgs e)
        {
            pass = 0;
        }

        private void radioButton1_CheckedChanged(object sender, EventArgs e)
        {
            pass = -1;
        }

        private void checkBox2_CheckedChanged(object sender, EventArgs e)
        {
            showPass = checkBox2.Checked;
        }
    }
}
