import java.net.*;

public class User {
    private InetAddress userAddress;
    private Integer userPort;
    private String id;
    private String connectionStatus;
    public Player player;
    private String name;

    public User(InetAddress userAddress, Integer userPort, String id, String name){
        this.userAddress = userAddress;
        this.userPort = userPort;
        this.id = id;
        this.name = name;
        this.player = new Player();
    }

    public String getId(){
        return this.id;
    }

    public InetAddress getAddress(){
        return this.userAddress;
    }

    public Integer getPort(){
        return this.userPort;
    }

    public String getName(){
        return this.name;
    }


}
