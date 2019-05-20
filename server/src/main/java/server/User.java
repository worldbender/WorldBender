package server;

import server.connection.GameController;
import server.connection.TcpClientThread;
import server.players.Player;
import server.players.PlayersFactory;

import java.net.InetAddress;

public class User {
    private InetAddress userAddress;
    private Integer userUdpPort;
    private Integer userTcpPort;
    private String connectionId;
    private boolean connection;
    private Player player;
    private String name;
    private String characterType;
    private TcpClientThread thread;
    private Properties properties;
    
    public User(){
        this.properties = new Properties();
        this.player = new Player(this);
        this.connection = true;
    }
    
    public User(Player player){
        this.properties = new Properties();
        this.player = new Player(this);
        this.connection = true;
    }

    public void initializePlayer(GameController gameController, String playerType){
        this.player = PlayersFactory.createPlayer(playerType, this, gameController);
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

    public String getCharacterType() {
        return characterType;
    }

    public void setCharacterType(String characterType) {
        this.characterType = characterType;
    }
    
    public Properties getProperties(){ return this.properties; }
    
}
