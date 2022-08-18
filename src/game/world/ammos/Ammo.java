package game.world.ammos;

import engine.core.Mathp;
import engine.render.Sprite;
import engine.window.Actor;
import engine.helpers.SpriteHelper;
import engine.helpers.StarterHelper;
import game.magic.Effect;
import game.players.AutoControl;
import game.players.Battle;
import game.players.Player;

public class Ammo extends Actor {

    private static final int STYLE_EXPLODE = 1;
    private static final int STYLE_EXPLODE_LONG = 2;

    static ProjectileData arrow = new ProjectileData("spells/projectiles/arrow1", 20, 20,1,-1,true);
    static ProjectileData fireBolt = new ProjectileData("spells/projectiles/fireProjectile", 64, 64,4,0,true);
    static ProjectileData fireStaying = new ProjectileData("spells/projectiles/fireProjectile", 64, 64,4,1,false);


    static ProjectileData fireExplode = new ProjectileData("spells/projectiles/fireExplode1", 64, 64,16,true);
    static ProjectileData fireExplodeLong = new ProjectileData("spells/projectiles/fireExplode1", 64, 64,16,false);

    static ProjectileData[] projectiles = {arrow,fireBolt,fireStaying};
    static ProjectileData[] explodes = {fireExplode,fireExplodeLong};
    private boolean workFirst;
    private int time;

    public Ammo(ProjectileData explode, double x, double y) {
        super();
        sprite = new Sprite(explode.texture,x,y,16,explode.sizeX,explode.sizeY,1);
        ammoStyle = STYLE_EXPLODE;
        this.perFrame = explode.perFrame;
        maxFrame = explode.frames;
    }

    public double getAngle() {
        return angle;
    }

    static class ProjectileData{
        String texture;
        double sizeX, sizeY;
        int frames;
        float perFrame;
        public int explode;
        public boolean oneTIme;
        public boolean workFirst;

        public ProjectileData(String t, double x, double y,int f,int explodeIndex,boolean workFirst){
            texture = t;
            sizeX = x;
            sizeY = y;
            frames = f;
            perFrame = 1.0f / (float) frames;
            explode = explodeIndex;
            this.workFirst = workFirst;
        }
        public ProjectileData(String t, double x, double y,int f,boolean oneTime){
            texture = t;
            sizeX = x;
            sizeY = y;
            frames = f;
            perFrame = 1.0f / (float) frames;
            this.oneTIme = oneTime;
        }
    }
    private Effect effect = null;
    Player shooter;
    Sprite sprite;
    double angle;
    double damage;
    int damageType;
    int dist = -1;
    int radius = 0;
    boolean hasDist = false;
    int ammoStyle = 0;

    double speed = 4;
    double animProgress = 0;
    int curFrame = 0;
    int maxFrame = 0;
    float perFrame;
    int explodeIndex;
    int timeStay = 0;
    public Ammo(Player shot,double x, double y, double angle, int projectileIndex,double damage, int damageType, int dist){
        ProjectileData data = projectiles[projectileIndex];
        sprite = new Sprite(data.texture,x,y,15,data.sizeX,data.sizeY,1);
        sprite.setAngle(angle);
        locX = x;
        locY = y;
        this.angle = angle;
        shooter = shot;
        this.damage = damage;
        this.damageType = damageType;
        this.dist = dist;
        hasDist = true;
        this.perFrame = data.perFrame;
        maxFrame = data.frames;
        explodeIndex = data.explode;
    }

    public Ammo(Player shot, double x, double y, double angle, int projectileIndex, Effect effect,int dist, int radius) {
        super();
        ProjectileData data = projectiles[projectileIndex];
        sprite = new Sprite(data.texture,x,y,15,data.sizeX,data.sizeY,1);
        sprite.setAngle(angle);
        locX = x;
        locY = y;
        this.angle = angle;
        shooter = shot;
        this.effect = new Effect( effect.id,effect.power1,effect.time);
        this.effect.owner = shot;
        this.dist = dist;
        this.radius = radius;
        if(dist > 0) {
            hasDist = true;
        }
        this.time = effect.time;
        this.perFrame = data.perFrame;
        maxFrame = data.frames;
        explodeIndex = data.explode;
        workFirst = data.workFirst;
        timeStay = time;
    }


