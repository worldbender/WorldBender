package server.opponents.opponentAI;

import server.LogicMap.LogicMapHandler;
import server.User;
import server.bullets.BulletList;
import server.connection.GameController;
import server.opponents.AOpponent;
import server.opponents.OpponentList;
import server.pickups.PickupList;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AOpponentAI implements IOpponentAI {
    protected AOpponent opponent;
    protected GameController gameController;
    protected LogicMapHandler map;
    protected CopyOnWriteArrayList<User> usersInRoom;
    protected BulletList bulletList;
    protected OpponentList opponentList;
    protected PickupList pickupList;
    protected boolean isChangeable;

    public AOpponentAI(AOpponent opponent, GameController gameController, boolean isChangeable){
        this.opponent = opponent;
        this.map = gameController.logicMapHandler;
        this.usersInRoom = gameController.usersInRoom;
        this.bulletList = gameController.bulletList;
        this.opponentList = gameController.opponentList;
        this.pickupList = gameController.pickupList;
        this.gameController = gameController;
        this.isChangeable = isChangeable;
    }

    @Override
    public abstract void behave(double deltaTime);

    @Override
    public abstract boolean shouldChangeAI();

    @Override
    public abstract void changeAI();

    public boolean isChangeable() {
        return isChangeable;
    }

    public void setChangeable(boolean changeable) {
        isChangeable = changeable;
    }
}
