package server.powers;

import server.LogicMap.LogicMapHandler;
import server.Player;
import server.User;
import server.bullets.BulletList;
import server.connection.GameController;
import server.opponents.AOpponent;
import server.opponents.OpponentList;

import java.util.concurrent.CopyOnWriteArrayList;

public class AgroTaker extends APower {
    private LogicMapHandler map;
    private BulletList bulletList;
    private CopyOnWriteArrayList<User> usersInRoom;
    private GameController gameController;
    private OpponentList opponentList;
    private Player player;

    public AgroTaker(GameController gameController, Player player){
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
            double distance;
            for(AOpponent opponent : this.opponentList.getOpponents()){
                distance = Math.sqrt((Math.abs(player.getCenterY() - opponent.getCenterY())) * (Math.abs(player.getCenterY() - opponent.getCenterY())) +
                        (Math.abs(opponent.getCenterX() - player.getCenterX()) * (Math.abs(opponent.getCenterX() - player.getCenterX()))));
                if(distance < 5000){
                    opponent.setOpponentAI("Chaser");
                    opponent.setIdOfChasedPlayer(player.getUser().getName());
                    opponent.getOpponentAI().setAdditionalFlag(true);
                }
            }
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
