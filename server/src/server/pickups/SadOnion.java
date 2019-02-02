package server.pickups;

import server.Player;

public class SadOnion extends APickup{
    public SadOnion(int x, int y){
        this.setX(x);
        this.setY(y);
        this.setType("SadOnion");
    }

    @Override
    public void modifyPlayer(Player player) {
        if(!player.getWeaponType().equals("SadOnion")){
            player.setWeaponType("SadOnion");
        }
        if(!player.hasPlayerItem("SadOnion")){
            player.setShootSpeedModificator(2L);
        }
        player.getCollectedItems().add("SadOnion");
    }
}
