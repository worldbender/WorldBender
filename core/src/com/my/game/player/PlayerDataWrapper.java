package com.my.game.player;

public class PlayerDataWrapper {
    private String name;
    private String userId;
    private String character;
    private boolean isOwner;

    public PlayerDataWrapper(String name, String userId, String character, boolean isOwner) {
        this.name = name;
        this.userId = userId;
        this.character = character;
        this.isOwner = isOwner;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getCharacter() {
        if(character.matches("Ground")) return "Tank";
        else if(character.matches("Water")) return "Healer";
        return character;
    }

    public boolean isOwner() {
        return isOwner;
    }
}
