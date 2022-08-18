package game.players;

import engine.core.Mathp;
import engine.helpers.StarterHelper;
import engine.window.Window;
import game.world.decals.Decals;

import static engine._SetsStrings.*;

public class Moving {

    public static void move(Player player){
        if(player.coloredStepTime>0){
            player.coloredStepTime--;
            if(player.coloredStepTime == 0){
                player.stepsColor[0] = 0;
                player.stepsColor[1] = 0;
                player.stepsColor[2] = 0;
            }
        }

        collideWall(player);
        if(player.onGuard){
            player.speedX *= D_PLAYER_SPEED_GUARD_KOEF;
            player.speedY *= D_PLAYER_SPEED_GUARD_KOEF;
        }
        if(player.speedX != 0 && player.speedY != 0){
            player.speedX *= D_PLAYER_SPEED_DIOGONAL_KOEF;
            player.speedY *= D_PLAYER_SPEED_DIOGONAL_KOEF;
        }

        double loadOtn = getLoadLost(player);
        if(loadOtn == D_PLAYER_LOAD_MAX && player.moveMode >= D_MANUAL_SPEED_MODE_RUN){
            player.moveMode = D_MANUAL_SPEED_MODE_WALK;
        }
        if(player.moveMode >= D_MANUAL_SPEED_MODE_RUN && (player.speedX != 0 || player.speedY != 0)){
            player.staminaLoss(D_PLAYER_STAMINA_RUN_LOSS);
            if(player.stamina == 0){
                player.moveMode = D_MANUAL_SPEED_MODE_WALK;
            }
        }
        player.speedX *= player.moveMode;
        player.speedY *= player.moveMode;

        player.locX += player.speedX;
        player.locY += player.speedY;
        player.movedRast = Math.sqrt(player.speedX*player.speedX+player.speedY*player.speedY);

        player.sprite.setPos(player.locX,player.locY);
        double rast = D_PLAYER_GUARD_SPRITE_OFFSET;
        player.attackProj.setPos(player.locX+Math.cos(player.getAngle())*rast,player.locY-Math.sin(player.getAngle())*rast);
        player.guardProj.setPos(player.locX,player.locY);
        player.guardProj.setAngle(player.getAngle()+1.57);
        if(player.speedX == 0 && player.speedY == 0){
            player.setCutX(1);
            player.walkProgress = 0;
            player.stepTime = 0;
        } else {
            player.walkProgress += F_PLAYER_WALK_PROGRESS_ADD;
            if(player.walkProgress > 1){
                player.walkProgress -= 1;
            }
            if(player.stepTime < 0) {
                if(Window.getSets().showDecals == 1) {
                    double angleMove = Math.atan2(player.speedY,player.speedX);
                    angleMove += player.leftStep ? D_PLAYER_STEP_ANGLE_OFFSET : -D_PLAYER_STEP_ANGLE_OFFSET;
                    player.leftStep = !player.leftStep;
                    double x = player.getX() + Math.cos(angleMove) * D_PLAYER_STEP_RAST_OFFSET;
                    double y = player.getY() + Math.sin(angleMove) * D_PLAYER_STEP_RAST_OFFSET;
                    Decals step = new Decals(player.steps, x, y, Math.atan2(-player.speedY, player.speedX) + 1.57, I_ACTOR_DEF_SIZE);
                    step.getSprite().red = (float) player.stepsColor[0];
                    step.getSprite().green = (float) player.stepsColor[1];
                    step.getSprite().blue = (float) player.stepsColor[2];

                    StarterHelper.game.current.addDecal(step);
                }
                player.stepTime += I_PLAYER_STEP_TIME_WAIT;
            } else {
                player.stepTime-=player.movedRast;
            }
        }
        if(player.walkProgress>0 && player.walkProgress < D_PLAYER_WALK_PROGRESS_HALF_WAY){
            player.setCutX(0);
        } else if(player.walkProgress > D_PLAYER_WALK_PROGRESS_HALF_WAY){
            player.setCutX(2);
        }
        recovery(player);
        player.speedX = 0;
        player.speedY = 0;
        player.moveMode = D_MANUAL_SPEED_MODE_WALK;

    }

    private static void collideWall(Player player){
        boolean playerCollide = false;
        for(Player el : StarterHelper.game.getPlayers()){
            if(el.equals(player)) continue;
            if(Mathp.rast(player.getX()+player.speedX,player.getY()+player.speedY,el.getX(),el.getY()) < 20){
                double angle = Math.atan2(el.getY()-player.getY(),el.getX()-player.getX())+1.57;
                player.speedX = -Math.cos(angle) * 1;
                player.speedY = -Math.sin(angle) * 1;
                playerCollide = true;
            }
        }
        double[] collided = StarterHelper.game.testCollide(player.locX,player.locY,player.speedX,player.speedY);
        boolean wallCollided = collided[0] != player.speedX && collided[1] != player.speedY;
        player.speedX = collided[0];
        player.speedY = collided[1];
        if(wallCollided && playerCollide){
            player.speedX = 0;
            player.speedY = 0;
        }
    }

    private static void recovery(Player player) {
        if((player.speedX != 0 || player.speedY != 0) && player.moveMode == 2 || player.hitProgress != 0){
            player.recoveryProgress = 0;
            return;
        }
        if(player.recoveryProgress < 1){
            player.recoveryProgress+=D_PLAYER_STAMINA_RECOVERY_PROGRESS_ADD;
            return;
        }
        double addStamina = D_PLAYER_STAMINA_RECOVERY;
        addStamina *= player.onGuard ? D_PLAYER_STAMINA_RECOVERY_GUARD_KOEF : 1;
        addStamina *= getLoadLost(player);
        player.stamina+=addStamina;
        if(player.stamina>player.staminaMax){
            player.stamina = player.staminaMax;
        }
    }

    private static double getLoadLost(Player player) {
        double otn =  (float)player.load/(float)player.loadMax;
        if(otn < D_PLAYER_LOAD_MAX) otn = 0;
        if(otn > 1) otn = 1;
        return 1 - otn * D_PLAYER_LOAD_MAX;
    }
}
