package server.opponents;

import org.json.JSONObject;
import server.logicmap.LogicMapHandler;
import server.User;
import server.connection.GameController;
import server.connection.TcpServer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OpponentList {
    private CopyOnWriteArrayList<AOpponent> opponents = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<User> usersInRoom;
    private LogicMapHandler logicMapHandler;
    public int id = 0;

    public OpponentList(){
    }

    public void initializeWithGameController(GameController gameController){
        this.usersInRoom = gameController.usersInRoom;
        this.logicMapHandler = gameController.logicMapHandler;
    }

    public List<AOpponent> getOpponents(){
        return opponents;
    }

    public void createOpponent(AOpponent opponent){
        opponent.setId(id);
        id++;
        opponents.add(opponent);
    }

    public void deleteOpponent(AOpponent opponent){
        opponents.remove(opponent);
        JSONObject message = new JSONObject()
                .put("msg", "deleteOpponent")
                .put("content", new JSONObject().put("id", opponent.getId()));
        TcpServer.sendTcpMsgToAllUsersInRoom(message, this.usersInRoom);
        this.checkIfShouldOpenDoors();
    }

    public void checkIfShouldOpenDoors(){
        if(opponents.isEmpty()){
            this.logicMapHandler.openDoors();
        }
    }
}
