package server.bullets;

import org.json.JSONObject;
import server.User;
import server.connection.TcpServer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BulletList {
    private CopyOnWriteArrayList<ABullet> bullets = new CopyOnWriteArrayList<ABullet>();
    private CopyOnWriteArrayList<User> users;
    public static int id = 0;

    public BulletList(CopyOnWriteArrayList<User> users){
        this.users = users;
    }

    public List<ABullet> getBullets(){
        return bullets;
    }

    public void addBullet(ABullet bullet){
        bullet.setId(id);
        id++;
        bullets.add(bullet);
        JSONObject obj = new JSONObject()
                .put("msg", "createBullet")
                .put("content", new JSONObject().put("type", bullet.getType()).put("id", bullet.getId()).put("angle", bullet.getAngle())
                );

        TcpServer.sendTcpMsgToAllUsersInRoom(obj, this.users);

    }
    public void deleteBullet(ABullet bullet){
        bullets.remove(bullet);
        JSONObject obj = new JSONObject()
                .put("msg", "deleteBullet")
                .put("content", new JSONObject().put("id", bullet.getId())
                );
        TcpServer.sendTcpMsgToAllUsersInRoom(obj, this.users);
    }

}
