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
    public partial class Minimap : Form
    {
        bool moving = false;
        Form1 parent;
        public Minimap(Form1 par)
        {
            parent = par;
            InitializeComponent();
           
        }

        private void Minimap_Load(object sender, EventArgs e)
        {

        }

        private void Minimap_Paint(object sender, PaintEventArgs e)
        {
            if (Form1.map.minimap != "")
            {
                Bitmap map = new Bitmap("res/tex/minimaps/"+ Form1.map.minimap + ".png");
                e.Graphics.DrawImage(map, 12, 12,256,256);
            }
            Bitmap bitmap = new Bitmap("res/tex/greedSquare1.png");
            double sizeX = Form1.map.sizeX;
            double sizeY = Form1.map.sizeY;

            double sx = 22.0/Form1.map.sizeX*256;
            double sy = 22.0/Form1.map.sizeY*256;
            double x = Form1.map.camX/(sizeX-22)*(256- sx) +12;
            double y = Form1.map.camY/(sizeY-22)*(256- sy) +12;
            
            e.Graphics.DrawImage(bitmap, (int)x, (int)y, (int)sx, (int)sy);
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            
            
            Refresh();
        }

        private void Minimap_MouseDown(object sender, MouseEventArgs e)
        {
            double x = e.X;
            double y = e.Y;
            if (x >= 12 && y >= 12 && x <= 278 && y <= 278)
            {
                x -= 12;
                y -= 12;
                Form1.map.camX = (int)(x / 256.0 * (Form1.map.sizeX - 22));
                Form1.map.camY = (int)(y / 256.0 * (Form1.map.sizeY - 22));

            }
            moving = true;
        }

        private void Minimap_MouseUp(object sender, MouseEventArgs e)
        {
            moving = false;
        }
    }
}
