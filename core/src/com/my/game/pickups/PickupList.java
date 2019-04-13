package com.my.game.pickups;

import java.util.concurrent.CopyOnWriteArrayList;

public class PickupList {
    private static CopyOnWriteArrayList<APickup> pickups=new CopyOnWriteArrayList<>();
    private PickupList(){ }

    public static void addPickup(APickup pickup){
        pickups.add(pickup);
    }
    public static void removePickup(APickup pickup){
        pickups.remove(pickup);
    }
    public static CopyOnWriteArrayList<APickup> getPickups(){
        return pickups;
    }

    public static void removePickupById(int id){
        for(APickup pickup : pickups){
            if(pickup.getId() == id){
                pickups.remove(pickup);
            }
        }
    }
}
