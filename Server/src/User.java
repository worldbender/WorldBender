import java.net.*;

public class User {
    private InetAddress userAddress;
    private Integer userUdpPort;
    private Integer userTcpPort;
    private String id;
    private boolean connection;
    public Player player;
    private String name;
    public TcpClientThread thread;

    public User(InetAddress userAddress, Integer userTcpPort, Integer userUdpPort, String id, String name){
        this.userAddress = userAddress;
        this.userTcpPort = userTcpPort;
        this.userUdpPort = userUdpPort;
        this.id = id;
        this.name = name;
        this.player = new Player();
        this.connection = true;
    }

    public User(){
        this.player = new Player();
        this.connection = true;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
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

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public boolean getConnection(){
        return this.connection;
    }

    public void setConnection(boolean status){
        this.connection = status;
    }

}
