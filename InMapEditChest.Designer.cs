namespace Game02Editor
{
    partial class InMapEditChest
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.groupBox5 = new System.Windows.Forms.GroupBox();
            this.button6 = new System.Windows.Forms.Button();
            this.button5 = new System.Windows.Forms.Button();
            this.loot = new System.Windows.Forms.DataGridView();
            this.lootID = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.lootName = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.itemNew = new System.Windows.Forms.TextBox();
            this.label31 = new System.Windows.Forms.Label();
            this.goldMin = new System.Windows.Forms.NumericUpDown();
            this.groupBox5.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.loot)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.goldMin)).BeginInit();
            this.SuspendLayout();
            // 
            // groupBox5
            // 
            this.groupBox5.Controls.Add(this.button6);
            this.groupBox5.Controls.Add(this.button5);
            this.groupBox5.Controls.Add(this.loot);
            this.groupBox5.Controls.Add(this.itemNew);
            this.groupBox5.Controls.Add(this.label31);
            this.groupBox5.Controls.Add(this.goldMin);
            this.groupBox5.Location = new System.Drawing.Point(12, 12);
            this.groupBox5.Name = "groupBox5";
            this.groupBox5.Size = new System.Drawing.Size(218, 332);
            this.groupBox5.TabIndex = 39;
            this.groupBox5.TabStop = false;
            this.groupBox5.Text = "Трофеи";
            // 
            // button6
            // 
            this.button6.Location = new System.Drawing.Point(130, 301);
            this.button6.Name = "button6";
            this.button6.Size = new System.Drawing.Size(75, 23);
            this.button6.TabIndex = 41;
            this.button6.Text = "Удалить";
            this.button6.UseVisualStyleBackColor = true;
            this.button6.Click += new System.EventHandler(this.button6_Click);
            // 
            // button5
            // 
            this.button5.Location = new System.Drawing.Point(9, 301);
            this.button5.Name = "button5";
            this.button5.Size = new System.Drawing.Size(75, 23);
            this.button5.TabIndex = 40;
            this.button5.Text = "Добавить";
            this.button5.UseVisualStyleBackColor = true;
            this.button5.Click += new System.EventHandler(this.button5_Click);
            // 
            // loot
            // 
            this.loot.AllowUserToAddRows = false;
            this.loot.AllowUserToDeleteRows = false;
            this.loot.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.loot.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.lootID,
            this.lootName});
            this.loot.Location = new System.Drawing.Point(9, 40);
            this.loot.Name = "loot";
            this.loot.ReadOnly = true;
            this.loot.Size = new System.Drawing.Size(196, 229);
            this.loot.TabIndex = 37;
            this.loot.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.loot_CellContentClick);
            this.loot.CellMouseClick += new System.Windows.Forms.DataGridViewCellMouseEventHandler(this.loot_CellMouseClick);
            // 
            // lootID
            // 
            this.lootID.HeaderText = "ИД";
            this.lootID.Name = "lootID";
            this.lootID.ReadOnly = true;
            // 
            // lootName
            // 
            this.lootName.HeaderText = "Имя";
            this.lootName.Name = "lootName";
            this.lootName.ReadOnly = true;
            // 
            // itemNew
            // 
            this.itemNew.Location = new System.Drawing.Point(9, 275);
            this.itemNew.Name = "itemNew";
            this.itemNew.Size = new System.Drawing.Size(196, 20);
            this.itemNew.TabIndex = 36;
            // 
            // label31
            // 
            this.label31.AutoSize = true;
            this.label31.Location = new System.Drawing.Point(6, 19);
            this.label31.Name = "label31";
            this.label31.Size = new System.Drawing.Size(43, 13);
            this.label31.TabIndex = 22;
            this.label31.Text = "Золото";
            // 
            // goldMin
            // 
            this.goldMin.Location = new System.Drawing.Point(131, 14);
            this.goldMin.Maximum = new decimal(new int[] {
            999999,
            0,
            0,
            0});
            this.goldMin.Name = "goldMin";
            this.goldMin.Size = new System.Drawing.Size(74, 20);
            this.goldMin.TabIndex = 16;
            this.goldMin.ValueChanged += new System.EventHandler(this.goldMin_ValueChanged);
            // 
            // InMapEditChest
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(240, 352);
            this.Controls.Add(this.groupBox5);
            this.Name = "InMapEditChest";
            this.Text = "Содержание сундука";
            this.Load += new System.EventHandler(this.InMapEditChest_Load);
            this.groupBox5.ResumeLayout(false);
            this.groupBox5.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.loot)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.goldMin)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox groupBox5;
        private System.Windows.Forms.Button button6;
        private System.Windows.Forms.Button button5;
        private System.Windows.Forms.DataGridView loot;
        private System.Windows.Forms.DataGridViewTextBoxColumn lootID;
        private System.Windows.Forms.DataGridViewTextBoxColumn lootName;
        private System.Windows.Forms.TextBox itemNew;
        private System.Windows.Forms.Label label31;
        private System.Windows.Forms.NumericUpDown goldMin;
    }
}