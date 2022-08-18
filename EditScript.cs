using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Game02Editor
{
    public partial class EditScript : Form
    {
        public EditScript(string file)
        {
            InitializeComponent();
            if (file != null)
            {

                int statr = file.LastIndexOf("\\");
                file = file.Substring(statr + 1);
                StreamReader streamReader = new StreamReader("res/scripts/" + file);
                string line;
                string gather = "";
                while ((line = streamReader.ReadLine()) != null)
                {
                    gather += line+ "\r\n";
                }
                streamReader.Close();
                textBox1.Text = file;
                textBox2.Text = gather;
            }
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void EditScript_Load(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            string fileName = textBox1.Text;
            string fileContent = textBox2.Text;

            StreamWriter streamReader = new StreamWriter("res/scripts/"+ fileName);
            streamReader.Write(fileContent);
            streamReader.Close();
        }
    }
}
