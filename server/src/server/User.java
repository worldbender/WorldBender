package server;

import server.connection.TcpClientThread;

import java.net.InetAddress;

public class User {
    private InetAddress userAddress;
    private Integer userUdpPort;
    private Integer userTcpPort;
    private String connectionId;
    private boolean connection;
    private Player player;
    private String name;
    private TcpClientThread thread;

    public User(InetAddress userAddress, Integer userTcpPort, Integer userUdpPort, String connectionId, String name){
        this.userAddress = userAddress;
        this.userTcpPort = userTcpPort;
        this.userUdpPort = userUdpPort;
        this.connectionId = connectionId;
        this.name = name;
        this.player = new Player(this);
        this.connection = true;
    }

    public User(){
        this.player = new Player(this);
        this.connection = true;
    }

    public String getConnectionId(){
        return this.connectionId;
    }

    public void setConnectionId(String connectionId){
        this.connectionId = connectionId;
    }

    public InetAddress getAddress(){
        return this.userAddress;
    }

    public void setAddress(InetAddress userAddress){
        this.userAddress = userAddress;
    }

    public Integer getUdpPort(){
        return this.userUdpPort;
    }

    public void setUdpPort(Integer userUdpPort){
        this.userUdpPort = userUdpPort;
    }

    public Integer getTcpPort(){
        return this.userTcpPort;
    }

    public void setTcpPort(Integer userTcpPort){
        this.userTcpPort = userTcpPort;
    }

    public Player getPlayer(){
        return this.player;
    }

    public void setPlayer(Player player){
        this.player=player;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public boolean hasConnection(){
        return this.connection;
    }

    public void setConnection(boolean status){
        this.connection = status;
    }

    public TcpClientThread getThread(){
        return this.thread;
    }

    public void setThread(TcpClientThread thread){
        this.thread = thread;
    }

}
