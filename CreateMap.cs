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
    public partial class CreateMap : Form
    {
        Form1 parent;
        bool newMap;
        public CreateMap(Form1 par, bool neww)
        {
            parent = par;
            InitializeComponent();
            newMap = neww;

            if (!neww)
            {
                button2.Text = "Сохранить ихменения";
                label3.Visible = false;
                label4.Visible = false;
                numericUpDown1.Visible = false;
                numericUpDown2.Visible = false;

                textBox1.Text = Form1.map.id;
                textBox2.Text = Form1.map.name;
                textBox3.Text = Form1.map.minimap;

                dataGridView1.Rows.Clear();
                dataGridView2.Rows.Clear();

                string[] split = Form1.map.explore.Split(',');
                for(int i = 0; i < split.Length - 1; i++)
                {
                    dataGridView1.Rows.Add(split[i]);
                }

                split = Form1.map.battle.Split(',');
                for (int i = 0; i < split.Length - 1; i++)
                {
                    dataGridView2.Rows.Add(split[i]);
                }

                Bitmap b = new Bitmap("res/tex/minimaps/" + Form1.map.minimap + ".png");

                pictureBox1.Image = b;
            }
        }


        private void button2_Click(object sender, EventArgs e)
        {
            string explore = "";
            for (int i = 0; i < dataGridView1.Rows.Count-1; i++)
            {
                explore += dataGridView1.Rows[i].Cells[0].Value + ",";
            }
            string battle = "";
            for (int i = 0; i < dataGridView2.Rows.Count-1; i++)
            {
                battle += dataGridView2.Rows[i].Cells[0].Value + ",";
            }

            if (newMap)
            {       
                Form1.map = new Map(textBox1.Text, textBox2.Text, textBox3.Text, (int)numericUpDown1.Value, (int)numericUpDown2.Value, explore, battle);
                Dispose();
            } else
            {
                Form1.map.name = textBox2.Text;
                Form1.map.id = textBox1.Text;
                Form1.map.minimap = textBox3.Text;
                Form1.map.explore = explore;
                Form1.map.battle = battle;
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (openFileDialog2.ShowDialog() == DialogResult.OK)
            {
                string file = openFileDialog2.FileName;
                int statr = file.LastIndexOf("\\");
                int finish = file.LastIndexOf(".");
                file = file.Substring(statr+1, finish-statr-1);
                textBox3.Text = file;
                Bitmap b = new Bitmap("res/tex/minimaps/" + file+".png");

                pictureBox1.Image = b;
            }
        }

        private void label4_Click(object sender, EventArgs e)
        {

        }
    }
}
