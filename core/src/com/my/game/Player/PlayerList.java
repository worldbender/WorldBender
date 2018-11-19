package com.my.game.Player;

import java.util.*;

public class PlayerList {
    private static Map<String, Player> players;

    private PlayerList(){ }

    public static final Map getInstance(){
        if(players == null){
            players = Collections.synchronizedMap(new HashMap());
        }
        return players;
    }
}
