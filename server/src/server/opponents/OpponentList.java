package server.opponents;

import server.connection.GameController;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OpponentList {
    private static CopyOnWriteArrayList<AOpponent> opponents=new CopyOnWriteArrayList<AOpponent>();
    public static int id = 0;

    private OpponentList(){

    }
    public static void addOpponent(AOpponent opponent){
        opponent.setId(id);
        id++;
        opponents.add(opponent);
    }
    public static void removeOpponent(AOpponent opponent){
        GameController.removeOpponent(opponent);
        opponents.remove(opponent);
    }

    public static List<AOpponent> getOpponents(){
        return opponents;
    }

}
