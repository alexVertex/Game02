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
    public partial class InMapEditSpawner : Form
    {
        public CharSpawner cur;
        bool onDraw = true;
        List<int[]> points = new List<int[]>();
        int rowIndex = -1;

        public InMapEditSpawner(CharSpawner work)
        {
            InitializeComponent();
            cur = work;
            load();
        }

        private void load()
        {
            points.Clear();
            dataGridView1.Rows.Clear();
            for(int i = 0; i < cur.patrolPoints.Length; i++)
            {
                points.Add(cur.patrolPoints[i]);
                dataGridView1.Rows.Add(cur.patrolPoints[i][0], cur.patrolPoints[i][1], cur.patrolPoints[i][2]);
            }

            comboBox1.SelectedIndex = cur.startAI;
            left.Value = cur.walkLeft;
            right.Value = cur.walkRight;
            top.Value = cur.walkTop;
            bot.Value = cur.walkBot;
            timeMin.Value = cur.walkWaitMin;
            timeMax.Value = cur.walkWaitMax;

            checkBox1.Checked = cur.buy == 1;
            checkBox2.Checked = cur.sell == 1;
            checkBox4.Checked = cur.teach == 1;
            checkBox3.Checked = cur.spellChanging == 1;
            checkBox5.Checked = cur.oneTimer == 1;
            checkBox6.Checked = cur.enebled == 1;

            textBox1.Text = cur.sellListID;
            textBox2.Text = cur.teachListID;
            textBox3.Text = cur.script;

        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (rowIndex == -1) return;
            points.RemoveAt(rowIndex);
            dataGridView1.Rows.RemoveAt(rowIndex);
        }

        private void button3_Click(object sender, EventArgs e)
        {
            cur.startAI = comboBox1.SelectedIndex;

            cur.walkLeft = ((int)left.Value);
            cur.walkRight = ((int)right.Value);
            cur.walkTop = ((int)top.Value);
            cur.walkBot = ((int)bot.Value);
            cur.walkWaitMin = ((int)timeMin.Value);
            cur.walkWaitMax = ((int)timeMax.Value);

            cur.patrolPoints = points.ToArray();

            cur.buy = checkBox1.Checked ? 1 : 0;
            cur.sell = checkBox2.Checked ? 1 : 0;
            cur.teach = checkBox4.Checked ? 1 : 0;
            cur.spellChanging = checkBox3.Checked ? 1 : 0;
            cur.oneTimer = checkBox5.Checked ? 1 : 0;
            cur.enebled = checkBox6.Checked ? 1 : 0;

            cur.sellListID = textBox1.Text;
            cur.teachListID = textBox2.Text;
            cur.script = textBox3.Text;

            load();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            int[] node = null;
            if (points.Count == 0)
            {
                node = new int[] { cur.x+1, cur.y+1, 100 };
            }
            else
            {
                int[] prevNode = points[points.Count - 1];
                node = new int[] { prevNode[0]+1, prevNode[1] + 1, 100 };
            }
            points.Add(node);

            dataGridView1.Rows.Add(node[0], node[1], node[2]);
        }
        static Bitmap borderTL = new Bitmap("res/tex/borderLT.png");
        static Bitmap borderTR = new Bitmap("res/tex/borderRT.png");
        static Bitmap borderBL = new Bitmap("res/tex/borderLB.png");
        static Bitmap borderBR = new Bitmap("res/tex/borderRB.png");
        static Bitmap wayPoint = new Bitmap("res/tex/waypoint.png");

        internal void draw(PaintEventArgs e)
        {
            if (onDraw)
            {
                if (comboBox1.SelectedIndex == 0)
                {
                    int leftReal = Math.Min(((int)left.Value), ((int)right.Value));
                    int rightReal = Math.Max(((int)left.Value), ((int)right.Value));
                    int topReal = Math.Min(((int)top.Value), ((int)bot.Value));
                    int botReal = Math.Max(((int)top.Value), ((int)bot.Value));

                    Rectangle clip = new Rectangle(537 + (leftReal - Form1.map.camX) * 32, 27 + (topReal - Form1.map.camY) * 32, 32, 32);
                    e.Graphics.DrawImage(borderTL, clip);

                    clip = new Rectangle(537 + (rightReal - Form1.map.camX) * 32, 27 + (topReal - Form1.map.camY) * 32, 32, 32);
                    e.Graphics.DrawImage(borderTR, clip);

                    clip = new Rectangle(537 + (leftReal - Form1.map.camX) * 32, 27 + (botReal - Form1.map.camY) * 32, 32, 32);
                    e.Graphics.DrawImage(borderBL, clip);

                    clip = new Rectangle(537 + (rightReal - Form1.map.camX) * 32, 27 + (botReal - Form1.map.camY) * 32, 32, 32);
                    e.Graphics.DrawImage(borderBR, clip);
                }
                else if(comboBox1.SelectedIndex == 1)
                {
                    for (int i = 0; i < points.Count; i++)
                    {
                        Rectangle clip = new Rectangle(537 + (points[i][0] - Form1.map.camX) * 32, 27 + (points[i][1] - Form1.map.camY) * 32, 32, 32);
                        e.Graphics.DrawImage(wayPoint, clip);

                    }
                }
            }
        }

        private void dataGridView1_CellEndEdit(object sender, DataGridViewCellEventArgs e)
        {
            points[e.RowIndex][e.ColumnIndex] = int.Parse(dataGridView1.Rows[e.RowIndex].Cells[e.ColumnIndex].Value+"");
        }

        private void InMapEditSpawner_FormClosed(object sender, FormClosedEventArgs e)
        {
            onDraw = false;
        }

        private void InMapEditSpawner_Load(object sender, EventArgs e)
        {

        }
    }
}
