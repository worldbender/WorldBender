package server.pickups;

import server.User;
import server.connection.TcpServer;

import java.util.concurrent.CopyOnWriteArrayList;

public class PickupList {
    private CopyOnWriteArrayList<APickup> pickups = new CopyOnWriteArrayList<APickup>();
    private CopyOnWriteArrayList<User> users;
    private static int id = 0;
    public PickupList(CopyOnWriteArrayList<User> users){
        this.users = users;
    }

    public void addPickup(APickup pickup){
        pickup.setId(id);
        id++;
        pickups.add(pickup);
        String msg = "createPickup:" + (int)pickup.getX() + ":" + (int)pickup.getY() + ":" + pickup.getId() + ":" + pickup.getType() + ":";
        TcpServer.sendTcpMsgToAllUsersInRoom(msg, users);
    }
    public void deletePickup(APickup pickup){
        pickups.remove(pickup);
        String msg = "deletePickup:" + pickup.getId() + ":";
        TcpServer.sendTcpMsgToAllUsersInRoom(msg, users);
    }

    public CopyOnWriteArrayList<APickup> getPickups(){return this.pickups;}
}
