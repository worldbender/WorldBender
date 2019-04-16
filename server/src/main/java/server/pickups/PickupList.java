package server.pickups;

import org.json.JSONObject;
import server.User;
import server.connection.TcpServer;
import java.util.concurrent.CopyOnWriteArrayList;

public class PickupList {
    private CopyOnWriteArrayList<APickup> pickups = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<User> users;
    private static int id = 0;
    public PickupList(CopyOnWriteArrayList<User> users){
        this.users = users;
    }

    public void addPickup(APickup pickup){
        pickup.setId(id);
        id++;
        pickups.add(pickup);

        JSONObject obj = new JSONObject()
                .put("msg", "createPickup")
                .put("content", new JSONObject()
                        .put("x", pickup.getX())
                        .put("y", pickup.getY())
                        .put("id", pickup.getId())
                        .put("type", pickup.getType())
                );
        TcpServer.sendTcpMsgToAllUsersInRoom(obj, users);
    }
    public void deletePickup(APickup pickup){
        pickups.remove(pickup);
        JSONObject obj = new JSONObject()
                .put("msg", "deletePickup")
                .put("content", new JSONObject()
                        .put("id", pickup.getId())
                );
        TcpServer.sendTcpMsgToAllUsersInRoom(obj, users);
    }

    public CopyOnWriteArrayList<APickup> getPickups(){return this.pickups;}
}
