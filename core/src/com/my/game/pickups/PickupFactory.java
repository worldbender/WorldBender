package com.my.game.pickups;

public class PickupFactory {
    private PickupFactory(){}
    public static APickup createPickup(int x, int y, int id, String type){
        APickup result = null;
        switch (type){
            case "Hp":
                result = new HpPickup(x, y, id);
                break;
            case "InnerEye":
                result = new InnerEye(x, y, id);
                break;
            case "SadOnion":
                result = new SadOnion(x, y, id);
                break;
            case "Warp":
                result = new Warp(x, y, id);
                break;
            case "InvisibleWarp":
                result = new InvisibleWarp(x, y, id);
                break;
            case "Mana":
               //TODO make mana pickup
                break;
            default:
        }
        return result;
    }
}
