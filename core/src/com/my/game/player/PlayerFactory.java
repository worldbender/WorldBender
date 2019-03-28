package com.my.game.player;

public class PlayerFactory {
    public static Player createPlayer(String type, String name) {
        Player resultPlayer;
        switch (type) {
            case "Ground":
                resultPlayer = new Ground(name);
                break;
            case "Water":
                resultPlayer = new Water(name);
                break;
            default:
                resultPlayer = null;
                break;
        }
        return resultPlayer;
    }
}
