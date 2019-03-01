package server.connection;

import server.rooms.Room;
import server.rooms.RoomList;
import com.badlogic.gdx.Gdx;
import server.ExistingUsers;
import server.User;
import server.opponents.AOpponent;
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
            case "joinRoom": joinRoom(splitedArray[1], splitedArray[2]); break;
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
        sendMessage("newPlayer:" +
                initedUser.getName() + ":true");

        //te pakiety wysyłamy do innych graczy z informacja ze gracz dolaczyl do gry
        for (User currentUser : room.getUsersInRoom()) {
            currentUser.getThread().sendMessage("newPlayer:"+ initedUser.getName() + ":false") ;
        }

        //TODO
        //Rozsyłamy informacje graczom, kto jest właścicielem pokoju
//        for (User currentUser : room.getUsersInRoom()) {
//            currentUser.getThread().sendMessage("owner:" + room.getRoomOwner().getName());
//        }

        //te pakiety wysylamy do naszego gracza z pozycjami juz istniejacych graczy
        for (User currentUser : room.getUsersInRoom()) {
            String message = "newPlayer:" +
                    currentUser.getName() + ":false";

            if (currentUser.hasConnection())
                sendMessage(message);
        }
    }

    //TODO: lepsze nadawanie id pokoi
    private void newRoom(String udpPort){
        String id = clientSocket.getInetAddress().toString() + "," + udpPort;
        User user = existingUsers.get(id);
        Room room = new Room(rooms.size(), user);

        initUser(user, room);
        room.addUserToRoom(user);

        Gdx.app.postRunnable(() -> TcpServer.createGameController(room));

        sendMessage("createdRoom:" + user.getName() + ":" + room.getId());
    }

    private void joinRoom(String udpPort, String roomToJoin){
        boolean roomExists = false;
        String id = clientSocket.getInetAddress().toString() + "," + udpPort;
        User user = existingUsers.get(id);
        int roomId = Integer.parseInt(roomToJoin);

        for(Room room : rooms){
            if(room.getId() == roomId) {
                roomExists = true;
                initUser(user, room);
                if(room.checkIfUserCanJoinRoom()) {
                    room.addUserToRoom(user);
                    sendMessage("joinedRoom:" + user.getName() + ":" + room.getId());
                }
                else sendMessage("fullRoom:" + user.getName());
                break;
            }
        }

        if(!roomExists)
            sendMessage("roomDoesNotExist:" + user.getName());
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

        currentRoom.setGameStarted(true);
    }

    //TODO: przejściowa wersja, do ogarnięcia
    private void initGame(Room room){
        for(User user : room.getUsersInRoom()){
            user.initializePlayer(room.getGameController());
        }
        room.getGameController().setPlayersPosition();
        room.getGameController().spawnAllOpponents();
    }
}
