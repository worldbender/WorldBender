package server.players;

import server.User;
import server.connection.GameController;
import server.powers.PowerFabric;

public class Ground extends Player {
    public Ground(User user, GameController gameController){
        super(user, gameController);
        this.power = PowerFabric.createPower("AggroTaker", gameController, this);
        this.setPlayerType("Ground");
    }
}
