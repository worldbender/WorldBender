import java.net.*;

public class Client {
    private InetAddress clientAddress;
    private Integer clientPort;
    private String id;
    private String name;
    private int x=500;
    private int y=500;

    public Client(InetAddress clientAddress, Integer clientPort, String id, String name){
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
        this.id = id;
        this.name = name;
    }

    public String getId(){
        return this.id;
    }

    public InetAddress getAddress(){
        return this.clientAddress;
    }

    public Integer getPort(){
        return this.clientPort;
    }

    public String getName(){
        return this.name;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setPosition(String content){
        char letter = content.charAt(0);
        if(letter=='A'){
            this.x-=5;
        }
        else if(letter=='D'){
            this.x+=5;
        }
        else if(letter=='W'){
            this.y+=5;
        }
        else if(letter=='S'){
            this.y-=5;
        }
    }


}
