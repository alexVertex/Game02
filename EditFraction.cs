using Game02Editor.world;
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
    public partial class EditFraction : Form
    {
        public static List<CharFraction> fractions = new List<CharFraction>();

        public static void loadFractionsData()
        {
            StreamReader streamReader = new StreamReader("res/data/players/fractions.csv");
            string line = "";
            int lineCur = 0;
            while ((line = streamReader.ReadLine()) != null)
            {
                string[] split = line.Split(';');
                if (lineCur == 0)
                {
                    lineCur = 1;
                    for (int i = 0; i < split.Length-1; i++)
                    {
                        CharFraction fraction = new CharFraction(split[i]);
                        fractions.Add(fraction);
                    }
                } 
                else
                {
                    CharFraction cur = fractions[lineCur - 1];
                    for (int i = 0; i < fractions.Count; i++)
                    {
                        CharFraction next = fractions[i];
                        cur.attitude[next.name] = int.Parse(split[i]);

                    }
                    lineCur++;
                }
            }
        }

        internal static string[] getFracionsNames()
        {
            List<string> data = new List<string>();
            foreach(CharFraction el in fractions)
            {
                data.Add(el.name);
            }
            return data.ToArray();
        }

        public EditFraction()
        {
            InitializeComponent();
            foreach (CharFraction el in fractions)
            {
                comboBox1.Items.Add(el.name);
                comboBox2.Items.Add(el.name);
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string name = textBox1.Text;
            bool ok = true;
            foreach(CharFraction el in fractions)
            {
                if (el.Equals(name))
                {
                    ok = false;
                    break;
                }
            }
            if (ok)
            {
                CharFraction fraction = new CharFraction(textBox1.Text);
                foreach (CharFraction el in fractions)
                {
                       
                    fraction.attitude[el.name] = 1;
                    el.attitude[fraction.name] = 1;
                }
                fraction.attitude[fraction.name] = 0;

                fractions.Add(fraction);
                comboBox1.Items.Add(fraction.name);
                comboBox2.Items.Add(fraction.name);
            }
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            setAttitudeValue();
        }

        private void comboBox2_SelectedIndexChanged(object sender, EventArgs e)
        {
            setAttitudeValue();
        }

        private void setAttitudeValue()
        {
            if (comboBox1.SelectedIndex == -1) return;
            if (comboBox2.SelectedIndex == -1) return;

            CharFraction cur = fractions[comboBox1.SelectedIndex];
            comboBox3.SelectedIndex = cur.attitude[fractions[comboBox2.SelectedIndex].name];
        }

        private void comboBox3_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (comboBox1.SelectedIndex == -1) return;
            if (comboBox2.SelectedIndex == -1) return;
            CharFraction cur = fractions[comboBox1.SelectedIndex];
            cur.attitude[fractions[comboBox2.SelectedIndex].name] = comboBox3.SelectedIndex;
          
        }

        private void button2_Click(object sender, EventArgs e)
        {
            StreamWriter streamReader = new StreamWriter("res/data/players/fractions.csv");
            string head = "";
            foreach (CharFraction el in fractions)
            {
                head += el.name + ";";
            }
            streamReader.WriteLine(head);
            foreach (CharFraction el in fractions)
            {
                string body = "";
                foreach (CharFraction ele in fractions)
                {
                    body += el.attitude[ele.name] + ";";
                }
                streamReader.WriteLine(body);
            }
            streamReader.Close();
        }
    }
}
