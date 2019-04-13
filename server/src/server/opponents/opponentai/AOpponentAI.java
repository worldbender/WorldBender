package server.opponents.opponentai;

import server.logicmap.LogicMapHandler;
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
    public boolean additionalFlag = false;

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
    public void setAdditionalFlag(boolean additionalFlag){
        this.additionalFlag = additionalFlag;
    }
    @Override
    public boolean getAdditionalFlag(){
        return additionalFlag;
    }

    public boolean isChangeable() {
        return isChangeable;
    }

    public void setChangeable(boolean changeable) {
        isChangeable = changeable;
    }
}
