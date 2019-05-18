package server.pickups;

import server.players.Player;

public class HpPickup extends APickup {
    public HpPickup(int x, int y){
        this.setX(x);
        this.setY(y);
        this.setType("Hp");
    }


    @Override
    public void modifyPlayer(Player player) {
        if(player.getHp() < player.MAX_HP){
            player.setHp(player.getHp() + 10);
        }
    }
}
