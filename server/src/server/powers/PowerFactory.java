package server.powers;

import server.players.Player;
import server.connection.GameController;

public class PowerFactory {
    public static IPower createPower(String type, GameController gameController, Player player) {
        IPower resultPower;
        switch (type) {
            case "AggroTaker":
                resultPower = new AggroTaker(gameController, player);
                break;
            case "Healer":
                resultPower = new Healer(gameController, player);
                break;
            default:
                resultPower = new EmptyPower();
                break;
        }
        return resultPower;
    }
}
