package server.powers;

import server.LogicMap.LogicMapHandler;
import server.players.Player;
import server.User;
import server.bullets.BulletList;
import server.connection.GameController;
import server.opponents.OpponentList;

import java.util.concurrent.CopyOnWriteArrayList;

public class Healer extends APower {
    private LogicMapHandler map;
    private BulletList bulletList;
    private CopyOnWriteArrayList<User> usersInRoom;
    private GameController gameController;
    private OpponentList opponentList;
    private Player player;

    public Healer(GameController gameController, Player player){
        this.map = gameController.logicMapHandler;
        this.bulletList = gameController.bulletList;
        this.usersInRoom = gameController.usersInRoom;
        this.opponentList = gameController.opponentList;
        this.player = player;
        this.gameController = gameController;
        this.setCooldown(2000L);
        this.setLastTimePowerWasUsed(0L);
    }

    @Override
    public void act(double deltaTime) {
        if(this.canAct()){
            this.usersInRoom.stream()
                    .map(user -> user.getPlayer())
                    .forEach(player -> {
                        final double distance = Math.sqrt((Math.abs(this.player.getCenterY() - player.getCenterY())) * (Math.abs(this.player.getCenterY() - player.getCenterY())) +
                                (double)(Math.abs(player.getCenterX() - this.player.getCenterX()) * (Math.abs(player.getCenterX() - this.player.getCenterX()))));
                        if(distance < 2000){
                            player.setHp(player.getHp() + 5);
                        }
                    });
        }
    }

    @Override
    public boolean canAct() {
        if(player.KEY_SPACE){
            return this.hasCoolDownPassed();
        }
        return false;
    }
}
