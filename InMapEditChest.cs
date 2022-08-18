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

    public partial class InMapEditChest : Form
    {
        Special cur;

        public InMapEditChest(Special chest)
        {
            InitializeComponent();
            cur = chest;
            foreach (string el in cur.chestLoot)
            {
                Item curLoot = Item.getItemByID(el);
                if (curLoot != null)
                {
                    string name = curLoot.name;
                    loot.Rows.Add(el, name);
                }
            }

            goldMin.Value = cur.chestGold;
        }

        private void button5_Click(object sender, EventArgs e)
        {
            string id = itemNew.Text;
            Item curLoot = Item.getItemByID(id);
            if (curLoot != null)
            {
                string name = curLoot.name;
                loot.Rows.Add(id, name);
                cur.chestLoot.Add(id);
                List<Special> chests = Form1.map.chests;
            }
        }
        int row = -1;
        private void button6_Click(object sender, EventArgs e)
        {
            if (row > -1)
            {
                loot.Rows.RemoveAt(row);
                cur.chestLoot.RemoveAt(row);
            }
        }

        private void loot_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void loot_CellMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            row = e.RowIndex;
        }

        private void goldMin_ValueChanged(object sender, EventArgs e)
        {
            cur.chestGold = ((int)goldMin.Value);
        }

        private void InMapEditChest_Load(object sender, EventArgs e)
        {

        }
    }
}
