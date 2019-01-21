package server.connection;


import RoomsController.Room;
import RoomsController.RoomList;
import server.ExistingUsers;
import server.User;
import server.opponents.AOpponent;
import server.opponents.OpponentFabric;
import server.opponents.OpponentList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class TcpClientThread extends Thread{
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Map<String, User> existingUsers;
    private List<Room> rooms;
    public User user;

    public TcpClientThread(Socket clientSocket) {
        this.user = new User();
        this.existingUsers = ExistingUsers.getInstance();
        this.clientSocket = clientSocket;
        this.rooms = RoomList.getInstance();
        try {
            out = new PrintWriter(clientSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        System.out.println ("New TCP Connection Started with" + clientSocket.getPort());
        String message;
        try {
            while ((message = in.readLine()) != null) {
                readMessage(message);
                System.out.println(message);
            }
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) { }
        existingUsers.get(user.getConnectionId()).setConnection(false);
        for (User current : existingUsers.values()) {
            current.getThread().sendMessage("dc:"+user.getName());
        }
        System.out.println("TCP Connection lost with " + clientSocket.getPort());
    }

    public void sendMessage(String message){
        out.println(message);
    }

    public void readMessage(String message){
        String[] splitedArray = message.split(":");
        switch (splitedArray[0]){
            case "udpPort": newUser(splitedArray[1]); break;
            case "newRoom": newRoom(this.user); break;
            case "joinRoom": joinRoom(this.user); break;
            case "leaveRoom": leaveRoom(this.user); break;
        }
    }

    public void newUser(String udpPort){
        String id = clientSocket.getInetAddress().toString() + "," + udpPort;
        this.user.setAddress(clientSocket.getInetAddress());
        this.user.setTcpPort(clientSocket.getPort());
        this.user.setUdpPort(Integer.valueOf(udpPort));
        this.user.setConnectionId(id);
        this.user.setName("player"+ existingUsers.size());

        //ten pakiet wysylamy do naszego gracza z jego poczatkowa pozycja
        sendMessage("init:"+this.user.getName()+":"+this.user.getPlayer().getX()+":"+this.user.getPlayer().getY()+":true");

        //te pakiety wysyłamy do innych graczy z informacja ze gracz dolaczyl do gry
        for (User current : existingUsers.values()) {
            current.getThread().sendMessage("newPlayer:player" + (existingUsers.size()));
        }

        //te pakiety wysylamy do naszego gracza z pozycjami juz istniejacych graczy
        for (User current : existingUsers.values()) {
            String message = "init:"+current.getName() + ":" + current.getPlayer().getX() + ":" + current.getPlayer().getY()+":false";
            if(current.hasConnection())
                sendMessage(message);
        }

        existingUsers.put(id, this.user);
        existingUsers.get(user.getConnectionId()).setThread(this);
        createOpponent();
    }

    private void newRoom(User user){
        Room newRoom = new Room(rooms.size());
        newRoom.addUserToRoom(user);
        rooms.add(newRoom);


    }

    //TODO: dolaczanie do roznych pokoi
    private void joinRoom(User user){
        for(Room room : rooms){
            if(room.getId() == 0) room.addUserToRoom(user);
        }
    }

    //TODO: opuszczanie pokoi
    private void leaveRoom(User user){
        for(Room room : rooms){
            if(room.getId() == 0) {
                room.deleteUserFromRoom(user);
                if(room.getUsersInRoom().size() == 0) deleteRoom(room);
            }
        }
    }

    //TODO: start gry
    private void startGame(){
        //te pakiety wysyłamy do innych graczy z informacja ze gracz dolaczyl do gry
        for (User current : existingUsers.values()) {
            current.getThread().sendMessage("newPlayer:player" + (existingUsers.size()));
        }
    }

    //TODO: sprawdzic czy poprawnie dziala
    private void deleteRoom(Room roomToDelete){
        System.out.println("deleting room");
        for(Room room : rooms){
            if(room.getId() == roomToDelete.getId()) {
                rooms.remove(room);
            }
        }
    }

    private void createOpponent(){
        String opponentType = "Nietzsche";
        AOpponent newOpponent = OpponentFabric.createOpponent(opponentType);
        OpponentList.addOpponent(newOpponent);
        String message = "createOpponent:" + newOpponent.getType() + ":" + newOpponent.getId();
        for(User user : existingUsers.values()){
            if(user.hasConnection())
                user.getThread().sendMessage(message);
        }
        opponentType = "Schopenheuer";
        newOpponent = OpponentFabric.createOpponent(opponentType);
        newOpponent.setX(2000);
        newOpponent.setY(2600);
        OpponentList.addOpponent(newOpponent);
        message = "createOpponent:" + newOpponent.getType() + ":" + newOpponent.getId();
        for(User user : existingUsers.values()){
            if(user.hasConnection())
                user.getThread().sendMessage(message);
        }
    }
}
