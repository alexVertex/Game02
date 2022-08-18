using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Game02Editor.world
{
    public partial class EditMerch : Form
    {
        Merch cur = null;
        public EditMerch()
        {
            InitializeComponent();
            foreach(string el in Merch.data.Keys)
            {
                comboBox1.Items.Add(el);
            }
        }

        private void EditMerch_Load(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            Merch merch = new Merch(textBox1.Text + ";");
            Merch.data[merch.name] = merch;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            string id = textBox2.Text;
            Item item = Item.getItemByID(id);
            if(item != null)
            {
                dataGridView1.Rows.Add(id, item.name,1);
                cur.ids.Add(id + ",1");
            }
            else
            {
                Spell spell = Spell.getItemByID(id);
                if (spell != null)
                {
                    dataGridView1.Rows.Add(id, spell.name, 1);
                    cur.ids.Add(id + ",1");

                }
            }
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            dataGridView1.Rows.Clear();
            cur = Merch.data[comboBox1.Text];
            List<string> data = Merch.data[comboBox1.Text].ids;
            for(int i = 0; i < data.Count; i++)
            {
                string id = data[i].Split(',')[0];
                string count = data[i].Split(',')[1];
                Item item = Item.getItemByID(id);
                if (item != null)
                {
                    dataGridView1.Rows.Add(id, item.name, count);
                } else
                {
                    Spell spell = Spell.getItemByID(id);
                    if (spell != null)
                    {
                        dataGridView1.Rows.Add(id, spell.name, count);

                    }
                }
            }
        }
        int row = -1;
        private void dataGridView1_CellMouseUp(object sender, DataGridViewCellMouseEventArgs e)
        {
            row = e.RowIndex;
        }

        private void button3_Click(object sender, EventArgs e)
        {
            if (row == -1) return;
            dataGridView1.Rows.RemoveAt(row);
            cur.ids.RemoveAt(row);
        }

        private void button4_Click(object sender, EventArgs e)
        {
            Merch.saveAllMerch();
        }

        private void dataGridView1_CellEndEdit(object sender, DataGridViewCellEventArgs e)
        {
            cur.ids[row] = dataGridView1.Rows[row].Cells[0].Value + ","+ dataGridView1.Rows[row].Cells[2].Value;

        }
    }
}
