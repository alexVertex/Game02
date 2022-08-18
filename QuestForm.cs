using Game02Editor.history;
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
    public partial class QuestForm : Form
    {
        Quest cur = null;
        int row = -1;
        public QuestForm()
        {
            InitializeComponent();
            
                Bitmap map = new Bitmap("res/tex/minimaps/" + Form1.map.minimap + ".png");

                pictureBox1.Image = map;
            
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                string file = openFileDialog1.FileName;
                int statr = file.LastIndexOf("\\");
                int finish = file.LastIndexOf(".");
                file = file.Substring(statr + 1, finish - statr - 1);
                textBox1.Text = file;
                cur = new Quest("res/data/history/quests/" + file + ".csv");

                textBox3.Text = cur.name;
                textBox2.Text = cur.map;

                dataGridView1.Rows.Clear();
                int i = 0;
                foreach (string el in cur.taskData)
                {
                    string[] split = el.Split(';');
                    dataGridView1.Rows.Add();
                    int index = int.Parse(split[0]);
                    dataGridView1.Rows[i ].Cells[0].Value = type.Items[index];
                    for (int j = 1; j < split.Length-1; j++)
                    {
                        dataGridView1.Rows[i].Cells[j].Value = split[j];
                    }
                    i++;
                }
            }
        }

        private void textBox3_TextChanged(object sender, EventArgs e)
        {
            cur.name = textBox3.Text;
        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {
            cur.map = textBox2.Text;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            
            cur.saveToFile("res/data/history/quests/" + textBox1.Text + ".csv");
        }

        private void dataGridView1_CellEndEdit(object sender, DataGridViewCellEventArgs e)
        {
            cur.taskData.Clear();
            for (int i = 0; i < dataGridView1.Rows.Count;i++)
            {
                if (dataGridView1.Rows[i].Cells[0].Value == null) continue;
                string data = type.Items.IndexOf(dataGridView1.Rows[i].Cells[0].Value)+";";
                for(int j = 1; j < dataGridView1.Rows[i].Cells.Count;j++)
                {
                    data += dataGridView1.Rows[i].Cells[j].Value + ";";
                }
                cur.taskData.Add(data);
            }
        }

        private void QuestForm_Load(object sender, EventArgs e)
        {

        }

        private void dataGridView1_CellMouseUp(object sender, DataGridViewCellMouseEventArgs e)
        {
            row = e.RowIndex;
        }

        private void pictureBox1_MouseClick(object sender, MouseEventArgs e)
        {
            if(row != -1)
            {
                dataGridView1.Rows[row].Cells[2].Value = e.X;
                dataGridView1.Rows[row].Cells[3].Value = e.X;
            }
        }
    }
}
