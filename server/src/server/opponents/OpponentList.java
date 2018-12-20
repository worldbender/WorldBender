package server.opponents;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OpponentList {
    private static CopyOnWriteArrayList<AOpponent> opponents=new CopyOnWriteArrayList<AOpponent>();
    private static CopyOnWriteArrayList<AOpponent> deadOpponents=new CopyOnWriteArrayList<AOpponent>();
    public static int id = 0;

    private OpponentList(){

    }
    public static void addOpponent(AOpponent opponent){
        opponent.setId(id);
        id++;
        opponents.add(opponent);
    }
    public static void removeOpponent(AOpponent opponent){
        opponents.remove(opponent);
    }

    public static List<AOpponent> getOpponents(){
        return opponents;
    }

    public static List<AOpponent> getDeadOpponents(){
        return deadOpponents;
    }
    public static void addToDeadOpponents(AOpponent opponent){
        deadOpponents.add(opponent);
    }
    public static void flushDeadOpponents(){
        deadOpponents.clear();
    }
}
