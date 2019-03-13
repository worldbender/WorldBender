package server.players;

import server.User;
import server.connection.GameController;
import server.powers.PowerFabric;

public class Water extends Player{
    public Water(User user, GameController gameController){
        super(user, gameController);
        this.power = PowerFabric.createPower("Healer", gameController, this);
    }
}
