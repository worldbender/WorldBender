package server.logicmap;

import server.connection.GameController;
import server.opponents.AOpponent;
import server.opponents.OpponentFactory;
import server.opponents.OpponentList;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventList {
    private CopyOnWriteArrayList<EventBlock> passiveEventList = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<EventBlock> activeEventList = new CopyOnWriteArrayList<>();
    private String nextMap;
    private GameController gameController;
    public EventList(GameController gameController){
        this.gameController = gameController;
    }
    public Point getNextPlayerSpawnPoint(){
        Point resultPoint = new Point();
        for (EventBlock eventBlock : passiveEventList){
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
        for (EventBlock eventBlock : passiveEventList){
            if(eventBlock.getKey().equals("enemy") && !eventBlock.isHappened()){
                String opponentType = eventBlock.getValue();
                AOpponent newOpponent = OpponentFactory.createOpponent(opponentType, this.gameController);
                newOpponent.setX(eventBlock.getX());
                newOpponent.setY(eventBlock.getY());
                opponentList.createOpponent(newOpponent);
                eventBlock.setHappened(true);
            }
        }
    }
    public void addPassiveEvent(EventBlock eventBlock){
        this.passiveEventList.add(eventBlock);
    }
    public void addActiveEvent(EventBlock eventBlock){
        this.activeEventList.add(eventBlock);
    }

    public String getNextMap() {
        return nextMap;
    }

    public void setNextMap(String nextMap) {
        this.nextMap = nextMap;
    }
}
