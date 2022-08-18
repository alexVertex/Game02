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
    public partial class DialogForm : Form
    {
        DialogData cur = null;
        Dialog dialog;
        public DialogForm()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                string file = openFileDialog1.FileName;
                int statr = file.LastIndexOf("\\");
                int finish = file.LastIndexOf(".");
                file = file.Substring(statr + 1, finish - statr - 1);
                textBox1.Text = file;
                cur = new DialogData("res/data/history/dialogs/" + file + ".csv");
            }
            comboBox1.Items.Clear();
            comboBox1.Items.AddRange(cur.getDialogs());
        }

        private void button3_Click(object sender, EventArgs e)
        {
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                string file = openFileDialog1.FileName;
                int statr = file.LastIndexOf("\\");
                int finish = file.LastIndexOf(".");
                file = file.Substring(statr + 1, finish - statr - 1);
                textBox1.Text = file;
                cur = new DialogData("res/data/history/dialogs/" + file + ".csv");
            }
            comboBox1.Items.Clear();
            comboBox1.Items.AddRange(cur.getDialogs());
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            dialog = cur.getDialog(comboBox1.Text);

            string cond = dialog.getConds();
            dataGridView1.Rows.Clear();
            string[] split = cond.Split(';');
            if (split.Length > 1)
            {
                for (int i = 0; i < split.Length-1; i += 2)
                {
                    dataGridView1.Rows.Add();
                    int index = int.Parse(split[i]);
                    dataGridView1.Rows[i / 2].Cells[0].Value = whatCode.Items[index];
                    dataGridView1.Rows[i / 2].Cells[1].Value = split[i+1];
                }
            }
            cond = dialog.getResult();
            dataGridView2.Rows.Clear();
            split = cond.Split(';');
            if (split.Length > 1)
            {
                for (int i = 0; i < split.Length-1; i += 2)
                {
                    dataGridView2.Rows.Add();
                    int index = int.Parse(split[i]);
                    dataGridView2.Rows[i / 2].Cells[0].Value = dataGridViewComboBoxColumn1.Items[index];
                    dataGridView2.Rows[i / 2].Cells[1].Value = split[i + 1];
                }
            }

            dataGridView3.Rows.Clear();
            int q = 0;
            foreach(string el in dialog.getLines()){
                split = el.Split(';');
                dataGridView3.Rows.Add();
                dataGridView3.Rows[q].Cells[0].Value = dataGridViewComboBoxColumn2.Items[int.Parse(split[0])];
                dataGridView3.Rows[q].Cells[1].Value = split[1];
                q++;
            }
        }

        private void changeDialogParams()
        {
            dialog.getLines().Clear();
            for(int i = 0; i < dataGridView3.Rows.Count-1; i++)
            {
                int selectedIndex = dataGridViewComboBoxColumn2.Items.IndexOf(dataGridView3.Rows[i].Cells[0].Value);
                dialog.getLines().Add(selectedIndex + ";"+ dataGridView3.Rows[i].Cells[1].Value);
            }

            string cond = "";
            for (int i = 0; i < dataGridView1.Rows.Count - 1; i++)
            {
                int selectedIndex = whatCode.Items.IndexOf(dataGridView1.Rows[i].Cells[0].Value);
                cond += (selectedIndex + ";" + dataGridView1.Rows[i].Cells[1].Value)+";";
            }
            dialog.setCond(cond);

            cond = "";
            for (int i = 0; i < dataGridView2.Rows.Count - 1; i++)
            {
                int selectedIndex = dataGridViewComboBoxColumn1.Items.IndexOf(dataGridView2.Rows[i].Cells[0].Value);
                cond += (selectedIndex + ";" + dataGridView2.Rows[i].Cells[1].Value) + ";";
            }
            dialog.setResults(cond);
        }

        private void button2_Click(object sender, EventArgs e)
        {
            
            cur.save(textBox1.Text);
        }

        private void dataGridView3_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void dataGridView3_CellEndEdit(object sender, DataGridViewCellEventArgs e)
        {
            changeDialogParams();
        }

        private void dataGridView2_CellEndEdit(object sender, DataGridViewCellEventArgs e)
        {
            changeDialogParams();

        }

        private void dataGridView1_CellEndEdit(object sender, DataGridViewCellEventArgs e)
        {
            changeDialogParams();

        }

        private void button4_Click(object sender, EventArgs e)
        {
            if(cur == null)
            {
                cur = new DialogData();
            }
            dialog = new Dialog();
            dialog.setName(textBox2.Text);
            cur.getDialogsFull()[textBox2.Text] = dialog;
            comboBox1.Items.Add(textBox2.Text);
        }

        private void button5_Click(object sender, EventArgs e)
        {
            cur.getDialogsFull().Remove(comboBox1.Text);
            comboBox1.Items.Remove(comboBox1.Text);
        }

        private void DialogForm_Load(object sender, EventArgs e)
        {

        }
    }
}
