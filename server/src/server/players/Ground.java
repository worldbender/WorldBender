package server.players;

import server.User;
import server.connection.GameController;
import server.powers.PowerFactory;

public class Ground extends Player {
    public Ground(User user, GameController gameController){
        super(user, gameController);
        this.power = PowerFactory.createPower("AggroTaker", gameController, this);
        this.setPlayerType("Ground");
        this.bulletType = "FireRing";
    }
}
