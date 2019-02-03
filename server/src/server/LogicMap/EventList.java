package server.LogicMap;

import server.opponents.AOpponent;
import server.opponents.OpponentFabric;
import server.opponents.OpponentList;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventList {
    private CopyOnWriteArrayList<EventBlock> eventList = new CopyOnWriteArrayList<EventBlock>();
    public EventList(){

    }
    public Point getNextPlayerSpawnPoint(){
        Point resultPoint = new Point();
        for (EventBlock eventBlock : eventList){
            if(eventBlock.getKey().equals("spawn") && eventBlock.getValue().equals("player") && !eventBlock.isHappened()){
                resultPoint.setLocation(
                        eventBlock.getX(),
                        eventBlock.getY()
                );
                eventBlock.setHappened(true);
                break;
            }
        }
        return resultPoint;
    }

    public void spawnAllOpponents(OpponentList opponentList){
        for (EventBlock eventBlock : eventList){
            if(eventBlock.getKey().equals("enemy") && !eventBlock.isHappened()){
                String opponentType = eventBlock.getValue();
                AOpponent newOpponent = OpponentFabric.createOpponent(opponentType);
                newOpponent.setX(eventBlock.getX());
                newOpponent.setY(eventBlock.getY());
                opponentList.addOpponent(newOpponent);
                eventBlock.setHappened(true);
            }
        }
    }
    public void add(EventBlock eventBlock){
        this.eventList.add(eventBlock);
    }
}
