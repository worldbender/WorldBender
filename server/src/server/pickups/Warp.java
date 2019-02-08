package server.pickups;

import server.Player;

public class Warp extends APickup {
    public Warp(int x, int y){
        this.setX(x);
        this.setY(y);
        this.setType("Warp");
    }
    @Override
    public void modifyPlayer(Player player) {
        player.getGameController().changeMap("map2");
    }
}
