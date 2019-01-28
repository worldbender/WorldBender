package server.opponents;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OpponentList {
    private CopyOnWriteArrayList<AOpponent> opponents = new CopyOnWriteArrayList<AOpponent>();
    private CopyOnWriteArrayList<AOpponent> deadOpponents = new CopyOnWriteArrayList<AOpponent>();
    public int id = 0;

    public OpponentList(){

    }

    public void addOpponent(AOpponent opponent){
        opponent.setId(id);
        id++;
        opponents.add(opponent);
    }

    public void removeOpponent(AOpponent opponent){
        opponents.remove(opponent);
    }

    public void addDeadAOpponentsTrashList(AOpponent opponent){
        deadOpponents.add(opponent);
    }
    public void flushDeadAOpponents(){
        deadOpponents.clear();
    }
    public List<AOpponent> getOpponents(){
        return opponents;
    }
    public List<AOpponent> getDeadOpponenets(){
        return deadOpponents;
    }
}
