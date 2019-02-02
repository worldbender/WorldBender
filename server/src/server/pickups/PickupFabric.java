package server.pickups;

public class PickupFabric {
    public static APickup createPickup(int x, int y, String type){
        APickup pickup = null;
        switch (type){
            case "Hp":
                pickup = new HpPickup(x, y);
                break;
            case "InnerEye":
                pickup = new InnerEye(x, y);
                break;
            case "SadOnion":
                pickup = new SadOnion(x, y);
                break;
            case "Mana":
                //TODO make mana pickup
                break;
        }
        return pickup;
    }
}
