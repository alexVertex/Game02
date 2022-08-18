using System.IO;

namespace Game02Editor
{
    partial class Form1
    {
        /// <summary>
        /// Обязательная переменная конструктора.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Освободить все используемые ресурсы.
        /// </summary>
        /// <param name="disposing">истинно, если управляемый ресурс должен быть удален; иначе ложно.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Код, автоматически созданный конструктором форм Windows

        /// <summary>
        /// Требуемый метод для поддержки конструктора — не изменяйте 
        /// содержимое этого метода с помощью редактора кода.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.Windows.Forms.TreeNode treeNode1 = new System.Windows.Forms.TreeNode("Оружие");
            System.Windows.Forms.TreeNode treeNode2 = new System.Windows.Forms.TreeNode("Щиты");
            System.Windows.Forms.TreeNode treeNode3 = new System.Windows.Forms.TreeNode("Броня");
            System.Windows.Forms.TreeNode treeNode4 = new System.Windows.Forms.TreeNode("Талисманы");
            System.Windows.Forms.TreeNode treeNode5 = new System.Windows.Forms.TreeNode("Расходные вещи");
            System.Windows.Forms.TreeNode treeNode6 = new System.Windows.Forms.TreeNode("Руны");
            System.Windows.Forms.TreeNode treeNode7 = new System.Windows.Forms.TreeNode("Прочее");
            System.Windows.Forms.TreeNode treeNode8 = new System.Windows.Forms.TreeNode("Предметы", new System.Windows.Forms.TreeNode[] {
            treeNode1,
            treeNode2,
            treeNode3,
            treeNode4,
            treeNode5,
            treeNode6,
            treeNode7});
            System.Windows.Forms.TreeNode treeNode9 = new System.Windows.Forms.TreeNode("Анимированные тайлы");
            System.Windows.Forms.TreeNode treeNode10 = new System.Windows.Forms.TreeNode("Активаторы");
            System.Windows.Forms.TreeNode treeNode11 = new System.Windows.Forms.TreeNode("Двери");
            System.Windows.Forms.TreeNode treeNode12 = new System.Windows.Forms.TreeNode("Контейнеры");
            System.Windows.Forms.TreeNode treeNode13 = new System.Windows.Forms.TreeNode("Источники звука");
            System.Windows.Forms.TreeNode treeNode14 = new System.Windows.Forms.TreeNode("Структуры");
            System.Windows.Forms.TreeNode treeNode15 = new System.Windows.Forms.TreeNode("Объекты", new System.Windows.Forms.TreeNode[] {
            treeNode9,
            treeNode10,
            treeNode11,
            treeNode12,
            treeNode13,
            treeNode14});
            System.Windows.Forms.TreeNode treeNode16 = new System.Windows.Forms.TreeNode("Персонажи");
            System.Windows.Forms.TreeNode treeNode17 = new System.Windows.Forms.TreeNode("Заклинания");
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.файлToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.создатьКартуToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.загрузитьКартуToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.настройкиКартыToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.сохранитьКартуToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.сохранитьКакToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.мирToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.тайлыToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.миникартаToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.содержимоеToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.зоныОтдыхаToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.скриптыToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.создатьToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.открытьToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.персонажToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.фракцииToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.историяToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.диалогиToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.заданияToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.treeView1 = new System.Windows.Forms.TreeView();
            this.statusStrip1 = new System.Windows.Forms.StatusStrip();
            this.toolStripStatusLabel1 = new System.Windows.Forms.ToolStripStatusLabel();
            this.button1 = new System.Windows.Forms.Button();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.id = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.name = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.saveFileDialog1 = new System.Windows.Forms.SaveFileDialog();
            this.ассортиментToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.menuStrip1.SuspendLayout();
            this.statusStrip1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // menuStrip1
            // 
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.файлToolStripMenuItem,
            this.мирToolStripMenuItem,
            this.скриптыToolStripMenuItem,
            this.персонажToolStripMenuItem,
            this.историяToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(1243, 24);
            this.menuStrip1.TabIndex = 0;
            this.menuStrip1.Text = "menuStrip1";
            this.menuStrip1.ItemClicked += new System.Windows.Forms.ToolStripItemClickedEventHandler(this.menuStrip1_ItemClicked);
            this.menuStrip1.KeyDown += new System.Windows.Forms.KeyEventHandler(this.menuStrip1_KeyDown);
            // 
            // файлToolStripMenuItem
            // 
            this.файлToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.создатьКартуToolStripMenuItem,
            this.загрузитьКартуToolStripMenuItem,
            this.настройкиКартыToolStripMenuItem,
            this.сохранитьКартуToolStripMenuItem,
            this.сохранитьКакToolStripMenuItem});
            this.файлToolStripMenuItem.Name = "файлToolStripMenuItem";
            this.файлToolStripMenuItem.Size = new System.Drawing.Size(48, 20);
            this.файлToolStripMenuItem.Text = "Файл";
            // 
            // создатьКартуToolStripMenuItem
            // 
            this.создатьКартуToolStripMenuItem.Name = "создатьКартуToolStripMenuItem";
            this.создатьКартуToolStripMenuItem.Size = new System.Drawing.Size(170, 22);
            this.создатьКартуToolStripMenuItem.Text = "Создать карту";
            this.создатьКартуToolStripMenuItem.Click += new System.EventHandler(this.создатьКартуToolStripMenuItem_Click);
            // 
            // загрузитьКартуToolStripMenuItem
            // 
            this.загрузитьКартуToolStripMenuItem.Name = "загрузитьКартуToolStripMenuItem";
            this.загрузитьКартуToolStripMenuItem.Size = new System.Drawing.Size(170, 22);
            this.загрузитьКартуToolStripMenuItem.Text = "Загрузить карту";
            this.загрузитьКартуToolStripMenuItem.Click += new System.EventHandler(this.загрузитьКартуToolStripMenuItem_Click);
            // 
            // настройкиКартыToolStripMenuItem
            // 
            this.настройкиКартыToolStripMenuItem.Name = "настройкиКартыToolStripMenuItem";
            this.настройкиКартыToolStripMenuItem.Size = new System.Drawing.Size(170, 22);
            this.настройкиКартыToolStripMenuItem.Text = "Настройки карты";
            this.настройкиКартыToolStripMenuItem.Click += new System.EventHandler(this.настройкиКартыToolStripMenuItem_Click);
            // 
            // сохранитьКартуToolStripMenuItem
            // 
            this.сохранитьКартуToolStripMenuItem.Name = "сохранитьКартуToolStripMenuItem";
            this.сохранитьКартуToolStripMenuItem.Size = new System.Drawing.Size(170, 22);
            this.сохранитьКартуToolStripMenuItem.Text = "Сохранить карту";
            this.сохранитьКартуToolStripMenuItem.Click += new System.EventHandler(this.сохранитьКартуToolStripMenuItem_Click);
            // 
            // сохранитьКакToolStripMenuItem
            // 
            this.сохранитьКакToolStripMenuItem.Name = "сохранитьКакToolStripMenuItem";
            this.сохранитьКакToolStripMenuItem.Size = new System.Drawing.Size(170, 22);
            this.сохранитьКакToolStripMenuItem.Text = "Сохранить как";
            this.сохранитьКакToolStripMenuItem.Click += new System.EventHandler(this.сохранитьКакToolStripMenuItem_Click);
            // 
            // мирToolStripMenuItem
            // 
            this.мирToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.тайлыToolStripMenuItem,
            this.миникартаToolStripMenuItem,
            this.содержимоеToolStripMenuItem,
            this.зоныОтдыхаToolStripMenuItem});
            this.мирToolStripMenuItem.Name = "мирToolStripMenuItem";
            this.мирToolStripMenuItem.Size = new System.Drawing.Size(44, 20);
            this.мирToolStripMenuItem.Text = "Мир";
            // 
            // тайлыToolStripMenuItem
            // 
            this.тайлыToolStripMenuItem.Name = "тайлыToolStripMenuItem";
            this.тайлыToolStripMenuItem.Size = new System.Drawing.Size(146, 22);
            this.тайлыToolStripMenuItem.Text = "Тайлы";
            this.тайлыToolStripMenuItem.Click += new System.EventHandler(this.тайлыToolStripMenuItem_Click);
            // 
            // миникартаToolStripMenuItem
            // 
            this.миникартаToolStripMenuItem.Name = "миникартаToolStripMenuItem";
            this.миникартаToolStripMenuItem.Size = new System.Drawing.Size(146, 22);
            this.миникартаToolStripMenuItem.Text = "Миникарта";
            this.миникартаToolStripMenuItem.Click += new System.EventHandler(this.миникартаToolStripMenuItem_Click);
            // 
            // содержимоеToolStripMenuItem
            // 
            this.содержимоеToolStripMenuItem.Name = "содержимоеToolStripMenuItem";
            this.содержимоеToolStripMenuItem.Size = new System.Drawing.Size(146, 22);
            this.содержимоеToolStripMenuItem.Text = "Содержимое";
            this.содержимоеToolStripMenuItem.Click += new System.EventHandler(this.содержимоеToolStripMenuItem_Click);
            // 
            // зоныОтдыхаToolStripMenuItem
            // 
            this.зоныОтдыхаToolStripMenuItem.Name = "зоныОтдыхаToolStripMenuItem";
            this.зоныОтдыхаToolStripMenuItem.Size = new System.Drawing.Size(146, 22);
            this.зоныОтдыхаToolStripMenuItem.Text = "Зоны отдыха";
            this.зоныОтдыхаToolStripMenuItem.Click += new System.EventHandler(this.зоныОтдыхаToolStripMenuItem_Click);
            // 
            // скриптыToolStripMenuItem
            // 
            this.скриптыToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.создатьToolStripMenuItem,
            this.открытьToolStripMenuItem});
            this.скриптыToolStripMenuItem.Name = "скриптыToolStripMenuItem";
            this.скриптыToolStripMenuItem.Size = new System.Drawing.Size(68, 20);
            this.скриптыToolStripMenuItem.Text = "Скрипты";
            // 
            // создатьToolStripMenuItem
            // 
            this.создатьToolStripMenuItem.Name = "создатьToolStripMenuItem";
            this.создатьToolStripMenuItem.Size = new System.Drawing.Size(121, 22);
            this.создатьToolStripMenuItem.Text = "Создать";
            this.создатьToolStripMenuItem.Click += new System.EventHandler(this.создатьToolStripMenuItem_Click);
            // 
            // открытьToolStripMenuItem
            // 
            this.открытьToolStripMenuItem.Name = "открытьToolStripMenuItem";
            this.открытьToolStripMenuItem.Size = new System.Drawing.Size(121, 22);
            this.открытьToolStripMenuItem.Text = "Открыть";
            this.открытьToolStripMenuItem.Click += new System.EventHandler(this.открытьToolStripMenuItem_Click);
            // 
            // персонажToolStripMenuItem
            // 
            this.персонажToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.фракцииToolStripMenuItem,
            this.ассортиментToolStripMenuItem});
            this.персонажToolStripMenuItem.Name = "персонажToolStripMenuItem";
            this.персонажToolStripMenuItem.Size = new System.Drawing.Size(76, 20);
            this.персонажToolStripMenuItem.Text = "Персонаж";
            // 
            // фракцииToolStripMenuItem
            // 
            this.фракцииToolStripMenuItem.Name = "фракцииToolStripMenuItem";
            this.фракцииToolStripMenuItem.Size = new System.Drawing.Size(180, 22);
            this.фракцииToolStripMenuItem.Text = "Фракции";
            this.фракцииToolStripMenuItem.Click += new System.EventHandler(this.фракцииToolStripMenuItem_Click);
            // 
            // историяToolStripMenuItem
            // 
            this.историяToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.диалогиToolStripMenuItem,
            this.заданияToolStripMenuItem});
            this.историяToolStripMenuItem.Name = "историяToolStripMenuItem";
            this.историяToolStripMenuItem.Size = new System.Drawing.Size(66, 20);
            this.историяToolStripMenuItem.Text = "История";
            // 
            // диалогиToolStripMenuItem
            // 
            this.диалогиToolStripMenuItem.Name = "диалогиToolStripMenuItem";
            this.диалогиToolStripMenuItem.Size = new System.Drawing.Size(121, 22);
            this.диалогиToolStripMenuItem.Text = "Диалоги";
            this.диалогиToolStripMenuItem.Click += new System.EventHandler(this.диалогиToolStripMenuItem_Click);
            // 
            // заданияToolStripMenuItem
            // 
            this.заданияToolStripMenuItem.Name = "заданияToolStripMenuItem";
            this.заданияToolStripMenuItem.Size = new System.Drawing.Size(121, 22);
            this.заданияToolStripMenuItem.Text = "Задания";
            this.заданияToolStripMenuItem.Click += new System.EventHandler(this.заданияToolStripMenuItem_Click);
            // 
            // treeView1
            // 
            this.treeView1.Location = new System.Drawing.Point(12, 56);
            this.treeView1.Name = "treeView1";
            treeNode1.Name = "weapons";
            treeNode1.Text = "Оружие";
            treeNode2.Name = "shields";
            treeNode2.Text = "Щиты";
            treeNode3.Name = "armours";
            treeNode3.Text = "Броня";
            treeNode4.Name = "talismans";
            treeNode4.Text = "Талисманы";
            treeNode5.Name = "uses";
            treeNode5.Text = "Расходные вещи";
            treeNode6.Name = "runes";
            treeNode6.Text = "Руны";
            treeNode7.Name = "misc";
            treeNode7.Text = "Прочее";
            treeNode8.Name = "items";
            treeNode8.Text = "Предметы";
            treeNode9.Name = "animTiles";
            treeNode9.Text = "Анимированные тайлы";
            treeNode10.Name = "activators";
            treeNode10.Text = "Активаторы";
            treeNode11.Name = "doors";
            treeNode11.Text = "Двери";
            treeNode12.Name = "chest";
            treeNode12.Text = "Контейнеры";
            treeNode13.Name = "sounds";
            treeNode13.Text = "Источники звука";
            treeNode14.Name = "structures";
            treeNode14.Text = "Структуры";
            treeNode15.Name = "objects";
            treeNode15.Text = "Объекты";
            treeNode16.Name = "chars";
            treeNode16.Text = "Персонажи";
            treeNode17.Name = "spells";
            treeNode17.Text = "Заклинания";
            this.treeView1.Nodes.AddRange(new System.Windows.Forms.TreeNode[] {
            treeNode8,
            treeNode15,
            treeNode16,
            treeNode17});
            this.treeView1.Size = new System.Drawing.Size(155, 675);
            this.treeView1.TabIndex = 1;
            this.treeView1.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.treeView1_AfterSelect);
            this.treeView1.KeyDown += new System.Windows.Forms.KeyEventHandler(this.menuStrip1_KeyDown);
            // 
            // statusStrip1
            // 
            this.statusStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripStatusLabel1});
            this.statusStrip1.Location = new System.Drawing.Point(0, 735);
            this.statusStrip1.Name = "statusStrip1";
            this.statusStrip1.Size = new System.Drawing.Size(1243, 22);
            this.statusStrip1.TabIndex = 2;
            this.statusStrip1.Text = "statusStrip1";
            // 
            // toolStripStatusLabel1
            // 
            this.toolStripStatusLabel1.Name = "toolStripStatusLabel1";
            this.toolStripStatusLabel1.Size = new System.Drawing.Size(118, 17);
            this.toolStripStatusLabel1.Text = "toolStripStatusLabel1";
            this.toolStripStatusLabel1.Click += new System.EventHandler(this.toolStripStatusLabel1_Click);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(12, 27);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(155, 23);
            this.button1.TabIndex = 4;
            this.button1.Text = "Добавить";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // timer1
            // 
            this.timer1.Enabled = true;
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // openFileDialog1
            // 
            this.openFileDialog1.FileName = "openFileDialog1";
            this.openFileDialog1.InitialDirectory = "C:\\Program Files\\Microsoft Visual Studio\\2022\\Community\\Common7\\IDE\\res\\data\\worl" +
    "d\\maps";
            this.openFileDialog1.FileOk += new System.ComponentModel.CancelEventHandler(this.openFileDialog1_FileOk);
            // 
            // dataGridView1
            // 
            this.dataGridView1.AllowUserToAddRows = false;
            this.dataGridView1.AllowUserToDeleteRows = false;
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.id,
            this.name});
            this.dataGridView1.Location = new System.Drawing.Point(174, 27);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.ReadOnly = true;
            this.dataGridView1.Size = new System.Drawing.Size(357, 704);
            this.dataGridView1.TabIndex = 6;
            this.dataGridView1.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellClick);
            this.dataGridView1.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellContentClick);
            this.dataGridView1.CellMouseUp += new System.Windows.Forms.DataGridViewCellMouseEventHandler(this.dataGridView1_CellMouseUp);
            // 
            // id
            // 
            this.id.HeaderText = "ИД";
            this.id.Name = "id";
            this.id.ReadOnly = true;
            // 
            // name
            // 
            this.name.HeaderText = "Название";
            this.name.Name = "name";
            this.name.ReadOnly = true;
            // 
            // ассортиментToolStripMenuItem
            // 
            this.ассортиментToolStripMenuItem.Name = "ассортиментToolStripMenuItem";
            this.ассортиментToolStripMenuItem.Size = new System.Drawing.Size(180, 22);
            this.ассортиментToolStripMenuItem.Text = "Ассортимент";
            this.ассортиментToolStripMenuItem.Click += new System.EventHandler(this.ассортиментToolStripMenuItem_Click);
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackColor = System.Drawing.Color.Transparent;
            this.pictureBox1.Location = new System.Drawing.Point(537, 27);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(704, 704);
            this.pictureBox1.TabIndex = 5;
            this.pictureBox1.TabStop = false;
            this.pictureBox1.Click += new System.EventHandler(this.pictureBox1_Click);
            this.pictureBox1.MouseClick += new System.Windows.Forms.MouseEventHandler(this.pictureBox1_MouseClick);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1243, 757);
            this.Controls.Add(this.dataGridView1);
            this.Controls.Add(this.pictureBox1);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.statusStrip1);
            this.Controls.Add(this.treeView1);
            this.Controls.Add(this.menuStrip1);
            this.DoubleBuffered = true;
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "Form1";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.Paint += new System.Windows.Forms.PaintEventHandler(this.Form1_Paint);
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.menuStrip1_KeyDown);
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.statusStrip1.ResumeLayout(false);
            this.statusStrip1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem файлToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem создатьКартуToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem загрузитьКартуToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem сохранитьКартуToolStripMenuItem;
        private System.Windows.Forms.TreeView treeView1;
        private System.Windows.Forms.StatusStrip statusStrip1;
        private System.Windows.Forms.ToolStripStatusLabel toolStripStatusLabel1;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.ToolStripMenuItem мирToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem тайлыToolStripMenuItem;
        private System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.OpenFileDialog openFileDialog1;
        private System.Windows.Forms.ToolStripMenuItem миникартаToolStripMenuItem;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.DataGridViewTextBoxColumn id;
        private System.Windows.Forms.DataGridViewTextBoxColumn name;
        private System.Windows.Forms.ToolStripMenuItem содержимоеToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem скриптыToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem создатьToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem открытьToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem зоныОтдыхаToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem персонажToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem фракцииToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem историяToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem диалогиToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem заданияToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem настройкиКартыToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem сохранитьКакToolStripMenuItem;
        private System.Windows.Forms.SaveFileDialog saveFileDialog1;
        private System.Windows.Forms.ToolStripMenuItem ассортиментToolStripMenuItem;
    }
}

