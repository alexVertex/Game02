package game.players;

import engine.core.Mathp;
import engine.helpers.StarterHelper;

import java.util.*;

public class PathFind {

    static class Point {
        public int x;
        public int y;
        public Point from;
        public Point(int X, int Y, Point From){
            x = X;
            y = Y;
            from = From;
        }
    }
    private static int[][] net;
    public static void initNet(int sizeX, int sizeY){
        net = new int[sizeX][sizeY];
    }
    public static int[] pathFind(int startX, int startY, int finishX,int finishY){
        double testX = startX;
        double testY = startY;
        startX/=32;
        startY/=32;
        int finishXDiv = finishX /32;
        int finishYDiv = finishY /32;
        int startXDiv = startX;
        int startYDiv = startY;
        finishX/=32;
        finishY/=32;
        for (int[] ints : net) {
            Arrays.fill(ints, 0);
        }
        Point fin = null;
        List<Point> tests = new ArrayList<>();
        tests.add(new Point(startX,startY,null));
        setNet(startX,startY,1);
        for(int i = 0; i < tests.size();){
            Point point = tests.get(0);
            if(point.x == finishX && point.y == finishY){
                fin = point;
                break;
            }
            tests.remove(0);
            Point pointW = new Point(point.x-1,point.y,point);
            Point pointE = new Point(point.x+1,point.y,point);
            Point pointN = new Point(point.x,point.y+1,point);
            Point pointS = new Point(point.x,point.y-1,point);
            if(isPointOK(pointW)){
                tests.add(pointW);
            }
            if(isPointOK(pointE)){
                tests.add(pointE);
            }
            if(isPointOK(pointS)){
                tests.add(pointS);
            }
            if(isPointOK(pointN)){
                tests.add(pointN);
            }
            tests.sort(Comparator.comparingDouble(point2 -> Mathp.rast(point2.x, point2.y, finishXDiv, finishYDiv)+Mathp.rast(point2.x, point2.y, startXDiv, startYDiv)));
        }

        List<int[]> answers = new ArrayList<>();
        while (fin != null){
            answers.add(new int[]{fin.x,fin.y});
            fin = fin.from;
        }

        Collections.reverse(answers);
        if(answers.size()>0)
            answers.remove(0);

        if(answers.size()>0) {

            int[] save = answers.get(answers.size()-1);
            for(int i = 1; i < answers.size();i++){
                int[] test = answers.get(i);
                boolean noObstacle = StarterHelper.game.current.noObstacleWalk(testX,testY,test[0]*32,test[1]*32);
                if(!noObstacle){
                    save = answers.get(i-1);
                    break;
                }
            }
            return save;
        }
        return null;
    }

    private static boolean isPointOK(Point point) {
        if(point.x <= -1) return false;
        if(point.y <= -1) return false;
        if(point.x >= net.length) return false;
        if(point.y >= net[0].length) return false;
        if(net[point.x][point.y] == 0 && StarterHelper.game.current.getPass(point.x,point.y) > 0){
            net[point.x][point.y] = 1;
            return true;
        }
        return false;
    }
    public static void setNet(int i, int j, int val){
        if(i < 0) return;
        if(j < 0) return;
        if(i >= net.length) return;
        if(j >= net[0].length) return;
        net[i][j] = val;
    }
}
