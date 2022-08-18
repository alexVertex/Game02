using Game02Editor.world;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Imaging;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Game02Editor
{
    public partial class EditSpell : Form
    {
        Spell cur;
        private bool addNew;

        static string[] elemental = { "Огонь", "Мороз", "Земля","Кислота" };
        static string[] mental = { "Подавление", "Прозрение","Восстановление" };
        static string[] speechCraft = { "Воодушевление", "Заговоры" };
        static string[] weapon = { "Клинки", "Топоры", "Дубины", "Копья" };
        static string[] range = { "Луки", "Арбалеты" };
        static string[] shield = { "Лёгкие", "Средние", "Тяжелые" };
        static string[][] values = { elemental, mental, speechCraft, weapon, range, shield };

        public EditSpell(Spell sp)
        {
            InitializeComponent();

            if (sp == null)
            {
                cur = new Spell();
                addNew = true;
                comboBox2.Items.AddRange(values[0]);
                button2.Show();
            }
            else
            {
                cur = sp;
                textBox2.Text = cur.name;
                textBox1.Text = cur.ID;
                comboBox1.SelectedIndex = cur.sort;
                comboBox2.SelectedIndex = (cur.type);
                textBox3.Text = cur.texture;
                numericUpDown2.Value = cur.partY;
                numericUpDown1.Value = cur.partX;
                numericUpDown3.Value = cur.projectileID;
                numericUpDown4.Value = cur.effectID;
                numericUpDown6.Value = cur.effectPower;
                numericUpDown5.Value = cur.effectTime;
                comboBox3.SelectedIndex = cur.targetType;
                numericUpDown9.Value = cur.distant;
                numericUpDown8.Value = cur.radius;
                numericUpDown7.Value = cur.staminaNeed;
                numericUpDown11.Value = cur.difficult;
                textBox4.Text = cur.desc;
                numericUpDown10.Value = cur.cost;


                Bitmap pic = new Bitmap("res/tex/spells/" + cur.texture + ".png");
                Rectangle rectangle = new Rectangle(cur.partX * 32, cur.partY * 32, 32, 32);
                pictureBox1.Image = pic.Clone(rectangle, PixelFormat.Format16bppRgb555);
            }
        }

        private void EditSpell_Load(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            cur.ID = textBox1.Text;
        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {
            cur.name = textBox2.Text;
        }

        private void textBox3_TextChanged(object sender, EventArgs e)
        {
            cur.texture = textBox3.Text;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                string file = openFileDialog1.FileName;
                int statr = file.LastIndexOf("\\");
                int finish = file.LastIndexOf(".");
                file = file.Substring(statr + 1, finish - statr - 1);
                textBox3.Text = file;
                Bitmap b = new Bitmap("res/tex/spells/" + file + ".png");

                pictureBox1.Image = b;
                cur.texture = file;
            }
        }

        private void numericUpDown1_ValueChanged(object sender, EventArgs e)
        {
            cur.partX = (int)numericUpDown1.Value;
            Bitmap pic = new Bitmap("res/tex/spells/" + cur.texture + ".png");
            Rectangle rectangle = new Rectangle(cur.partX * 32, cur.partY * 32, 32, 32);
            pictureBox1.Image = pic.Clone(rectangle, PixelFormat.Format16bppRgb555);
        }

        private void numericUpDown2_ValueChanged(object sender, EventArgs e)
        {
            cur.partY = (int)numericUpDown2.Value;
            Bitmap pic = new Bitmap("res/tex/spells/" + cur.texture + ".png");
            Rectangle rectangle = new Rectangle(cur.partX * 32, cur.partY * 32, 32, 32);
            pictureBox1.Image = pic.Clone(rectangle, PixelFormat.Format16bppRgb555);
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            comboBox2.Items.Clear();
            comboBox2.Items.AddRange(values[comboBox1.SelectedIndex]);
            cur.sort = comboBox1.SelectedIndex;
        }

        private void comboBox2_SelectedIndexChanged(object sender, EventArgs e)
        {
            cur.type = comboBox2.SelectedIndex;

        }

        private void numericUpDown3_ValueChanged(object sender, EventArgs e)
        {
            cur.projectileID = ((int)numericUpDown3.Value);
        }

        private void numericUpDown4_ValueChanged(object sender, EventArgs e)
        {
            cur.effectID = ((int)numericUpDown4.Value);
        }

        private void numericUpDown6_ValueChanged(object sender, EventArgs e)
        {
            cur.effectPower = ((int)numericUpDown6.Value);
        }

        private void numericUpDown5_ValueChanged(object sender, EventArgs e)
        {
            cur.effectTime = ((int)numericUpDown5.Value);
        }

        private void comboBox3_SelectedIndexChanged(object sender, EventArgs e)
        {
            cur.targetType = comboBox3.SelectedIndex;
        }

        private void numericUpDown9_ValueChanged(object sender, EventArgs e)
        {
            cur.distant = ((int)numericUpDown9.Value);
        }

        private void numericUpDown8_ValueChanged(object sender, EventArgs e)
        {
            cur.radius = ((int)numericUpDown8.Value);
        }

        private void numericUpDown7_ValueChanged(object sender, EventArgs e)
        {
            cur.staminaNeed = ((int)numericUpDown7.Value);
        }

        private void numericUpDown11_ValueChanged(object sender, EventArgs e)
        {
            cur.difficult = ((int)numericUpDown11.Value);
        }

        private void textBox4_TextChanged(object sender, EventArgs e)
        {
            cur.desc = textBox4.Text;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (addNew)
            {
                bool contains = false;
                foreach (Spell el in Spell.items)
                {
                    if (el.ID.Equals(cur.ID))
                    {
                        contains = true;
                        break;
                    }
                }
                if (!contains)
                {
                    Spell.addItem(cur);
                }
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Spell.upDate();
        }

        private void numericUpDown10_ValueChanged(object sender, EventArgs e)
        {
            cur.cost = ((int)numericUpDown10.Value);
        }
    }
}
