package game.players;

import engine.helpers.CameraHelper;
import engine.helpers.ControlHelper;
import engine.input.MainInput;

import static engine._SetsStrings.*;

public class ManualControl {

    public static void control(MainInput input, Player player){
        double cursorX = input.getMouseX()- CameraHelper.getX();
        double cursorY = input.getMouseY()- CameraHelper.getY();
        player.setLook(cursorX,cursorY);
        double speedX = 0;
        double speedY = 0;
        if(input.isKeyHolded(ControlHelper.MOVE_UP)){
            speedY = 1;
        }
        if(input.isKeyHolded(ControlHelper.MOVE_DOWN)){
            speedY = -1;
        }
        if(input.isKeyHolded(ControlHelper.MOVE_LEFT)){
            speedX = -1;
        }
        if(input.isKeyHolded(ControlHelper.MOVE_RIGHT)){
            speedX = 1;
        }
        if(input.isKeyHolded(ControlHelper.MOVE_RUN)){
            player.setMoveMode(D_MANUAL_SPEED_MODE_RUN);
        }
        if(input.isKeyHolded(ControlHelper.MOVE_SNEAK)){
            player.setMoveMode(D_MANUAL_SPEED_MODE_SNEAK);
        }
        if(input.isKeyHolded(ControlHelper.HIT)){
            Battle.startHit(player);
        }
        if(input.isKeyHolded(ControlHelper.GUARD)){
            Battle.startGuard(player);
        }
        if(input.isKeyJustPressed(ControlHelper.SWITCH_WEAPON)){
            Equip.switchWeapon(player);
        }
        if(input.isKeyJustPressed(ControlHelper.SWITCH_SHIELD)){
            Equip.switchShield(player);
        }
        if(input.isKeyJustPressed(ControlHelper.SWITCH_ITEM)){
            player.switchItem(I_MANUAL_SWITCH_ITEM);
        }
        if(input.isKeyJustPressed(ControlHelper.SWITCH_SPELL1)){
            player.switchItem(I_MANUAL_SWITCH_SPELL1);
        }
        if(input.isKeyJustPressed(ControlHelper.SWITCH_SPELL2)){
            player.switchItem(I_MANUAL_SWITCH_SPELL2);
        }
        if(input.isKeyJustPressed(ControlHelper.USE_ITEM)){
            player.useItem();
        }
        if(input.isKeyJustPressed(ControlHelper.USE_SPELL1)){
            player.useSpell(true);
        }
        if(input.isKeyJustPressed(ControlHelper.USE_SPELL2)){
            player.useSpell(false);
        }
        player.setMove(speedX,speedY);
    }
}
