package server.connection;


import server.rooms.Room;
import server.rooms.RoomList;
import com.badlogic.gdx.Gdx;
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
        System.out.println ("New TCP Connection Started with " + clientSocket.getPort());
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
            case "newRoom": newRoom(splitedArray[1]); break;
            case "joinRoom": joinRoom(splitedArray[1]); break;
            case "leaveRoom": leaveRoom(splitedArray[1]); break;
            case "startGame": startGame(splitedArray[1]); break;
        }
    }

    public void newUser(String udpPort){
        String id = clientSocket.getInetAddress().toString() + "," + udpPort;
        this.user.setAddress(clientSocket.getInetAddress());
        this.user.setTcpPort(clientSocket.getPort());
        this.user.setUdpPort(Integer.valueOf(udpPort));
        this.user.setConnectionId(id);
        this.user.setName("player"+ existingUsers.size());

        existingUsers.put(id, this.user);
        existingUsers.get(user.getConnectionId()).setThread(this);
    }

    private void initUser(User initedUser, Room room){
        //ten pakiet wysylamy do naszego gracza z jego poczatkowa pozycja
        sendMessage("init:" +
                initedUser.getName() + ":" +
                initedUser.getPlayer().getX() + ":" +
                initedUser.getPlayer().getY() + ":true");

        //te pakiety wysyłamy do innych graczy z informacja ze gracz dolaczyl do gry
        for (User currentUser : room.getUsersInRoom()) {
            currentUser.getThread().sendMessage("newPlayer:player" + (room.getUsersInRoom().size()));
        }

        //TODO
        //Rozsyłamy informacje graczom, kto jest właścicielem pokoju
//        for (User currentUser : room.getUsersInRoom()) {
//            currentUser.getThread().sendMessage("owner:" + room.getRoomOwner().getName());
//        }

        //Rozsyłamy informacje o istniejacych potworkach do nowego gracza
        for(AOpponent opponent : room.getOpponentList().getOpponents()){
            sendMessage("createOpponent:" +
                    opponent.getType() + ":" +
                    opponent.getId());
        }

        //te pakiety wysylamy do naszego gracza z pozycjami juz istniejacych graczy
        for (User currentUser : room.getUsersInRoom()) {
            String message = "init:" +
                    currentUser.getName() + ":" +
                    currentUser.getPlayer().getX() + ":" +
                    currentUser.getPlayer().getY() + ":false";

            if (currentUser.hasConnection())
                sendMessage(message);
        }
    }

    private void newRoom(String udpPort){
        String id = clientSocket.getInetAddress().toString() + "," + udpPort;
        User user = existingUsers.get(id);
        Room room = new Room(rooms.size(), user);

        initUser(user, room);
        room.addUserToRoom(user);

        Gdx.app.postRunnable(() -> TcpServer.createGameController(room));
    }

    //TODO: dolaczanie do roznych pokoi
    private void joinRoom(String udpPort){
        String id = clientSocket.getInetAddress().toString() + "," + udpPort;
        User user = existingUsers.get(id);

        for(Room room : rooms){
            if(room.getId() == 0) {
                initUser(user, room);
                room.addUserToRoom(user);
            }
        }
    }

    private void leaveRoom(String udpPort){
        String id = clientSocket.getInetAddress().toString() + "," + udpPort;
        User user = existingUsers.get(id);
        Room currentRoom = RoomList.getUserRoom(id);

        currentRoom.deleteUserFromRoom(user);
    }

    private void startGame(String udpPort){
        String id = clientSocket.getInetAddress().toString() + "," + udpPort;
        User user = existingUsers.get(id);
        Room currentRoom = RoomList.getUserRoom(id);

        initGame(currentRoom);

        for(User currentUser : currentRoom.getUsersInRoom()){
            currentUser.getThread().sendMessage("startGame:" + user.getName()); //TODO: ewentualna poprawka wysyłanego info
        }
    }

    //TODO: przejściowa wersja, do ogarnięcia
    private void initGame(Room room){
        int x = 0;
        int nextX = 100;
        for(User user : room.getUsersInRoom()){
            user.getPlayer().setX(user.getPlayer().getX() + x);
            x -= nextX;
        }

        createOpponent(room);
    }

    private void createOpponent(Room room) {
        OpponentList roomOpponentList = room.getOpponentList();

        String opponentType = "Nietzsche";
        AOpponent newOpponent = OpponentFabric.createOpponent(opponentType);
        newOpponent.setX(1600);
        newOpponent.setY(400);
        roomOpponentList.addOpponent(newOpponent);

        opponentType = "Schopenheuer";
        newOpponent = OpponentFabric.createOpponent(opponentType);
        newOpponent.setX(2000);
        newOpponent.setY(2600);
        roomOpponentList.addOpponent(newOpponent);

        opponentType = "Poe";
        newOpponent = OpponentFabric.createOpponent(opponentType);
        newOpponent.setX(1400);
        newOpponent.setY(400);
        roomOpponentList.addOpponent(newOpponent);

        newOpponent = OpponentFabric.createOpponent("Poe");
        newOpponent.setX(600);
        newOpponent.setY(1600);
        roomOpponentList.addOpponent(newOpponent);

        newOpponent = OpponentFabric.createOpponent("Poe");
        newOpponent.setX(1900);
        newOpponent.setY(500);
        roomOpponentList.addOpponent(newOpponent);

        newOpponent = OpponentFabric.createOpponent("Poe");
        newOpponent.setX(2000);
        newOpponent.setY(400);
        roomOpponentList.addOpponent(newOpponent);

        newOpponent = OpponentFabric.createOpponent("Poe");
        newOpponent.setX(1900);
        newOpponent.setY(1500);
        roomOpponentList.addOpponent(newOpponent);

        newOpponent = OpponentFabric.createOpponent("Poe");
        newOpponent.setX(1500);
        newOpponent.setY(1500);
        roomOpponentList.addOpponent(newOpponent);;

        newOpponent = OpponentFabric.createOpponent("Poe");
        newOpponent.setX(1330);
        newOpponent.setY(1500);
        roomOpponentList.addOpponent(newOpponent);

        newOpponent = OpponentFabric.createOpponent("Poe");
        newOpponent.setX(1200);
        newOpponent.setY(1500);
        roomOpponentList.addOpponent(newOpponent);

        newOpponent = OpponentFabric.createOpponent("Poe");
        newOpponent.setX(1000);
        newOpponent.setY(1500);
        roomOpponentList.addOpponent(newOpponent);

        String message;
        for(User user : room.getUsersInRoom()){
            for(AOpponent opponent : roomOpponentList.getOpponents()){
                message = "createOpponent:" +
                        opponent.getType() + ":" +
                        opponent.getId();

                if(user.hasConnection())
                    user.getThread().sendMessage(message);
            }
        }
    }

}
