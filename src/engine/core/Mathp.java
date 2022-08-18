package engine.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mathp {

    static   Random r = new Random();
    public static boolean isPointInRect(double rlx, double rly, double rsx, double rsy, double px, double py) {
        return Math.abs(rlx - px) < rsx / 2 && Math.abs(rly - py) < rsy / 2;
    }

    public static double rast(double x, double y, double x1, double y1) {
        return Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1));
    }

    public static int random(int begin, int end) {
        return r.nextInt(end-begin)+begin;
    }
    public static double angleBetwinAngles(double one, double two){
        double angleRotate = Math.abs(one - two);
        if(angleRotate > Math.PI)
            angleRotate = 2*Math.PI - angleRotate;
        return angleRotate;
    }
    public static double random() {
        return r.nextDouble();
    }

    public static boolean isPointInRect(int[] rectangle, double X, double Y) {
        return Math.abs(rectangle[0] - X) < rectangle[2] / 2.0 && Math.abs(rectangle[1] - Y) < rectangle[3] / 2.0;
    }

    private static int sign (int x) {
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
        //возвращает 0, если аргумент (x) равен нулю; -1, если x < 0 и 1, если x > 0.
    }

    public static int[][] poses (int xstart, int ystart, int xend, int yend)
    {
        List<Integer> xes = new ArrayList<>();
        List<Integer> yes = new ArrayList<>();

        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;

        dx = xend - xstart;//проекция на ось икс
        dy = yend - ystart;//проекция на ось игрек

        incx = sign(dx);
        /*
         * Определяем, в какую сторону нужно будет сдвигаться. Если dx < 0, т.е. отрезок идёт
         * справа налево по иксу, то incx будет равен -1.
         * Это будет использоваться в цикле постороения.
         */
        incy = sign(dy);
        /*
         * Аналогично. Если рисуем отрезок снизу вверх -
         * это будет отрицательный сдвиг для y (иначе - положительный).
         */

        if (dx < 0) dx = -dx;//далее мы будем сравнивать: "if (dx < dy)"
        if (dy < 0) dy = -dy;//поэтому необходимо сделать dx = |dx|; dy = |dy|
        //эти две строчки можно записать и так: dx = Math.abs(dx); dy = Math.abs(dy);

        if (dx > dy)
        //определяем наклон отрезка:
        {
            /*
             * Если dx > dy, то значит отрезок "вытянут" вдоль оси икс, т.е. он скорее длинный, чем высокий.
             * Значит в цикле нужно будет идти по икс (строчка el = dx;), значит "протягивать" прямую по иксу
             * надо в соответствии с тем, слева направо и справа налево она идёт (pdx = incx;), при этом
             * по y сдвиг такой отсутствует.
             */
            pdx = incx;	pdy = 0;
            es = dy;	el = dx;
        }
        else//случай, когда прямая скорее "высокая", чем длинная, т.е. вытянута по оси y
        {
            pdx = 0;	pdy = incy;
            es = dx;	el = dy;//тогда в цикле будем двигаться по y
        }

        x = xstart;
        y = ystart;
        err = el/2;
        xes.add(x);
        yes.add(y);
        //все последующие точки возможно надо сдвигать, поэтому первую ставим вне цикла

        for (int t = 0; t < el; t++)//идём по всем точкам, начиная со второй и до последней
        {
            err -= es;
            if (err < 0)
            {
                err += el;
                x += incx;//сдвинуть прямую (сместить вверх или вниз, если цикл проходит по иксам)
                y += incy;//или сместить влево-вправо, если цикл проходит по y
            }
            else
            {
                x += pdx;//продолжить тянуть прямую дальше, т.е. сдвинуть влево или вправо, если
                y += pdy;//цикл идёт по иксу; сдвинуть вверх или вниз, если по y
            }

            xes.add(x);
            yes.add(y);
        }
        int[][] res = new int[xes.size()][2];
        for(int i = 0; i < res.length;i++){
            res[i][0] = xes.get(i);
            res[i][1] = yes.get(i);
        }
        return res;
    }
}
