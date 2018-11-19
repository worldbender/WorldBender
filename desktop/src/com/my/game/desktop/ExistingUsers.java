package com.my.game.desktop;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ExistingUsers {
    private static Map<String, User> users;

    private ExistingUsers(){ }

    public static final Map getInstance(){
        if(users == null){
            users = Collections.synchronizedMap(new HashMap());
        }
        return users;
    }
}
