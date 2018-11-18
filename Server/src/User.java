import java.net.*;

public class User {
    private InetAddress userAddress;
    private Integer userUdpPort;
    private Integer userTcpPort;
    private String id;
    private boolean connection = false;
    public Player player;
    private String name;

    public User(InetAddress userAddress, Integer userTcpPort, Integer userUdpPort, String id, String name){
        this.userAddress = userAddress;
        this.userTcpPort = userTcpPort;
        this.userUdpPort = userUdpPort;
        this.id = id;
        this.name = name;
        this.player = new Player();
        this.connection = true;
    }

    public String getId(){
        return this.id;
    }

    public InetAddress getAddress(){
        return this.userAddress;
    }

    public Integer getUdpPort(){
        return this.userUdpPort;
    }

    public Integer getTcpPort(){
        return this.userTcpPort;
    }

    public String getName(){
        return this.name;
    }

    public void setConnection(boolean status){
        this.connection = status;
    }


}