    @Override
    public void init() {
        if(sprite.emptyModel()) {
            sprite.createModel();
        }
        SpriteHelper.addSprite(sprite);
    }

    @Override
    public void dispose() {
        SpriteHelper.removeSprite(sprite);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    public void delete(){
        if(ammoStyle == 0) {
            if(explodeIndex == -1) {
                StarterHelper.game.current.removeAmmo(this);
            } else {

                ProjectileData data = explodes[explodeIndex];
                if(data.oneTIme){
                    ammoStyle = STYLE_EXPLODE;
                } else {
                    ammoStyle = STYLE_EXPLODE_LONG;
                    effect.power2 = effect.time;
                    effect.time = 1;
                    workFirst = true;
                }
                animProgress = 0;
                sprite.texture = data.texture;
                maxFrame = data.frames;
                perFrame = data.perFrame;
                sprite.leftTex = 0;
                sprite.rightTex = perFrame;
                sprite.replaceModelTID();
            }
        } else if(ammoStyle == STYLE_EXPLODE){
            StarterHelper.game.current.removeAmmo(this);

        }
    }

    @Override
    public void use() {

    }

    public void logic() {
        if(ammoStyle == STYLE_EXPLODE){
            animProgress += 0.02;
            if(animProgress >= 1){
                StarterHelper.game.current.removeAmmo(this);
            }
            int curFrame = (int) (maxFrame*animProgress);
            sprite.leftTex = curFrame * perFrame;
            sprite.rightTex = (curFrame+1) * perFrame;
            sprite.replaceModelTID();
            return;
        }
        if(ammoStyle == STYLE_EXPLODE_LONG){
            animProgress += 0.02;
            if(animProgress >= 1){
                animProgress-=1;
            }
            int curFrame = (int) (maxFrame*animProgress);
            sprite.leftTex = curFrame * perFrame;
            sprite.rightTex = (curFrame+1) * perFrame;
            sprite.replaceModelTID();
            checkInZone();
            timeStay--;
            if(timeStay < 0){
                StarterHelper.game.current.removeAmmo(this);
            }
            return;
        }


        animProgress += 0.02;
        if(animProgress >= 1){
            animProgress--;
        }
        int curFrame = (int) (maxFrame*animProgress);
        sprite.leftTex = curFrame * perFrame;
        sprite.rightTex = (curFrame+1) * perFrame;
        sprite.replaceModelTID();

        locX += Math.cos(angle)*speed;
        locY -= Math.sin(angle)*speed;
        sprite.setPos(locX,locY);
        for(Player el : StarterHelper.game.getPlayers()){
            if(el.equals(shooter)) continue;

            double rast = Mathp.rast(el.getX(),el.getY(),locX,locY);
            if(rast < 20){
                delete();
                if(effect == null) {
                    Battle.takeDamage(shooter, el, damage, damageType, locX, locY,0);
                } else {
                    if(radius == 0) {
                        effect.wearOn(el,shooter,true);
                    } else {
                        checkInZone();
                    }
                }
            }
            if(rast < 100){
                if(shooter.equals(StarterHelper.game.getPlayer())) {
                    AutoControl.setHearAmmo(el, this);
                }
            }
        }
        boolean collided = StarterHelper.game.testCollide(locX,locY);
        if(collided){
            delete();
            if(radius > 0){
                checkInZone();
            }
        }
        if(hasDist){
            dist -= speed;
            if(dist < 0){
                delete();
                if(radius > 0){
                    checkInZone();
                }
            }
        }
    }

    private void checkInZone(){
        if(workFirst) {
            for (Player el : StarterHelper.game.getPlayers()) {
                if (Mathp.rast(el.getX(), el.getY(), locX, locY) < radius) {
                    effect.wearOn(el, shooter,true);
                }
            }
        }
    }

    @Override
    public void doStuff(String command, String params) {

    }
}
