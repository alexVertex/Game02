package game.world.map;

import java.io.Serial;
import java.io.Serializable;

public class ResortArea implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public double locX;
    public double locY;
    public double rast;
    public String name;
    public String ID;
    public String mapName;
    public String mapID;
    public String miniMapID;

    public ResortArea(String id, String name, double X, double Y, double R){
        locX = X;
        locY = Y;
        rast = R;
        this.name = name;
        ID = id;
    }

    public ResortArea(ResortArea copy, String MapName, String MapID, String MiniMapID){
        this.locX = copy.locX;
        this.locY = copy.locY;
        this.rast = copy.rast;
        this.name = copy.name;
        this.ID = copy.ID;
        mapName = MapName;
        mapID = MapID;
        miniMapID = MiniMapID;
    }
}
