package server.powers;

import server.Player;
import server.connection.GameController;

public class PowerFabric {
    public static IPower createPower(String type, GameController gameController, Player player) {
        IPower resultPower;
        switch (type) {
            case "AgroTaker":
                resultPower = new AgroTaker(gameController, player);
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
