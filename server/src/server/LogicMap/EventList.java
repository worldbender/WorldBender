package server.LogicMap;

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
            }
        }
        return resultPoint;
    }
    public void add(EventBlock eventBlock){
        this.eventList.add(eventBlock);
    }
}
