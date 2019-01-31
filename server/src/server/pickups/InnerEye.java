package server.pickups;

import server.Player;

public class InnerEye extends APickup{
    public InnerEye(int x, int y){
        this.setX(x);
        this.setY(y);
        this.setType("InnerEye");
    }

    @Override
    public void modifyPlayer(Player player) {
        if(!player.getWeaponType().equals("Triple")){
            player.setWeaponType("Triple");
            player.setShootCooldown(player.getShootCooldown()*2L);
        }
    }
}
