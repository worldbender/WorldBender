package server.opponents;

import server.User;
import server.connection.GameController;
import server.connection.TcpServer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OpponentList {
    private CopyOnWriteArrayList<AOpponent> opponents = new CopyOnWriteArrayList<AOpponent>();
    private CopyOnWriteArrayList<User> usersInRoom;
    public int id = 0;

    public OpponentList(){
    }

    public void initializeWithGameController(GameController gameController){
        this.usersInRoom = gameController.usersInRoom;
    }

    public List<AOpponent> getOpponents(){
        return opponents;
    }

    public void createOpponent(AOpponent opponent){
        opponent.setId(id);
        id++;
        opponents.add(opponent);
        String message = "createOpponent:" + opponent.getType() + ":" + opponent.getId();
        TcpServer.sendTcpMsgToAllUsersInRoom(message, this.usersInRoom);
    }

    public void deleteOpponent(AOpponent opponent){
        opponents.remove(opponent);
        String message = "deleteOpponent:" + opponent.getId();
        TcpServer.sendTcpMsgToAllUsersInRoom(message, this.usersInRoom);
    }
}
