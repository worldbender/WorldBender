package com.my.game.pickups;

public class PickupFabric {
    public static APickup createPickup(int x, int y, int id, String type){
        APickup result = null;
        switch (type){
            case "Hp":
                result = new HpPickup(x, y, id);
                break;
            case "Mana":
               //TODO make mana pickup
                break;
        }
        return result;
    }
}