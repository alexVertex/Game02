using Game02Editor.world;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Imaging;
using System.Linq;
using System.Media;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Game02Editor
{
    public partial class EditObjext : Form
    {
        Special cur;
        bool addNew = false;

        public EditObjext(Special special)
        {
            InitializeComponent();

            if (special == null)
            {
                addNew = true;
                cur = new Special();
                button2.Show();

            } else
            {
                cur = special;
                textBox1.Text = cur.ID;
                comboBox1.SelectedIndex = cur.type;
                textBox4.Text = cur.script;
                if (cur.type == 0)
                {
                    textBox3.Text = cur.texture;
                    partX.Value = cur.startX;
                    comboBox2.SelectedIndex = !cur.isFloor ? 0 : 1;
                    lockLevelAct.Value = cur.lockLevel;
                }
                if (cur.type == 1)
                {
                    numericUpDown2.Value = cur.animFrames;
                    numericUpDown1.Value = cur.animSpeed;

                }
                if (cur.type == 2)
                {
                    textBox3.Text = cur.texture;
                    numericUpDown4.Value = cur.startX;
                    comboBox3.SelectedIndex = !cur.isLoot ? 0 : 1;
                    numericUpDown3.Value = cur.lockLevel;
                }
                if (cur.type == 3)
                {
                    textBox3.Text = cur.texture;
                    numericUpDown5.Value = cur.lockLevel;
                }
                if (cur.type == 4)
                {
                    numericUpDown6.Value = cur.hearRadius;
                    textBox2.Text = cur.soundName;
                }
                if (cur.type == 5)
                {
                    textBox3.Text = cur.texture;
                    numericUpDown10.Value = cur.startX;
                    numericUpDown9.Value = cur.startY;
                    numericUpDown11.Value = cur.sizeX;
                    numericUpDown12.Value = cur.sizeY;
                    numericUpDown13.Value = cur.transparentPart;

                }
                if (cur.type != 5)
                {
                    Bitmap pic = new Bitmap(textures[cur.type] + cur.texture + ".png");
                    Rectangle rectangle = new Rectangle(cur.startX * 32, cur.startY * 32, cur.sizeX, cur.sizeY);
                    pictureBox1.Image = pic.Clone(rectangle, PixelFormat.DontCare);
                } 
                else
                {
                    Bitmap pic = new Bitmap(textures[cur.type] + cur.texture + ".png");
                    Rectangle rectangle = new Rectangle(cur.startX * 32, cur.startY * 32, cur.sizeX*32, cur.sizeY*32);
                    pictureBox1.Image = pic.Clone(rectangle, PixelFormat.DontCare);
                }
            }
        }

        private void EditObjext_Load(object sender, EventArgs e)
        {

        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (addNew)
            {
                bool contains = false;
                foreach (Special el in Special.specials)
                {
                    if (el.ID.Equals(cur.ID))
                    {
                        contains = true;
                        break;
                    }
                }
                if (!contains)
                {
                    Special.addItem(cur);
                }
            }
        }
        static string[] textures = { "res/tex/activators/", "res/tex/animTiles/", "res/tex/chests/",
        "res/tex/doors/","res/tex/","res/tex/objects/"};
        private void button1_Click(object sender, EventArgs e)
        {
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                string file = openFileDialog1.FileName;
                int statr = file.LastIndexOf("\\");
                int finish = file.LastIndexOf(".");
                file = file.Substring(statr + 1, finish - statr - 1);
                textBox3.Text = file;
                Bitmap b = new Bitmap(textures[cur.type] + file + ".png");
                Rectangle rectangle = new Rectangle(cur.startX * 32, cur.startY * 32, cur.sizeX, cur.sizeY);
                pictureBox1.Image = b.Clone(rectangle, PixelFormat.DontCare);
                cur.texture = file;
            }
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            cur.ID = textBox1.Text;
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            cur.type = comboBox1.SelectedIndex;
        }

        private void textBox3_TextChanged(object sender, EventArgs e)
        {
            cur.texture = textBox3.Text;
        }

        private void partX_ValueChanged(object sender, EventArgs e)
        {
            cur.startX = (int)partX.Value;
            Bitmap b = new Bitmap(textures[cur.type] + cur.texture + ".png");
            Rectangle rectangle = new Rectangle(cur.startX * 32, 0, 32, 128);
            pictureBox1.Image = b.Clone(rectangle, PixelFormat.DontCare);
        }

        private void comboBox2_SelectedIndexChanged(object sender, EventArgs e)
        {
            cur.isFloor = comboBox2.SelectedIndex == 1;
        }

        private void lockLevelAct_ValueChanged(object sender, EventArgs e)
        {
            cur.lockLevel = (int)lockLevelAct.Value;
        }
        double progress = 0;
        int oldFrame = 0;
        private void timer1_Tick(object sender, EventArgs e)
        {
            if(cur.type == 1)
            {
                progress += cur.animSpeed/100.0;
                if(progress >= 1)
                {
                    progress--;
                }
                int curFrame = (int)( progress * cur.animFrames);
                if (oldFrame != curFrame)
                {
                    Bitmap b = new Bitmap(textures[cur.type] + cur.texture + ".png");
                    Rectangle rectangle = new Rectangle(curFrame * 32, 0, 32, 32);
                    pictureBox1.Image = b.Clone(rectangle, PixelFormat.DontCare);
                    oldFrame = curFrame;
                }
            }
        }

        private void numericUpDown2_ValueChanged(object sender, EventArgs e)
        {
            cur.animFrames = ((int)numericUpDown2.Value);
        }

        private void numericUpDown1_ValueChanged(object sender, EventArgs e)
        {
            cur.animSpeed = ((int)numericUpDown1.Value);

        }

        private void numericUpDown4_ValueChanged(object sender, EventArgs e)
        {
            cur.startX = (int)numericUpDown4.Value;
            Bitmap b = new Bitmap(textures[cur.type] + cur.texture + ".png");
            Rectangle rectangle = new Rectangle(cur.startX * 32, 0, 32, 128);
            pictureBox1.Image = b.Clone(rectangle, PixelFormat.DontCare);
        }

        private void comboBox3_SelectedIndexChanged(object sender, EventArgs e)
        {
            cur.isLoot = comboBox2.SelectedIndex == 1;
        }

        private void numericUpDown3_ValueChanged(object sender, EventArgs e)
        {
            cur.lockLevel = ((int)numericUpDown3.Value);
        }

        private void numericUpDown5_ValueChanged(object sender, EventArgs e)
        {
            cur.lockLevel = ((int)numericUpDown5.Value);

        }

        private void button3_Click(object sender, EventArgs e)
        {
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                string file = openFileDialog1.FileName;
                int statr = file.LastIndexOf("\\");
                int finish = file.LastIndexOf(".");
                file = file.Substring(statr + 1, finish - statr - 1);
                string fileFullPath = "res/audio/ambient/" + file + ".ogg";
                textBox2.Text = file;
                cur.soundName = file;
            }
        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {
            cur.soundName = textBox2.Text;
        }

        private void numericUpDown6_ValueChanged(object sender, EventArgs e)
        {
            cur.hearRadius = ((int)numericUpDown6.Value);
        }

        private void numericUpDown10_ValueChanged(object sender, EventArgs e)
        {
            cur.startX = (int)numericUpDown10.Value;
            redraw();
        }

        private void numericUpDown9_ValueChanged(object sender, EventArgs e)
        {
            cur.startY = (int)numericUpDown9.Value;
            redraw();
        }

        private void numericUpDown11_ValueChanged(object sender, EventArgs e)
        {
            cur.sizeX = (int)numericUpDown11.Value;
            redraw();
        }

        private void numericUpDown12_ValueChanged(object sender, EventArgs e)
        {
            cur.sizeY = (int)numericUpDown12.Value;
            redraw();
        }

        private void numericUpDown13_ValueChanged(object sender, EventArgs e)
        {
            cur.transparentPart = (int)numericUpDown13.Value;
        }

        private void redraw()
        {
            if(cur.sizeX > 8)
            {
                cur.sizeX = 8;
            }
            if (cur.sizeY > 8)
            {
                cur.sizeY = 8;
            }
            Bitmap pic = new Bitmap(textures[cur.type] + cur.texture + ".png");
            Rectangle rectangle = new Rectangle(cur.startX * 32, cur.startY * 32, cur.sizeX * 32, cur.sizeY * 32);
            pictureBox1.Image = pic.Clone(rectangle, PixelFormat.DontCare);
        }

        private void button4_Click(object sender, EventArgs e)
        {
            Special.update();
        }

        private void textBox4_TextChanged(object sender, EventArgs e)
        {
            cur.script = textBox4.Text;
        }

        private void button5_Click(object sender, EventArgs e)
        {
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                string file = openFileDialog1.FileName;
                int statr = file.LastIndexOf("\\");
                file = file.Substring(statr + 1);
                textBox4.Text = file;
                cur.script = file;
            }
        }
    }
}
