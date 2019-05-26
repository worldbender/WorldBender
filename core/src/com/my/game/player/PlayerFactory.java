package com.my.game.player;

public class PlayerFactory {
    private PlayerFactory(){}
    public static Player createPlayer(String type, String name, String id) {
        Player resultPlayer;
        switch (type) {
            case "Ground":
                resultPlayer = new Ground(name, id);
                break;
            case "Water":
                resultPlayer = new Water(name, id);
                break;
            default:
                resultPlayer = null;
                break;
        }
        return resultPlayer;
    }
}
