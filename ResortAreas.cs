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
    public partial class ResortAreas : Form
    {
        Form1 parent;
        ResortArea cur = null;
        List<ResortArea> areas = new List<ResortArea>();
        public ResortAreas(Form1 form)
        {
            parent = form;
            InitializeComponent();
            loadData();
            parent.drawResortAreas = true;
        }

        private void loadData()
        {
            areas.Clear();
            dataGridView1.Rows.Clear();
            foreach(ResortArea area in Form1.map.resortAreas)
            {
                dataGridView1.Rows.Add(area.id, area.name, area.x, area.y, area.size);
                areas.Add(area);
            }
        }
        private void button1_Click(object sender, EventArgs e)
        {
            string id = textBox1.Text;
            string name = textBox2.Text;
            int x = (int)numericUpDown1.Value;
            int y = (int)numericUpDown2.Value;
            int size = (int)numericUpDown3.Value;
            ResortArea area = new ResortArea(id, name,x,y,size);
            areas.Add(area);
            dataGridView1.Rows.Add(id, name, x, y, size);
            Form1.map.resortAreas.Add(area);
        }

        private void dataGridView1_CellMouseUp(object sender, DataGridViewCellMouseEventArgs e)
        {
            if (e.RowIndex == -1) return;
            cur = areas[e.RowIndex];
            textBox1.Text = cur.id;
            textBox2.Text = cur.name;
            numericUpDown1.Value = cur.x;
            numericUpDown2.Value = cur.y;
            numericUpDown3.Value = cur.size;
            
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if(cur != null)
            {
                string id = textBox1.Text;
                string name = textBox2.Text;
                int x = (int)numericUpDown1.Value;
                int y = (int)numericUpDown2.Value;
                int size = (int)numericUpDown3.Value;
                Form1.map.resortAreas.Remove(cur);
                cur = new ResortArea(id, name, x, y, size);
                Form1.map.resortAreas.Add(cur);
                loadData();

            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            if (cur != null)
            {
                Form1.map.resortAreas.Remove(cur);
                areas.Remove(cur);
                loadData();
            }
        }

        private void ResortAreas_FormClosed(object sender, FormClosedEventArgs e)
        {
            parent.drawResortAreas = false;
        }
    }
}
