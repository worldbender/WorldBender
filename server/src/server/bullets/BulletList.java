package server.bullets;

import server.User;
import server.connection.TcpServer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BulletList {
    private CopyOnWriteArrayList<ABullet> bulletsToCreate = new CopyOnWriteArrayList<ABullet>();
    private CopyOnWriteArrayList<ABullet> bullets = new CopyOnWriteArrayList<ABullet>();
    private CopyOnWriteArrayList<ABullet> deadBullets = new CopyOnWriteArrayList<ABullet>();
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
        String msg = "createBullet:" +
                bullet.getType() + ":" +
                bullet.getId() + ":" +
                bullet.getAngle() + ":";
        TcpServer.sendTcpMsgToAllUsersInRoom(msg, this.users);

    }
    public void deleteBullet(ABullet bullet){
        bullets.remove(bullet);
        String msg = "deleteBullet:" + bullet.getId() + ":";
        TcpServer.sendTcpMsgToAllUsersInRoom(msg, this.users);
    }

}
