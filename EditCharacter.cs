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
    public partial class EditCharacter : Form
    {
        world.Char cur;
        bool addNew = false;
        public EditCharacter(world.Char character)
        {
            InitializeComponent();
            faction.Items.Clear();
            faction.Items.AddRange(EditFraction.getFracionsNames());
            if(character == null)
            {
                cur = new world.Char();
                addNew = true;
                button3.Show();
            } else
            {
                cur = character;
                name.Text = cur.name;
                ID.Text = cur.ID;
                texture.Text = cur.texture;
                voice.Text = cur.voice;
                faction.Text = cur.fraction;
                playerStatus.SelectedIndex = cur.playerStatus;
                health.Value = cur.health;
                stamina.Value = cur.stamina;
                armor.Value = cur.armor;
                poise.Value = cur.poise;
                speedMove.Value = cur.speedMove;
                speedAttack.Value = cur.speedAttack;
                visionDistant.Value = cur.visionDistant;
                damage.Value = cur.damage;
                comboBox1.SelectedIndex = cur.damageType;
                ShieldReduction.Value = cur.shieldReduct;
                shieldStability.Value = cur.shieldStability;

                defPierce.Value = cur.defPierce;
                defSlash.Value = cur.defSlash;
                defChop.Value = cur.defChop;
                defStrike.Value = cur.defStrike;
                defFire.Value = cur.defFire;
                defIce.Value = cur.defIce;
                defShock.Value = cur.defShock;
                defAcid.Value = cur.defAcid;
                defPoison.Value = cur.defPoison;
                defMental.Value = cur.defMental;
                defNegative.Value = cur.defNegative;

                goldMin.Value = cur.goldMin;
                goldMax.Value = cur.goldMax;

                foreach(string el in cur.itemsLoot)
                {
                    Item curLoot = Item.getItemByID(el);
                    if (curLoot != null)
                    {
                        string name = curLoot.name;
                        loot.Rows.Add(el, name);
                    }
                }
                Bitmap b = new Bitmap("res/tex/chars/" + cur.texture + ".png");

                pictureBox1.Image = b;
            }

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void label8_Click(object sender, EventArgs e)
        {

        }

        private void label9_Click(object sender, EventArgs e)
        {

        }

        private void label7_Click(object sender, EventArgs e)
        {

        }

        private void EditCharacter_Load(object sender, EventArgs e)
        {

        }

        private void name_TextChanged(object sender, EventArgs e)
        {
            cur.name = name.Text;
        }

        private void ID_TextChanged(object sender, EventArgs e)
        {
            cur.ID = ID.Text;
        }

        private void texture_TextChanged(object sender, EventArgs e)
        {
            cur.texture = texture.Text;
        }

        private void voice_TextChanged(object sender, EventArgs e)
        {
            cur.voice = voice.Text;
        }

        private void faction_SelectedIndexChanged(object sender, EventArgs e)
        {
            cur.fraction = faction.Text;
        }

        private void playerStatus_SelectedIndexChanged(object sender, EventArgs e)
        {
            cur.playerStatus = playerStatus.SelectedIndex;
        }

        private void health_ValueChanged(object sender, EventArgs e)
        {
            cur.health = ((int)health.Value);
        }

        private void stamina_ValueChanged(object sender, EventArgs e)
        {
            cur.stamina = ((int)stamina.Value);
        }

        private void armor_ValueChanged(object sender, EventArgs e)
        {
            cur.armor = ((int)armor.Value);
        }

        private void poise_ValueChanged(object sender, EventArgs e)
        {
            cur.poise = ((int)poise.Value);

        }

        private void speedMove_ValueChanged(object sender, EventArgs e)
        {
            cur.speedMove = ((int)speedMove.Value);

        }

        private void speedAttack_ValueChanged(object sender, EventArgs e)
        {
            cur.speedAttack = ((int)speedAttack.Value);
        }

        private void visionDistant_ValueChanged(object sender, EventArgs e)
        {
            cur.visionDistant = ((int)visionDistant.Value);

        }

        private void damage_ValueChanged(object sender, EventArgs e)
        {
            cur.damage = (int)damage.Value;
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            cur.damageType = comboBox1.SelectedIndex;

        }

        private void ShieldReduction_ValueChanged(object sender, EventArgs e)
        {
            cur.shieldReduct = ((int)ShieldReduction.Value);

        }

        private void shieldStability_ValueChanged(object sender, EventArgs e)
        {
            cur.shieldStability = ((int)shieldStability.Value);

        }

        private void defPierce_ValueChanged(object sender, EventArgs e)
        {
            cur.defPierce = ((int)defPierce.Value);

        }

        private void defSlash_ValueChanged(object sender, EventArgs e)
        {
            cur.defSlash = ((int)defSlash.Value);

        }

        private void defChop_ValueChanged(object sender, EventArgs e)
        {
            cur.defChop = ((int)defChop.Value);
        }

        private void defStrike_ValueChanged(object sender, EventArgs e)
        {
            cur.defStrike = ((int)defStrike.Value);

        }

        private void defFire_ValueChanged(object sender, EventArgs e)
        {
            cur.defFire = ((int)defFire.Value);
        }

        private void defIce_ValueChanged(object sender, EventArgs e)
        {
            cur.defIce = ((int)defIce.Value);
        }

        private void defShock_ValueChanged(object sender, EventArgs e)
        {
            cur.defShock = ((int)defShock.Value);

        }

        private void defAcid_ValueChanged(object sender, EventArgs e)
        {
            cur.defAcid = ((int)defAcid.Value);
        }

        private void defPoison_ValueChanged(object sender, EventArgs e)
        {
            cur.defPoison = ((int)defPoison.Value);
        }

        private void defMental_ValueChanged(object sender, EventArgs e)
        {
            cur.defMental = ((int)defMental.Value);
        }

        private void defNegative_ValueChanged(object sender, EventArgs e)
        {
            cur.defNegative = ((int)defNegative.Value);
        }

        private void button3_Click(object sender, EventArgs e)
        {
            if (addNew)
            {
                bool contains = false;
                foreach (world.Char el in world.Char.chars)
                {
                    if (el.ID.Equals(cur.ID))
                    {
                        contains = true;
                        break;
                    }
                }
                if (!contains)
                {
                    world.Char.addItem(cur);
                }
            }
            else
            {

                //Item.addItem(cur);
            }
        }

        private void button4_Click(object sender, EventArgs e)
        {
            world.Char.upDate();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                string file = openFileDialog1.FileName;
                int statr = file.LastIndexOf("\\");
                int finish = file.LastIndexOf(".");
                file = file.Substring(statr + 1, finish - statr - 1);
                texture.Text = file;
                Bitmap b = new Bitmap("res/tex/chars/" + file + ".png");

                pictureBox1.Image = b;
                cur.texture = file;
            }
        }

        private void itemNew_TextChanged(object sender, EventArgs e)
        {

        }

        private void goldMin_ValueChanged(object sender, EventArgs e)
        {
            cur.goldMin = (int)goldMin.Value;
        }

        private void goldMax_ValueChanged(object sender, EventArgs e)
        {
            cur.goldMax = (int)goldMax.Value;

        }

        private void button5_Click(object sender, EventArgs e)
        {
            string id = itemNew.Text;
            Item curLoot = Item.getItemByID(id);
            if(curLoot != null)
            {
                string name = curLoot.name;
                loot.Rows.Add(id, name);
                cur.itemsLoot.Add(id);
            }
        }
        int row = -1;
        private void loot_CellMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            row = e.RowIndex;
        }

        private void button6_Click(object sender, EventArgs e)
        {
            if(row> -1)
            {
                loot.Rows.RemoveAt(row);
                cur.itemsLoot.RemoveAt(row);
            }
        }

        private void loot_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }
    }
}
