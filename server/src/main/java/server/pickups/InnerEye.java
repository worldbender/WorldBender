package server.pickups;

import server.players.Player;

public class InnerEye extends APickup{
    public InnerEye(int x, int y){
        this.setX(x);
        this.setY(y);
        this.setType("InnerEye");
    }


    //TODO test this method
    @Override
    public void modifyPlayer(Player player) {
        if(!player.getWeaponType().equals("Triple")){
            player.setWeaponType("Triple");
        }
        if(!player.hasPlayerItem("Triple")){
            player.setShootSpeedModificator(2L);
        }
        player.getCollectedItems().add("Triple");
    }
}
