package server.players;

import server.User;
import server.connection.GameController;
import server.powers.PowerFactory;

public class Water extends Player{
    public Water(User user, GameController gameController){
        super(user, gameController);
        this.power = PowerFactory.createPower("Healer", gameController, this);
        this.setPlayerType("Water");
    }
}
