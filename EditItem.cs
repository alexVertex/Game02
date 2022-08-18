using Game02Editor.world;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Imaging;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Game02Editor
{
    public partial class EditItem : Form
    {
        List<Label> labelsSpec = new List<Label>();
        List<Control> componentsSpec = new List<Control>();

        bool addNew = false;
        Item cur = null;

        #region Строки
        static string[] blades = { "Кинжал", "Прямой меч", "Кривой меч", "Меч для выпадов", "Двуручный меч" };
        static string[] shortt = { "Топор", "Дубина", "Секира", "Молот"};
        static string[] longg = { "Копьё", "Алебарда" };
        static string[] range = { "Лук", "Арбалет", "Праща", "Метательное" };
        static string[] shield = { "Малый", "Средний", "Большой" };
        static string[] armour = { "Лёгкая", "Средняя", "Тяжелая" };
        static string[] talisman = { "Амулет", "Кольцо" };
        static string[] uses = { "Зелье", "Устройство", "Бомба", "Порошок" };
        static string[] runes = { "Слабая", "Средняя", "Сильная" };
        static string[] misc = { "Материал", "Важное", "Трофеи" };
        static string[][] values = { blades, shortt, longg, range, shield, armour, talisman, uses, runes, misc };

        static string[] bladesLab = { "Урон", "Тип урона", "Скорость атак", "Заряды", "Дистанция","Урон по балансу" };
        static string[] shorttLab = { "Урон", "Тип урона", "Скорость атак", "Заряды", "Дистанция", "Урон по балансу" };
        static string[] longgLab = { "Урон", "Тип урона", "Скорость атак", "Заряды", "Дистанция", "Урон по балансу" };
        static string[] rangeLab = { "Урон", "Тип урона", "Скорость атак", "Заряды", "Дистанция", "Урон по балансу" };
        static string[] shieldLab = { "Поглощение урона", "Стабильность" };
        static string[] armourLab = { "От колющего", "От режущего", "От рубящего", "От дробящего",
                    "От огня", "От холода","От электричества","От кислот","От отравления","От ментального","От негативного" };
        static string[] talismanLab = { "Номер эффекта", "Сила эффекта","Время эффекта" };
        static string[] usesLab = {  "Номер эффекта", "Сила эффекта", "Время эффекта", "Заряды", "Дистанция","Радиус", "Номер снаряда", };
        static string[] runesLab = { "Что изменяем", "Величина изменения", "Запас сил","Сложность","Подпись" };
        static string[] miscLab = { };
        static string[][] valuesLab = { bladesLab, shorttLab, longgLab, rangeLab, shieldLab, armourLab, talismanLab, usesLab, runesLab, miscLab };

        static int[] bladesCom = { 0, 1, 2, 3, 4, 5 };
        static int[] shorttCom = { 0, 1, 2, 3, 4, 5 };
        static int[] longgCom = { 0, 1, 2, 3, 4, 5 };
        static int[] rangeCom = { 0, 1, 2, 3, 4, 5 };
        static int[] shieldCom = { 6, 7 };
        static int[] armourCom = {8,9,10,11,12,13,14,15,16,17,18 };
        static int[] talismanCom = {19,20,21 };
        static int[] usesCom = {19,20,21,3,4,22,23 };
        static int[] runesCom = {24,25,26,27,28 };
        static int[] miscCom = { };
        static int[][] valuesCom = { bladesCom, shorttCom, longgCom, rangeCom, shieldCom, armourCom, talismanCom, usesCom, runesCom, miscCom };
        #endregion
        public EditItem(Item item)
        {
            InitializeComponent();
            labelsSpec.Add(label11); labelsSpec.Add(label12); labelsSpec.Add(label13); labelsSpec.Add(label14); labelsSpec.Add(label15);
            labelsSpec.Add(label16); labelsSpec.Add(label17); labelsSpec.Add(label18); labelsSpec.Add(label19); labelsSpec.Add(label20);
            labelsSpec.Add(label21);
            componentsSpec.Add(damage); componentsSpec.Add(damageType); componentsSpec.Add(AttackInterval); componentsSpec.Add(ammo);
            componentsSpec.Add(distant); componentsSpec.Add(balanceDamage); componentsSpec.Add(damageReduction); componentsSpec.Add(staminaDamageReduction);
            componentsSpec.Add(defensePierce); componentsSpec.Add(defenseSlash); componentsSpec.Add(defenseChop); componentsSpec.Add(defenseStrike);
            componentsSpec.Add(defenseFire); componentsSpec.Add(defenseIce); componentsSpec.Add(defenseShock); componentsSpec.Add(defenseAcid);
            componentsSpec.Add(defensePoison); componentsSpec.Add(defenseMental); componentsSpec.Add(defenseNegative);
            componentsSpec.Add(effectID); componentsSpec.Add(effectPower); componentsSpec.Add(effectTime);
            componentsSpec.Add(radius); componentsSpec.Add(ammoIndex);
            componentsSpec.Add(whatToRaise); componentsSpec.Add(raisePower); componentsSpec.Add(staminaRaise); componentsSpec.Add(difficult); componentsSpec.Add(label);
            if (item == null)
            {
                cur = new Item();
                addNew = true;
                comboBox2.Items.AddRange(values[0]);
                button2.Show();
            } else
            {
                cur = item;
                numericUpDown4.Value = cur.cost;
                numericUpDown3.Value = cur.weight;
                textBox2.Text = cur.name;
                textBox1.Text = cur.ID;
                comboBox1.SelectedIndex = cur.classs;
                comboBox2.SelectedIndex = (cur.type);
                textBox3.Text = cur.texture;
                numericUpDown2.Value = cur.partY;
                numericUpDown1.Value = cur.partX;

                if (cur.classs <= 3)
                {
                    damage.Value = cur.damage;
                    damageType.SelectedIndex = cur.damageType;
                    AttackInterval.Value = cur.interval;
                    ammo.Value = cur.ammo;
                    distant.Value = cur.distant;
                    balanceDamage.Value = cur.balanceDamage;
                }
                if (cur.classs == 4)
                {
                    damageReduction.Value = cur.damageReduction;
                    staminaDamageReduction.Value = cur.staminaDamageReduction;
                }
                if (cur.classs == 5)
                {
                    defensePierce.Value = cur.defensePierce;
                    defenseSlash.Value = cur.defenseSlash;
                    defenseChop.Value = cur.defenseChop;
                    defenseStrike.Value = cur.defenseStrike;
                    defenseFire.Value = cur.defenseFire;
                    defenseIce.Value = cur.defenseIce;
                    defenseShock.Value = cur.defenseShock;
                    defenseAcid.Value = cur.defenseAcid;
                    defensePoison.Value = cur.defensePoison;
                    defenseMental.Value = cur.defenseMental;
                    defenseNegative.Value = cur.defenseNegative;
                }
                if (cur.classs == 6)
                {
                    effectID.Value = cur.effectID;
                    effectPower.Value = cur.effectPower;
                    effectTime.Value = cur.effectTime;
                    textBox4.Text = cur.desc;
                }
                if (cur.classs == 7)
                {
                    effectID.Value = cur.effectID;
                    effectPower.Value = cur.effectPower;
                    effectTime.Value = cur.effectTime;
                    distant.Value = cur.distant;
                    radius.Value = cur.radius;
                    ammo.Value = cur.ammo;
                    ammoIndex.Value = cur.ammoIndex;
                    textBox4.Text = cur.desc;
                }
                if (cur.classs == 8)
                {
                    whatToRaise.SelectedIndex = cur.whatToRaise;
                    raisePower.Text = cur.raiseMult+"";
                    staminaRaise.Value = cur.staminaNeed;
                    difficult.Value = cur.difficult;
                    label.Text = cur.label;
                    textBox4.Text = cur.desc;
                }
                if (cur.classs == 9)
                {
                    textBox4.Text = cur.desc;
                }
                Bitmap pic = new Bitmap("res/tex/items/" + cur.texture + ".png");
                Rectangle rectangle = new Rectangle(cur.partX * 32, cur.partY * 32, 32, 32);
                pictureBox1.Image = pic.Clone(rectangle, PixelFormat.DontCare);
                button2.Hide();
            }
           
        }

        private void label5_Click(object sender, EventArgs e)
        {

        }

        private void label3_Click(object sender, EventArgs e)
        {

        }

        private void label4_Click(object sender, EventArgs e)
        {

        }

        private void label8_Click(object sender, EventArgs e)
        {

        }

        private void pictureBox1_Click(object sender, EventArgs e)
        {

        }

        private void EditItem_Load(object sender, EventArgs e)
        {

        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            comboBox2.Items.Clear();
            comboBox2.Items.AddRange(values[comboBox1.SelectedIndex]);
            cur.classs = comboBox1.SelectedIndex;
            changeSpecInterface();
        }

        private void changeSpecInterface()
        {
            for (int i = 0; i < 11; i++)
            {
                if (i < valuesLab[cur.classs].Length)
                {
                    labelsSpec[i].Text = valuesLab[cur.classs][i];
                }
                else
                {
                    labelsSpec[i].Text = "";
                }
            }
            for(int i = 0; i < componentsSpec.Count; i++)
            {
                componentsSpec[i].Visible = false;
            }
            for (int i = 0; i < valuesCom[cur.classs].Length; i++)
            {
                componentsSpec[valuesCom[cur.classs][i]].Visible = true;
            }
        }
        private void button1_Click(object sender, EventArgs e)
        {
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                string file = openFileDialog1.FileName;
                int statr = file.LastIndexOf("\\");
                int finish = file.LastIndexOf(".");
                file = file.Substring(statr + 1, finish - statr - 1);
                textBox3.Text = file;
                Bitmap b = new Bitmap("res/tex/items/" + file + ".png");

                pictureBox1.Image = b;
                cur.texture = file;
            }
        }

        private void numericUpDown1_ValueChanged(object sender, EventArgs e)
        {
            cur.partX = (int)numericUpDown1.Value;
            Bitmap pic = new Bitmap("res/tex/items/" + cur.texture + ".png");
            Rectangle rectangle = new Rectangle(cur.partX * 32, cur.partY * 32, 32, 32);
            pictureBox1.Image = pic.Clone(rectangle, PixelFormat.Format16bppRgb555);

        }

        private void numericUpDown2_ValueChanged(object sender, EventArgs e)
        {
            cur.partY = (int)numericUpDown2.Value;
            Bitmap pic = new Bitmap("res/tex/items/" + cur.texture + ".png");
            Rectangle rectangle = new Rectangle(cur.partX * 32, cur.partY * 32, 32, 32);
            pictureBox1.Image = pic.Clone(rectangle, PixelFormat.DontCare);
        }

        private void comboBox2_SelectedIndexChanged(object sender, EventArgs e)
        {
            cur.type = comboBox2.SelectedIndex;

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            cur.ID = textBox1.Text;
        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {
            cur.name = textBox2.Text;
        }

        private void numericUpDown3_ValueChanged(object sender, EventArgs e)
        {
            cur.weight = (int)numericUpDown3.Value;
        }

        private void numericUpDown4_ValueChanged(object sender, EventArgs e)
        {
            cur.cost = (int)numericUpDown4.Value;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (addNew)
            {
                bool contains = false;
                foreach (Item el in Item.items)
                {
                    if (el.ID.Equals(cur.ID)){
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    Item.addItem(cur);
                }
            } else
            {

                //Item.addItem(cur);
            }
        }

        private void label16_Click(object sender, EventArgs e)
        {

        }

        private void damage_ValueChanged(object sender, EventArgs e)
        {
            cur.damage = (int)damage.Value;
        }

        private void damageType_SelectedIndexChanged(object sender, EventArgs e)
        {
            cur.damageType = (int)damageType.SelectedIndex;
        }

        private void AttackInterval_ValueChanged(object sender, EventArgs e)
        {
            cur.interval = (int)AttackInterval.Value;
        }

        private void ammo_ValueChanged(object sender, EventArgs e)
        {
            cur.ammo = (int)ammo.Value;
        }

        private void distant_ValueChanged(object sender, EventArgs e)
        {
            cur.distant = (int)distant.Value;
        }

        private void balanceDamage_ValueChanged(object sender, EventArgs e)
        {
            cur.balanceDamage = (int)balanceDamage.Value;
        }

        private void damageReduction_ValueChanged(object sender, EventArgs e)
        {
            cur.damageReduction = (int)damageReduction.Value;
        }

        private void staminaDamageReduction_ValueChanged(object sender, EventArgs e)
        {
            cur.staminaDamageReduction = (int)staminaDamageReduction.Value;
        }

        private void defenseNegative_ValueChanged(object sender, EventArgs e)
        {
            cur.defenseNegative = (int)defenseNegative.Value;
        }

        private void defenseSlash_ValueChanged(object sender, EventArgs e)
        {
            cur.defenseSlash = (int)defenseSlash.Value;
        }

        private void defenseChop_ValueChanged(object sender, EventArgs e)
        {
            cur.defenseChop = (int)defenseChop.Value;
        }

        private void defenseStrike_ValueChanged(object sender, EventArgs e)
        {
            cur.defenseStrike = (int)defenseStrike.Value;
        }

        private void defenseFire_ValueChanged(object sender, EventArgs e)
        {
            cur.defenseFire = (int)defenseFire.Value;
        }

        private void defenseIce_ValueChanged(object sender, EventArgs e)
        {
            cur.defenseIce = (int)defenseIce.Value;
        }

        private void defenseShock_ValueChanged(object sender, EventArgs e)
        {
            cur.defenseShock = (int)defenseShock.Value;
        }

        private void defenseAcid_ValueChanged(object sender, EventArgs e)
        {
            cur.defenseAcid = (int)defenseAcid.Value;
        }

        private void defensePoison_ValueChanged(object sender, EventArgs e)
        {
            cur.defensePoison = (int)defensePoison.Value;
        }

        private void defenseMental_ValueChanged(object sender, EventArgs e)
        {
            cur.defenseMental = (int)defenseMental.Value;
        }

        private void defensePierce_ValueChanged(object sender, EventArgs e)
        {
            cur.defensePierce = (int)defensePierce.Value;
        }

        private void effectID_ValueChanged(object sender, EventArgs e)
        {
            cur.effectID = (int)effectID.Value;
        }

        private void effectPower_ValueChanged(object sender, EventArgs e)
        {
            cur.effectPower = (int)effectPower.Value;
        }

        private void effectTime_ValueChanged(object sender, EventArgs e)
        {
            cur.effectTime = (int)effectTime.Value;
        }

        private void textBox4_TextChanged(object sender, EventArgs e)
        {
            cur.desc = textBox4.Text;
        }

        private void ammoIndex_ValueChanged(object sender, EventArgs e)
        {
            cur.ammoIndex = (int)ammoIndex.Value;
        }

        private void radius_ValueChanged(object sender, EventArgs e)
        {
            cur.radius = (int)radius.Value;
        }

        private void whatToRaise_SelectedIndexChanged(object sender, EventArgs e)
        {
            cur.whatToRaise = whatToRaise.SelectedIndex;
        }

        private void raisePower_SelectedIndexChanged(object sender, EventArgs e)
        {
            cur.raiseMult = int.Parse(raisePower.Text);
        }

        private void staminaRaise_ValueChanged(object sender, EventArgs e)
        {
            cur.staminaNeed = (int)staminaRaise.Value;
        }

        private void difficult_ValueChanged(object sender, EventArgs e)
        {
            cur.difficult = (int)difficult.Value;
        }

        private void label_TextChanged(object sender, EventArgs e)
        {
            cur.label = label.Text;
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Item.upDate();
        }
    }
}
