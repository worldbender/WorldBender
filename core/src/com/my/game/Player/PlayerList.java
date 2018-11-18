package com.my.game.Player;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.Map;

public class PlayerList {
    private static List<Player> players; //= //new HashMap<>();
            //Collections.synchronizedList(new ArrayList());

    private PlayerList(){ }

    public static final List getInstance(){
        if(players == null){
            players = Collections.synchronizedList(new ArrayList<Player>());
        }
        return players;
    }
}
